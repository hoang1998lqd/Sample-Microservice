package com.regalite.userservice.utils;


import lombok.extern.slf4j.Slf4j;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author FISES-HoangVH15
 * Sử dụng thuật toán AES để mã hóa dữ liệu
 */
@Slf4j
public class AESAlgorithmCode {
    private static final String ALGO = "AES";
    // SHA256 Secret Key: Regalite; Text: HoangVH15
    private static final byte[] KEY_VALUE = "8f2bca486cf4aa94a1cf970ef8d66d239581f1525ec4d95b90f2952f2db90276".getBytes();

    public static String encode(String data) throws Exception {
        SecretKeySpec key = new SecretKeySpec(KEY_VALUE, ALGO);
        Cipher cipher = Cipher.getInstance(ALGO);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    public static String decode(String encodeData) {
        SecretKeySpec key = new SecretKeySpec(KEY_VALUE, ALGO);
        try {
            Cipher cipher = Cipher.getInstance(ALGO);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decodedData = Base64.getDecoder().decode(encodeData);
            byte[] decryptedData = cipher.doFinal(decodedData);
            return new String(decryptedData);
        } catch (InvalidKeyException | NoSuchAlgorithmException |
                 BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException exception) {
            log.error("Error occurred during Decode: Message - {}", exception.getMessage());
        }
        return null;
    }

}
