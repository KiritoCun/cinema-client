package vn.udn.dut.cinema.common.encrypt.core.encryptor;

import vn.udn.dut.cinema.common.encrypt.core.EncryptContext;
import vn.udn.dut.cinema.common.encrypt.core.IEncryptor;

/**
 * Base class for all cryptographic executors
 *
 * @author HoaLD
 * @version 4.6.0
 */
public abstract class AbstractEncryptor implements IEncryptor {

    public AbstractEncryptor(EncryptContext context) {
        // User configuration verification and configuration injection
    }
}
