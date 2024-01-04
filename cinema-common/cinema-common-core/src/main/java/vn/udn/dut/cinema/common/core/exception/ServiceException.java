package vn.udn.dut.cinema.common.core.exception;

import java.io.Serial;

/**
 * Business exception
 *
 * @author HoaLD
 */
public final class ServiceException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * error code
     */
    private Integer code;

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
    public ServiceException() {
    }

    public ServiceException(String message) {
        this.message = message;
    }

    public ServiceException(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    public String getDetailMessage() {
        return detailMessage;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }

    public ServiceException setMessage(String message) {
        this.message = message;
        return this;
    }

    public ServiceException setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
        return this;
    }
}
