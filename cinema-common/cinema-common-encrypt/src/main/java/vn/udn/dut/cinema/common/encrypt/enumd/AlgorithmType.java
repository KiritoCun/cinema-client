package vn.udn.dut.cinema.common.encrypt.enumd;

import lombok.AllArgsConstructor;
import lombok.Getter;
import vn.udn.dut.cinema.common.encrypt.core.encryptor.*;

/**
 * algorithm name
 *
 * @author HoaLD
 * @version 4.6.0
 */
@Getter
@AllArgsConstructor
public enum AlgorithmType {

    /**
     * Default yml configuration
     */
    DEFAULT(null),

    /**
     * base64
     */
    BASE64(Base64Encryptor.class),

    /**
     * aes
     */
    AES(AesEncryptor.class),

    /**
     * rsa
     */
    RSA(RsaEncryptor.class),

    /**
     * sm2
     */
    SM2(Sm2Encryptor.class),

    /**
     * sm4
     */
    SM4(Sm4Encryptor.class);

    private final Class<? extends AbstractEncryptor> clazz;
}
