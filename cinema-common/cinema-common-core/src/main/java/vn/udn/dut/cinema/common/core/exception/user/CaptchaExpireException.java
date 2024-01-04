package vn.udn.dut.cinema.common.core.exception.user;

import java.io.Serial;

/**
 * Verification code failure exception class
 *
 * @author HoaLD
 */
public class CaptchaExpireException extends UserException {

    @Serial
    private static final long serialVersionUID = 1L;

    public CaptchaExpireException() {
        super("user.jcaptcha.expire");
    }
}
