package com.akichou.satokentest.entity.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserUpdateDto {

    @NotNull
    private Long id ;

    @Min(0)
    @Max(120)
    private Integer age ;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]+$")
    private String note ;
}
