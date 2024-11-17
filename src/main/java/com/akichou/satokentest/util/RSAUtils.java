package com.akichou.satokentest.util;

import com.akichou.satokentest.enumeration.HttpCodeEnum;
import com.akichou.satokentest.global.exception.SystemException;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;

import static com.akichou.satokentest.constant.Constant.*;

public class RSAUtils {

    private static final String ALGORITHM_NAME = ALGORITHM_NAME_IN_RSA_UTILS ;

    private static final int KEY_SIZE = KEY_PAIR_GENERATE_SIZE ;

    private static final Base64.Encoder encoder = Base64.getEncoder() ;

    private static final Base64.Decoder decoder = Base64.getDecoder() ;

    /**
     * Generate RSA key pair
     */
    public static HashMap<String, String> generateKeyPair() throws Exception {

        // Create a generator to generate key pair for RSA Algorithm
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM_NAME) ;

        // Initialize the generator with a size of key and a random number generator object
        keyPairGenerator.initialize(KEY_SIZE, new SecureRandom()) ;

        // Do generating key pair
        KeyPair keyPair = keyPairGenerator.generateKeyPair() ;

        // Get public and private key separately
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic() ;
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate() ;

        // Make byte[] to Base64 String, then store in a Map
        HashMap<String, String> keyMap = new HashMap<>(2) ;
        keyMap.put(MAP_KEY_SIGN_PRIVATE, encoder.encodeToString(privateKey.getEncoded())) ;
        keyMap.put(MAP_KEY_SIGN_PUBLIC, encoder.encodeToString(publicKey.getEncoded())) ;

        return keyMap ;
    }

    /**
     * Encrypt With public key
     */
    public static String encrypt(String plainText, String publicKeyString) throws Exception {

        // Decode from Base64 public key string to byte[]
        byte[] publicKeyBytes = decoder.decode(publicKeyString) ;

        // Use RSA KeyFactory to transfer byte[] to PublicKey Object
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes) ;
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_NAME) ;
        PublicKey publicKey = keyFactory.generatePublic(keySpec) ;

        // Init RSA Cipher in order to encrypt with PublicKey Object
        Cipher cipher = Cipher.getInstance(ALGORITHM_NAME) ;
        cipher.init(Cipher.ENCRYPT_MODE, publicKey) ;

        // Use RSA Cipher to encrypt the byte[] of plainText
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes()) ;

        // Make byte[] to Base64 String
        return encoder.encodeToString(encryptedBytes) ;
    }

    /**
     * Decrypt With private key
     */
    public static String decrypt(String encryptedText, String privateKeyString) throws Exception {

        if (privateKeyString == null || privateKeyString.trim().isEmpty()) throw new SystemException(HttpCodeEnum.RSA_KEY_SHOULD_EXIST) ;

        // Decode from Base64 private key string to byte[]
        byte[] privateKeyBytes = decoder.decode(privateKeyString) ;

        // Use RSA KeyFactory to transfer byte[] to PrivateKey Object
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes) ;
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_NAME) ;
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec) ;

        // Init RSA Cipher in order to decrypt with PrivateKey Object
        Cipher cipher = Cipher.getInstance(ALGORITHM_NAME) ;
        cipher.init(Cipher.DECRYPT_MODE, privateKey) ;

        // Use RSA Cipher to decrypt the encryptedText
        byte[] decryptedBytes = cipher.doFinal(decoder.decode(encryptedText)) ;

        return new String(decryptedBytes) ;
    }
}
