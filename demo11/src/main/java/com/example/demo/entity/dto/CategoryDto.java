package com.example.demo.entity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    @NotNull(message = "category name should not be null!")
    @NotBlank(message = "category name should not be blank!")
    @Length(min = 5, max = 100)
    private String categoryName;
    @NotBlank(message = "description should not be blank!")
    @NotNull(message = "description should not be null!")
    @Length(min = 5 , max = 1000)
    private String description;
}
