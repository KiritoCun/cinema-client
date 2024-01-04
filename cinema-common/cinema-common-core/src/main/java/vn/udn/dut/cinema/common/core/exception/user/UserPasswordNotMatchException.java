package vn.udn.dut.cinema.common.core.exception.user;

import java.io.Serial;

/**
 * The user password is incorrect or does not meet the specification exception class
 *
 * @author HoaLD
 */
public class UserPasswordNotMatchException extends UserException {

    @Serial
    private static final long serialVersionUID = 1L;

    public UserPasswordNotMatchException() {
        super("user.password.not.match");
    }
}
