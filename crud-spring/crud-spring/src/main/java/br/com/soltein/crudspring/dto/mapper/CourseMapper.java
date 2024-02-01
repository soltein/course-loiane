package br.com.soltein.crudspring.dto.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import br.com.soltein.crudspring.dto.CourseDTO;
import br.com.soltein.crudspring.dto.LessonDTO;
import br.com.soltein.crudspring.enums.Category;
import br.com.soltein.crudspring.model.Course;
import br.com.soltein.crudspring.model.Lesson;

@Component
public class CourseMapper {

	public CourseDTO toDTO(Course course) {
		if(course == null) {
			return null;
		}	
		
		List<LessonDTO> lessons = course.getLessons()
				.stream()
				.map(lesson -> new LessonDTO(lesson.getId(), lesson.getName(), lesson.getYoutubeUrl()))
				.toList();
		
		return new CourseDTO(course.getId(), course.getName(), course.getCategory().getValue(), lessons);
	}
	
	public Course toEntity(CourseDTO courseDTO) {
		
		if(courseDTO == null) {
			return null;
		}
		
		Course course = new Course();
		
		if(courseDTO._id() != null) {
			course.setId(courseDTO._id());
		}
		
		course.setName(courseDTO.name());
		course.setCategory(convertCategoryValue(courseDTO.category()));
		
		List<Lesson> lessons = courseDTO.lessons().stream().map(lessonDTO -> {
			var lesson = new Lesson();
			lesson.setId(lessonDTO.id());
			lesson.setName(lessonDTO.name());
			lesson.setYoutubeUrl(lessonDTO.youtubeUrl());
			lesson.setCourse(course);
			
			return lesson;
		}).toList();
		
		course.setLessons(lessons);
		
		return course;
	}
	
	public Category convertCategoryValue(String value) {
		if(value == null) {
			return null;
		}
		
		return switch(value) {
			case "Front-End" -> Category.FRONT_END;
			case "Back-End" -> Category.BACKE_END;
			default -> throw new IllegalArgumentException("Categoria inválida: " + value);
		};
	}
}
