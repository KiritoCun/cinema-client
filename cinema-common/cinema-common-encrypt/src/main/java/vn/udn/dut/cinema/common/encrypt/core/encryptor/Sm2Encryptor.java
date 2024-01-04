package vn.udn.dut.cinema.common.encrypt.core.encryptor;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import vn.udn.dut.cinema.common.core.utils.StringUtils;
import vn.udn.dut.cinema.common.encrypt.core.EncryptContext;
import vn.udn.dut.cinema.common.encrypt.enumd.AlgorithmType;
import vn.udn.dut.cinema.common.encrypt.enumd.EncodeType;

/**
 * sm2 algorithm implementation
 *
 * @author HoaLD
 * @version 4.6.0
 */
public class Sm2Encryptor extends AbstractEncryptor {

    private final SM2 sm2;

    public Sm2Encryptor(EncryptContext context) {
        super(context);
        String privateKey = context.getPrivateKey();
        String publicKey = context.getPublicKey();
        if (StringUtils.isAnyEmpty(privateKey, publicKey)) {
            throw new IllegalArgumentException("Both SM2 public and private keys need to be provided, the public key encrypts and the private key decrypts.");
        }
        this.sm2 = SmUtil.sm2(Base64.decode(privateKey), Base64.decode(publicKey));
    }

    /**
     * get the current algorithm
     */
    @Override
    public AlgorithmType algorithm() {
        return AlgorithmType.SM2;
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
            return sm2.encryptHex(value, KeyType.PublicKey);
        } else {
            return sm2.encryptBase64(value, KeyType.PublicKey);
        }
    }

    /**
     * decrypt
     *
     * @param value      string to be encrypted
     */
    @Override
    public String decrypt(String value) {
        return this.sm2.decryptStr(value, KeyType.PrivateKey);
    }
}
