package com.akichou.satokentest.entity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserNoteDto {

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9\\s]+$")
    private String noteContent ;
}
