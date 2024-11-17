package com.akichou.satokentest.service;

import cn.dev33.satoken.util.SaResult;
import com.akichou.satokentest.entity.dto.DecryptInfoDto;
import com.akichou.satokentest.entity.dto.EncryptInfoDto;

public interface TextEncryptService {

    SaResult encryptText(EncryptInfoDto dto);

    SaResult decrypt(DecryptInfoDto dto);
}
