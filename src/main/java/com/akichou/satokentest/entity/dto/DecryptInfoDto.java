package com.akichou.satokentest.entity.dto;

import com.akichou.satokentest.enumeration.DecryptStrategyEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DecryptInfoDto {

    @NotBlank
    private String decryptedText ;

    private String key ;

    @NotNull
    private DecryptStrategyEnum decryptStrategy ;
}
