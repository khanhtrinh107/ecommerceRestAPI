package com.example.demo.entity.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    @NotNull(message = "id should not be null!" )
    @NotBlank(message = "id should not be blank!")
    private Integer productId;
    @NotNull(message = "id should not be null!" )
    @NotBlank(message = "id should not be blank!")
    private String productName;
    @Min(0)
    private float price;
    @Min(0)
    private int quantity;
}
