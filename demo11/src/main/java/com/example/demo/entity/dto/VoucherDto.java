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
public class VoucherDto {
    @NotNull(message = "code should not be null!")
    @NotBlank(message = "code should not be blank!")
    private String code;
    @NotNull(message = "persen should not be blank!")
    @Min(0)
    private int persen;
}
