package com.akichou.satokentest.entity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserIdAndDeviceDto {

    @NotNull
    private Long userId ;

    @NotBlank
    private String device ;
}
