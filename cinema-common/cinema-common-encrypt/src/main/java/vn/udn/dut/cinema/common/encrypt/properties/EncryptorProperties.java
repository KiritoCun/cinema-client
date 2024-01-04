package vn.udn.dut.cinema.common.encrypt.properties;

import lombok.Data;
import vn.udn.dut.cinema.common.encrypt.enumd.AlgorithmType;
import vn.udn.dut.cinema.common.encrypt.enumd.EncodeType;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Encryption and decryption attribute configuration class
 *
 * @author HoaLD
 * @version 4.6.0
 */
@Data
@ConfigurationProperties(prefix = "mybatis-encryptor")
public class EncryptorProperties {

    /**
     * filter switch
     */
    private Boolean enable;

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
