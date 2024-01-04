package vn.udn.dut.cinema.common.encrypt.core;

import vn.udn.dut.cinema.common.encrypt.enumd.AlgorithmType;
import vn.udn.dut.cinema.common.encrypt.enumd.EncodeType;

/**
 * Decomposer
 *
 * @author HoaLD
 * @version 4.6.0
 */
public interface IEncryptor {

    /**
     * get the current algorithm
     */
    AlgorithmType algorithm();

    /**
     * encryption
     *
     * @param value      string to be encrypted
     * @param encodeType Encrypted encoding format
     * @return encrypted string
     */
    String encrypt(String value, EncodeType encodeType);

    /**
     * decrypt
     *
     * @param value      string to be encrypted
     * @return decrypted string
     */
    String decrypt(String value);
}
