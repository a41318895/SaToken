package com.akichou.satokentest.service.impl;

import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.secure.SaBase64Util;
import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.util.SaResult;
import com.akichou.satokentest.entity.dto.DecryptInfoDto;
import com.akichou.satokentest.entity.dto.EncryptInfoDto;
import com.akichou.satokentest.entity.vo.RSAPrivateKeyVo;
import com.akichou.satokentest.enumeration.DecryptStrategyEnum;
import com.akichou.satokentest.enumeration.EncryptStrategyEnum;
import com.akichou.satokentest.enumeration.HttpCodeEnum;
import com.akichou.satokentest.functionalInterface.Supplier;
import com.akichou.satokentest.global.exception.SystemException;
import com.akichou.satokentest.service.TextEncryptService;
import com.akichou.satokentest.util.RSAUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.HashMap;

import static com.akichou.satokentest.constant.Constant.*;

@Service
@Slf4j
public class TextEncryptServiceImpl implements TextEncryptService {

    @Value("${sa.secure.encrypt.aes.key}")
    private String aesKey ;

    @Override
    public SaResult encryptText(EncryptInfoDto dto) {

        String rsaPrivateKey = "" ;
        String plainText = dto.getPlainText() ;
        EncryptStrategyEnum encryptStrategy = dto.getEncryptStrategy() ;

        String encryptedText = switch (encryptStrategy) {

            case MD5 -> SaSecureUtil.md5(plainText) ;

            case SHA1 -> SaSecureUtil.sha1(plainText) ;

            case SHA256 -> SaSecureUtil.sha256(plainText) ;

            case SHA384 -> SaSecureUtil.sha384(plainText) ;

            case SHA512 -> SaSecureUtil.sha512(plainText) ;

            case AES -> SaSecureUtil.aesEncrypt(aesKey, plainText) ;

            case RSA -> {

                HashMap<String, String> keyMap = execute(RSAUtils::generateKeyPair) ;

                rsaPrivateKey = keyMap.get(MAP_KEY_SIGN_PRIVATE) ;

                yield execute(() -> RSAUtils.encrypt(plainText, keyMap.get(MAP_KEY_SIGN_PUBLIC))) ;
            }

            case BASE64 -> SaBase64Util.encode(plainText) ;

            case BCRYPT -> {

                String strongSaltWithLogRounds12 = BCrypt.gensalt(HASH_SALT_LOG_ROUNDS) ;

                String hashedText = BCrypt.hashpw(plainText, strongSaltWithLogRounds12) ;

                yield hashedText ;
            }

            default -> throw new SystemException(HttpCodeEnum.NO_SUCH_ALGORITHM) ;
        } ;

        String result = getEncryptSuccessMessage(encryptedText, encryptStrategy) ;

        // Logging
        log.info(result) ;

        if (encryptStrategy == EncryptStrategyEnum.RSA) {

            SaResult saResult = new SaResult() ;
            saResult.setMsg(result) ;
            saResult.setData(new RSAPrivateKeyVo(rsaPrivateKey)) ;

            return saResult ;
        }
        return SaResult.ok(result) ;
    }

    @Override
    public SaResult decrypt(DecryptInfoDto dto) {

        String encryptedText = dto.getDecryptedText() ;
        DecryptStrategyEnum decryptStrategy = dto.getDecryptStrategy() ;
        String key = dto.getKey() ;

        String decryptedText = switch (decryptStrategy) {

            case AES -> SaSecureUtil.aesDecrypt(aesKey, encryptedText) ;

            case RSA -> execute(() -> RSAUtils.decrypt(encryptedText, key)) ;

            case BASE64 -> SaBase64Util.decode(encryptedText) ;

            default -> throw new SystemException(HttpCodeEnum.NO_SUCH_ALGORITHM) ;
        } ;

        String result = getDecryptSuccessMessage(decryptedText, decryptStrategy) ;

        // Logging
        log.info(result) ;

        return SaResult.ok(result) ;
    }

    private String getEncryptSuccessMessage(String encryptedText, EncryptStrategyEnum encryptStrategy) {

        return MessageFormat.format(ENCRYPT_SUCCESS_MESSAGE, encryptedText, encryptStrategy) ;
    }

    private String getDecryptSuccessMessage(String encryptedText, DecryptStrategyEnum decryptStrategy) {

        return MessageFormat.format(DECRYPT_SUCCESS_MESSAGE, encryptedText, decryptStrategy) ;
    }

    // To handle Exception throwing declaration on multiple methods situation
    private <V> V execute(Supplier<V> supplier) {
        
        try {

            return supplier.get() ;

        } catch (Exception e) {

            log.error(e.getMessage()) ;

            throw new RuntimeException(e) ;
        }
    }
}
