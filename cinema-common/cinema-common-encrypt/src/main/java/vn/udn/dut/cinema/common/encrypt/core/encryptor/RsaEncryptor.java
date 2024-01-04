package vn.udn.dut.cinema.common.encrypt.core.encryptor;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import vn.udn.dut.cinema.common.core.utils.StringUtils;
import vn.udn.dut.cinema.common.encrypt.core.EncryptContext;
import vn.udn.dut.cinema.common.encrypt.enumd.AlgorithmType;
import vn.udn.dut.cinema.common.encrypt.enumd.EncodeType;


/**
 * RSA algorithm implementation
 *
 * @author HoaLD
 * @version 4.6.0
 */
public class RsaEncryptor extends AbstractEncryptor {

    private final RSA rsa;

    public RsaEncryptor(EncryptContext context) {
        super(context);
        String privateKey = context.getPrivateKey();
        String publicKey = context.getPublicKey();
        if (StringUtils.isAnyEmpty(privateKey, publicKey)) {
            throw new IllegalArgumentException("Both RSA public and private keys need to be provided. The public key encrypts and the private key decrypts.");
        }
        this.rsa = SecureUtil.rsa(Base64.decode(privateKey), Base64.decode(publicKey));
    }

    /**
     * get the current algorithm
     */
    @Override
    public AlgorithmType algorithm() {
        return AlgorithmType.RSA;
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
            return rsa.encryptHex(value, KeyType.PublicKey);
        } else {
            return rsa.encryptBase64(value, KeyType.PublicKey);
        }
    }

    /**
     * decrypt
     *
     * @param value      string to be encrypted
     */
    @Override
    public String decrypt(String value) {
        return this.rsa.decryptStr(value, KeyType.PrivateKey);
    }
}
