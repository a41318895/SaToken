package com.akichou.satokentest.entity.dto;

import com.akichou.satokentest.enumeration.EncryptStrategyEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EncryptInfoDto {

    @NotBlank
    private String plainText ;

    @NotNull
    private EncryptStrategyEnum encryptStrategy ;
}
