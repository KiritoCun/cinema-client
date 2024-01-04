package vn.udn.dut.cinema.common.core.exception;

import java.io.Serial;

/**
 * global exception
 *
 * @author HoaLD
 */
public class GlobalException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Error message
     */
    private String message;

    /**
     * Error details, internal debugging errors
     */
    private String detailMessage;

    /**
     * Empty constructor to avoid deserialization problems
     */
    public GlobalException() {
    }

    public GlobalException(String message) {
        this.message = message;
    }

    public String getDetailMessage() {
        return detailMessage;
    }

    public GlobalException setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
        return this;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public GlobalException setMessage(String message) {
        this.message = message;
        return this;
    }
}
