package vn.udn.dut.cinema.common.encrypt.core.encryptor;

import cn.hutool.core.codec.Base64;
import vn.udn.dut.cinema.common.encrypt.core.EncryptContext;
import vn.udn.dut.cinema.common.encrypt.enumd.AlgorithmType;
import vn.udn.dut.cinema.common.encrypt.enumd.EncodeType;

/**
 * Base64 algorithm implementation
 *
 * @author HoaLD
 * @version 4.6.0
 */
public class Base64Encryptor extends AbstractEncryptor {

    public Base64Encryptor(EncryptContext context) {
        super(context);
    }

    /**
     * get the current algorithm
     */
    @Override
    public AlgorithmType algorithm() {
        return AlgorithmType.BASE64;
    }

    /**
     * encryption
     *
     * @param value      string to be encrypted
     * @param encodeType Encrypted encoding format
     */
    @Override
    public String encrypt(String value, EncodeType encodeType) {
        return Base64.encode(value);
    }

    /**
     * decrypt
     *
     * @param value      string to be encrypted
     */
    @Override
    public String decrypt(String value) {
        return Base64.decodeStr(value);
    }
}
