package com.example.demo.entity.dto;

import com.example.demo.entity.Category;
import com.example.demo.entity.OrderDetail;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    @NotNull(message = "product name should not be null!")
    @NotBlank(message = "product name should not be blank!")
    @Length(min = 5 , max = 100)
    private String productName;
    @Min(0)
    private String price;
    @NotBlank(message = "image should not be blank!")
    @NotNull(message = "image should not be null!")
    private String image;
    @NotNull(message = "description should not be null!")
    @NotBlank(message = "description should not be blank!")
    @Length(min = 5 , max = 100)
    private String description;
    @NotNull(message = "category should not be null!")
    @NotBlank(message = "category should not be blank!")
    @Length(min = 5 , max = 100)
    private String category;
    private Set<OrderDetail> orderDetails;
}
