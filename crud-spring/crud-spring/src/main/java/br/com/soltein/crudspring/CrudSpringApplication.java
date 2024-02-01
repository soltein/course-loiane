package br.com.soltein.crudspring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.com.soltein.crudspring.enums.Category;
import br.com.soltein.crudspring.model.Course;
import br.com.soltein.crudspring.model.Lesson;
import br.com.soltein.crudspring.repository.CourseRepository;

@SpringBootApplication
public class CrudSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudSpringApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(CourseRepository courseRepository){
		return args -> {
			courseRepository.deleteAll();
			Course c = new Course();
			c.setName("Angular");
			c.setCategory(Category.FRONT_END);

			Lesson lesson = new Lesson();
			lesson.setName("Teste");
			lesson.setYoutubeUrl("youtube123");
			lesson.setCourse(c);
			
			c.getLessons().add(lesson);
			
			Lesson lesson1 = new Lesson();
			lesson1.setName("Teste1");
			lesson1.setYoutubeUrl("youtube112");
			lesson1.setCourse(c);
			
			c.getLessons().add(lesson1);			
			
			courseRepository.save(c);
			
			
		};
	}

}
