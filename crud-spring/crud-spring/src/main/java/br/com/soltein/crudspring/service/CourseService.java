package br.com.soltein.crudspring.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.soltein.crudspring.dto.CourseDTO;
import br.com.soltein.crudspring.dto.CoursePageDTO;
import br.com.soltein.crudspring.dto.mapper.CourseMapper;
import br.com.soltein.crudspring.exception.RecordNotFoundException;
import br.com.soltein.crudspring.model.Course;
import br.com.soltein.crudspring.repository.CourseRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    
    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    public CoursePageDTO list(int page, int pageSize){
//        return this.courseRepository.findAll()
//        		.stream()
//        		.map(courseMapper::toDTO)
//        		.toList();
    	
    	
    	Page<Course> pageCourse = this.courseRepository.findAll(PageRequest.of(page, pageSize));
    	List<CourseDTO> courses = pageCourse.get().map(courseMapper::toDTO).collect(Collectors.toList());
    	
    	return new CoursePageDTO(courses, pageCourse.getTotalElements(), pageCourse.getTotalPages());
    }  
    
    public CourseDTO findById(@PathVariable @NotNull @Positive Long id){
        return this.courseRepository.findById(id)
        			.map(courseMapper::toDTO)
        			.orElseThrow(() -> new RecordNotFoundException(id));
    } 
    
    public CourseDTO create(@Valid CourseDTO courseDTO){
        return this.courseMapper.toDTO(
        			courseRepository.save(courseMapper.toEntity(courseDTO))
        		);
    } 
    
    public CourseDTO update(@NotNull @Positive Long id, @Valid CourseDTO courseDTO){
       return this.courseRepository.findById(id)
                .map(recordFound -> {
                	
                	Course course = courseMapper.toEntity(courseDTO);
                	
                    recordFound.setName(courseDTO.name());
                    recordFound.setCategory(courseMapper.convertCategoryValue(courseDTO.category()));
                    
                    recordFound.getLessons().clear();
                    
                    course.getLessons().forEach(recordFound.getLessons()::add);
                    
                    return this.courseMapper.toDTO(courseRepository.save(recordFound));
                })
                .orElseThrow(() -> new RecordNotFoundException(id));
    }    

    public void delete(@NotNull @Positive Long id){
    	this.courseRepository.delete(this.courseRepository.findById(id).
    				orElseThrow(() -> new RecordNotFoundException(id)));
     }    
}
