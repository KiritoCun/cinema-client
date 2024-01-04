package vn.udn.dut.cinema.common.encrypt.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.asymmetric.SM2;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Security related tools
 *
 * @author HoaLD
 */
public class EncryptUtils {
    /**
     * public key
     */
    public static final String PUBLIC_KEY = "publicKey";
    /**
     * private key
     */
    public static final String PRIVATE_KEY = "privateKey";

    /**
     * Base64 encryption
     *
     * @param data data to be encrypted
     * @return encrypted string
     */
    public static String encryptByBase64(String data) {
        return Base64.encode(data, StandardCharsets.UTF_8);
    }

    /**
     * Base64 decryption
     *
     * @param data data to be decrypted
     * @return decrypted string
     */
    public static String decryptByBase64(String data) {
        return Base64.decodeStr(data, StandardCharsets.UTF_8);
    }

    /**
     * AES encryption
     *
     * @param data     data to be decrypted
     * @param password key string
     * @return Encrypted string, encoded with Base64
     */
    public static String encryptByAes(String data, String password) {
        if (StrUtil.isBlank(password)) {
            throw new IllegalArgumentException("AES needs to pass in the secret key information");
        }
        // The secret key requirements of the aes algorithm are 16 bits, 24 bits, and 32 bits
        int[] array = {16, 24, 32};
        if (!ArrayUtil.contains(array, password.length())) {
            throw new IllegalArgumentException("AES key length requirements are 16 bits, 24 bits, 32 bits");
        }
        return SecureUtil.aes(password.getBytes(StandardCharsets.UTF_8)).encryptBase64(data, StandardCharsets.UTF_8);
    }

    /**
     * AES decryption
     *
     * @param data     data to be decrypted
     * @param password key string
     * @return decrypted string
     */
    public static String decryptByAes(String data, String password) {
        if (StrUtil.isBlank(password)) {
            throw new IllegalArgumentException("AES needs to pass in the secret key information");
        }
        // The secret key requirements of the aes algorithm are 16 bits, 24 bits, and 32 bits
        int[] array = {16, 24, 32};
        if (!ArrayUtil.contains(array, password.length())) {
            throw new IllegalArgumentException("AES key length requirements are 16 bits, 24 bits, 32 bits");
        }
        return SecureUtil.aes(password.getBytes(StandardCharsets.UTF_8)).decryptStr(data, StandardCharsets.UTF_8);
    }

    /**
     * sm4 encryption
     *
     * @param data     data to be encrypted
     * @param password key string
     * @return Encrypted string, encoded with Base64
     */
    public static String encryptBySm4(String data, String password) {
        if (StrUtil.isBlank(password)) {
            throw new IllegalArgumentException("SM4 needs to pass in the secret key information");
        }
        // The secret key requirement of the sm4 algorithm is 16 bits in length
        int sm4PasswordLength = 16;
        if (sm4PasswordLength != password.length()) {
            throw new IllegalArgumentException("The length of the SM4 secret key is required to be 16 bits");
        }
        return SmUtil.sm4(password.getBytes(StandardCharsets.UTF_8)).encryptBase64(data, StandardCharsets.UTF_8);
    }

    /**
     * sm4 decryption
     *
     * @param data     data to be decrypted
     * @param password key string
     * @return decrypted string
     */
    public static String decryptBySm4(String data, String password) {
        if (StrUtil.isBlank(password)) {
            throw new IllegalArgumentException("SM4 needs to pass in the secret key information");
        }
        // The secret key requirement of the sm4 algorithm is 16 bits in length
        int sm4PasswordLength = 16;
        if (sm4PasswordLength != password.length()) {
            throw new IllegalArgumentException("The length of the SM4 secret key is required to be 16 bits");
        }
        return SmUtil.sm4(password.getBytes(StandardCharsets.UTF_8)).decryptStr(data, StandardCharsets.UTF_8);
    }

    /**
     * Generate the public and private keys required for sm2 encryption and decryption
     *
     * @return Public-private keyMap
     */
    public static Map<String, String> generateSm2Key() {
        Map<String, String> keyMap = new HashMap<>(2);
        SM2 sm2 = SmUtil.sm2();
        keyMap.put(PRIVATE_KEY, sm2.getPrivateKeyBase64());
        keyMap.put(PUBLIC_KEY, sm2.getPublicKeyBase64());
        return keyMap;
    }

    /**
     * sm2 public key encryption
     *
     * @param data      data to be encrypted
     * @param publicKey public key
     * @return Encrypted string, encoded with Base64
     */
    public static String encryptBySm2(String data, String publicKey) {
        if (StrUtil.isBlank(publicKey)) {
            throw new IllegalArgumentException("SM2 needs to pass in the public key for encryption");
        }
        SM2 sm2 = SmUtil.sm2(null, publicKey);
        return sm2.encryptBase64(data, StandardCharsets.UTF_8, KeyType.PublicKey);
    }

    /**
     * sm2 private key decryption
     *
     * @param data       data to be encrypted
     * @param privateKey private key
     * @return decrypted string
     */
    public static String decryptBySm2(String data, String privateKey) {
        if (StrUtil.isBlank(privateKey)) {
            throw new IllegalArgumentException("SM2 needs to pass in the private key for decryption");
        }
        SM2 sm2 = SmUtil.sm2(privateKey, null);
        return sm2.decryptStr(data, KeyType.PrivateKey, StandardCharsets.UTF_8);
    }

    /**
     * Generate the public and private keys required for RSA encryption and decryption
     *
     * @return Public-private keyMap
     */
    public static Map<String, String> generateRsaKey() {
        Map<String, String> keyMap = new HashMap<>(2);
        RSA rsa = SecureUtil.rsa();
        keyMap.put(PRIVATE_KEY, rsa.getPrivateKeyBase64());
        keyMap.put(PUBLIC_KEY, rsa.getPublicKeyBase64());
        return keyMap;
    }

    /**
     * rsa public key encryption
     *
     * @param data      data to be encrypted
     * @param publicKey public key
     * @return Encrypted string, encoded with Base64
     */
    public static String encryptByRsa(String data, String publicKey) {
        if (StrUtil.isBlank(publicKey)) {
            throw new IllegalArgumentException("RSA needs to pass in the public key for encryption");
        }
        RSA rsa = SecureUtil.rsa(null, publicKey);
        return rsa.encryptBase64(data, StandardCharsets.UTF_8, KeyType.PublicKey);
    }

    /**
     * rsa private key decryption
     *
     * @param data       data to be encrypted
     * @param privateKey private key
     * @return decrypted string
     */
    public static String decryptByRsa(String data, String privateKey) {
        if (StrUtil.isBlank(privateKey)) {
            throw new IllegalArgumentException("RSA needs to pass in the private key for decryption");
        }
        RSA rsa = SecureUtil.rsa(privateKey, null);
        return rsa.decryptStr(data, KeyType.PrivateKey, StandardCharsets.UTF_8);
    }

    /**
     * md5 encryption
     *
     * @param data data to be encrypted
     * @return Encrypted string, using Hex encoding
     */
    public static String encryptByMd5(String data) {
        return SecureUtil.md5(data);
    }

    /**
     * sha256 encryption
     *
     * @param data data to be encrypted
     * @return Encrypted string, using Hex encoding
     */
    public static String encryptBySha256(String data) {
        return SecureUtil.sha256(data);
    }

    /**
     * sm3 encryption
     *
     * @param data data to be encrypted
     * @return Encrypted string, using Hex encoding
     */
    public static String encryptBySm3(String data) {
        return SmUtil.sm3(data);
    }

}
