package vn.udn.dut.cinema.common.encrypt.core.encryptor;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import vn.udn.dut.cinema.common.encrypt.core.EncryptContext;
import vn.udn.dut.cinema.common.encrypt.enumd.AlgorithmType;
import vn.udn.dut.cinema.common.encrypt.enumd.EncodeType;

import java.nio.charset.StandardCharsets;

/**
 * AES algorithm implementation
 *
 * @author HoaLD
 * @version 4.6.0
 */
public class AesEncryptor extends AbstractEncryptor {

    private final AES aes;

    public AesEncryptor(EncryptContext context) {
        super(context);
        String password = context.getPassword();
        if (StrUtil.isBlank(password)) {
            throw new IllegalArgumentException("AES did not get the key information");
        }
        // The secret key requirements of the aes algorithm are 16 bits, 24 bits, and 32 bits
        int[] array = {16, 24, 32};
        if (!ArrayUtil.contains(array, password.length())) {
            throw new IllegalArgumentException("AES key length should be 16 bits, 24 bits, 32 bits, the actual value is " + password.length() + " bits");
        }
        aes = SecureUtil.aes(context.getPassword().getBytes(StandardCharsets.UTF_8));
    }

    /**
     * get the current algorithm
     */
    @Override
    public AlgorithmType algorithm() {
        return AlgorithmType.AES;
    }

    /**
     * encryption
     *
     * @param value      string to be encrypted
     * @param encodeType Encrypted encoding format
     */
    @Override
    public String encrypt(String value, EncodeType encodeType) {
        if (encodeType == EncodeType.HEX) {
            return aes.encryptHex(value);
        } else {
            return aes.encryptBase64(value);
        }
    }

    /**
     * decrypt
     *
     * @param value      string to be encrypted
     */
    @Override
    public String decrypt(String value) {
        return this.aes.decryptStr(value);
    }
}
