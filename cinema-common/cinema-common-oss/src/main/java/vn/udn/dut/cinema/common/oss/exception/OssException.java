package vn.udn.dut.cinema.common.oss.exception;

import java.io.Serial;

/**
 * OSS exception class
 *
 * @author HoaLD
 */
public class OssException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public OssException(String msg) {
        super(msg);
    }

}
