package vn.udn.dut.cinema.common.core.exception.user;

import java.io.Serial;

/**
 * Maximum number of user errors exception class
 *
 * @author HoaLD
 */
public class UserPasswordRetryLimitExceedException extends UserException {

    @Serial
    private static final long serialVersionUID = 1L;

    public UserPasswordRetryLimitExceedException(int retryLimitCount, int lockTime) {
        super("user.password.retry.limit.exceed", retryLimitCount, lockTime);
    }

}
