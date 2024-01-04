package vn.udn.dut.cinema.common.core.exception.user;

import java.io.Serial;

/**
 * Verification code error exception class
 *
 * @author HoaLD
 */
public class CaptchaException extends UserException {

    @Serial
    private static final long serialVersionUID = 1L;

    public CaptchaException() {
        super("user.jcaptcha.error");
    }
}
