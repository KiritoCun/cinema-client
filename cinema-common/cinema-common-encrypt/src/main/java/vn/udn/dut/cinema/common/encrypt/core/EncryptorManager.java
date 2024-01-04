package vn.udn.dut.cinema.common.encrypt.core;

import cn.hutool.core.util.ReflectUtil;
import vn.udn.dut.cinema.common.encrypt.annotation.EncryptField;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Encryption management class
 *
 * @author HoaLD
 * @version 4.6.0
 */
public class EncryptorManager {

    /**
     * Cache Encryptor
     */
    Map<EncryptContext, IEncryptor> encryptorMap = new ConcurrentHashMap<>();

    /**
     * class encrypted field cache
     */
    Map<Class<?>, Set<Field>> fieldCache = new ConcurrentHashMap<>();

    /**
     * Get class encrypted field cache
     */
    public Set<Field> getFieldCache(Class<?> sourceClazz) {
        return fieldCache.computeIfAbsent(sourceClazz, clazz -> {
            Field[] declaredFields = clazz.getDeclaredFields();
            Set<Field> fieldSet = Arrays.stream(declaredFields).filter(field ->
                    field.isAnnotationPresent(EncryptField.class) && field.getType() == String.class)
                .collect(Collectors.toSet());
            for (Field field : fieldSet) {
                field.setAccessible(true);
            }
            return fieldSet;
        });
    }

    /**
     * Register cryptographic executors to the cache
     *
     * @param encryptContext Relevant configuration parameters required by the encryption executor
     */
    public IEncryptor registAndGetEncryptor(EncryptContext encryptContext) {
        if (encryptorMap.containsKey(encryptContext)) {
            return encryptorMap.get(encryptContext);
        }
        IEncryptor encryptor = ReflectUtil.newInstance(encryptContext.getAlgorithm().getClazz(), encryptContext);
        encryptorMap.put(encryptContext, encryptor);
        return encryptor;
    }

    /**
     * Remove encrypted executors from cache
     *
     * @param encryptContext Relevant configuration parameters required by the encryption executor
     */
    public void removeEncryptor(EncryptContext encryptContext) {
        this.encryptorMap.remove(encryptContext);
    }

    /**
     * Encrypt according to configuration. The corresponding algorithm and corresponding secret key information will be cached locally.
     *
     * @param value          the value to encrypt
     * @param encryptContext Encryption related configuration information
     */
    public String encrypt(String value, EncryptContext encryptContext) {
        IEncryptor encryptor = this.registAndGetEncryptor(encryptContext);
        return encryptor.encrypt(value, encryptContext.getEncode());
    }

    /**
     * Decrypt according to configuration
     *
     * @param value          the value to be decrypted
     * @param encryptContext Encryption related configuration information
     */
    public String decrypt(String value, EncryptContext encryptContext) {
        IEncryptor encryptor = this.registAndGetEncryptor(encryptContext);
        return encryptor.decrypt(value);
    }

}
