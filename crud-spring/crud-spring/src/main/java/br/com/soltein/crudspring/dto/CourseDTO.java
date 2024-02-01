package br.com.soltein.crudspring.dto;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import br.com.soltein.crudspring.enums.Category;
import br.com.soltein.crudspring.enums.validation.ValueOfEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CourseDTO(
		Long _id, 
		
		@NotBlank
		@NotNull
		@Length(min= 5, max = 200)
		String name,
		
		@NotBlank
		@NotNull
		@Length(max = 20) 
		//@Pattern(regexp = "Back-End|Front-End")
		@ValueOfEnum(enumClass = Category.class)
		String category,
		
	    @NotEmpty
	    @Valid
		List<LessonDTO> lessons
		) {
	
		

}
