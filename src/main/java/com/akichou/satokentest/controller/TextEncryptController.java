package com.akichou.satokentest.controller;

import cn.dev33.satoken.util.SaResult;
import com.akichou.satokentest.entity.dto.DecryptInfoDto;
import com.akichou.satokentest.entity.dto.EncryptInfoDto;
import com.akichou.satokentest.service.TextEncryptService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/encryption")
@RequiredArgsConstructor
public class TextEncryptController {

    private final TextEncryptService textEncryptService ;

    @PostMapping
    public SaResult encrypt(@Validated @RequestBody EncryptInfoDto dto) {

        return textEncryptService.encryptText(dto) ;
    }

    @PostMapping("/decryption")
    public SaResult decrypt(@Validated @RequestBody DecryptInfoDto dto) {

        return textEncryptService.decrypt(dto) ;
    }
}
