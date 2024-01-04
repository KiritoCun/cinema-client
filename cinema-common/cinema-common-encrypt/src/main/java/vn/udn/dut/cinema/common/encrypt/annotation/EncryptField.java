package vn.udn.dut.cinema.common.encrypt.annotation;

import java.lang.annotation.*;

import vn.udn.dut.cinema.common.encrypt.enumd.AlgorithmType;
import vn.udn.dut.cinema.common.encrypt.enumd.EncodeType;

/**
 * Field encryption annotation
 *
 * @author HoaLD
 */
@Documented
@Inherited
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EncryptField {

    /**
     * Encryption Algorithm
     */
    AlgorithmType algorithm() default AlgorithmType.DEFAULT;

    /**
     * Secret key. AES, SM4 required
     */
    String password() default "";

    /**
     * public key. RSA, SM2 required
     */
    String publicKey() default "";

    /**
     * public key. RSA, SM2 required
     */
    String privateKey() default "";

    /**
     * Encoding. Does not work for encryption algorithm BASE64
     */
    EncodeType encode() default EncodeType.DEFAULT;

}
