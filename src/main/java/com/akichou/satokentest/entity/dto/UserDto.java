package com.akichou.satokentest.entity.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserDto {

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]+$")
    @Size(min = 6, max = 20)
    private String username ;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]+$")
    @Size(min = 6, max = 20)
    private String password ;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]+$")
    private String note ;

    @Min(0)
    @Max(120)
    private Integer age ;
}
