package vn.udn.dut.cinema.common.encrypt.core;

import lombok.Data;
import vn.udn.dut.cinema.common.encrypt.enumd.AlgorithmType;
import vn.udn.dut.cinema.common.encrypt.enumd.EncodeType;

/**
 * The encryption context is used to pass the necessary parameters to the encryptor.
 *
 * @author HoaLD
 * @version 4.6.0
 */
@Data
public class EncryptContext {

    /**
     * default algorithm
     */
    private AlgorithmType algorithm;

    /**
     * security key
     */
    private String password;

    /**
     * public key
     */
    private String publicKey;

    /**
     * private key
     */
    private String privateKey;

    /**
     * Encoding method, base64/hex
     */
    private EncodeType encode;

}
