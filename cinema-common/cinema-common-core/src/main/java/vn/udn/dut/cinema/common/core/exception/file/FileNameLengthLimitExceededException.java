package vn.udn.dut.cinema.common.core.exception.file;

import java.io.Serial;

/**
 * File name super long limit exception class
 *
 * @author HoaLD
 */
public class FileNameLengthLimitExceededException extends FileException {

    @Serial
    private static final long serialVersionUID = 1L;

    public FileNameLengthLimitExceededException(int defaultFileNameLength) {
        super("upload.filename.exceed.length", new Object[]{defaultFileNameLength});
    }
}
