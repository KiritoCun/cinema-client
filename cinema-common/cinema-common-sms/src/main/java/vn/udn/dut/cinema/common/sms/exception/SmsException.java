package vn.udn.dut.cinema.common.sms.exception;

import java.io.Serial;

/**
 * Sms exception class
 *
 * @author HoaLD
 */
public class SmsException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public SmsException(String msg) {
        super(msg);
    }

}
