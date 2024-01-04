package vn.udn.dut.cinema.common.encrypt.core.encryptor;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SM4;
import vn.udn.dut.cinema.common.encrypt.core.EncryptContext;
import vn.udn.dut.cinema.common.encrypt.enumd.AlgorithmType;
import vn.udn.dut.cinema.common.encrypt.enumd.EncodeType;

import java.nio.charset.StandardCharsets;

/**
 * sm4 algorithm implementation
 *
 * @author HoaLD
 * @version 4.6.0
 */
public class Sm4Encryptor extends AbstractEncryptor {

    private final SM4 sm4;

    public Sm4Encryptor(EncryptContext context) {
        super(context);
        String password = context.getPassword();
        if (StrUtil.isBlank(password)) {
            throw new IllegalArgumentException("SM4 did not get the key information");
        }
        // The secret key requirement of the sm4 algorithm is 16 bits in length
        if (16 != password.length()) {
            throw new IllegalArgumentException("The length of the SM4 secret key should be 16 bits, but the actual value is " + password.length() + " bits");
        }
        this.sm4 = SmUtil.sm4(password.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * get the current algorithm
     */
    @Override
    public AlgorithmType algorithm() {
        return AlgorithmType.SM4;
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
            return sm4.encryptHex(value);
        } else {
            return sm4.encryptBase64(value);
        }
    }

    /**
     * decrypt
     *
     * @param value      string to be encrypted
     */
    @Override
    public String decrypt(String value) {
        return this.sm4.decryptStr(value);
    }
}
