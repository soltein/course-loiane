package br.com.soltein.crudspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.soltein.crudspring.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long>{
    
}
