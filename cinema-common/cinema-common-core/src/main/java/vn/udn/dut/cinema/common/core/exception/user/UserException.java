package vn.udn.dut.cinema.common.core.exception.user;

import java.io.Serial;

import vn.udn.dut.cinema.common.core.exception.base.BaseException;

/**
 * User information exception class
 *
 * @author HoaLD
 */
public class UserException extends BaseException {

    @Serial
    private static final long serialVersionUID = 1L;

    public UserException(String code, Object... args) {
        super("user", code, args, null);
    }
}
