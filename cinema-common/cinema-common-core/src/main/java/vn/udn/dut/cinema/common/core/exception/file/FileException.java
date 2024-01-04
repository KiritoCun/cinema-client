package vn.udn.dut.cinema.common.core.exception.file;

import java.io.Serial;

import vn.udn.dut.cinema.common.core.exception.base.BaseException;

/**
 * File Information Exception Class
 *
 * @author HoaLD
 */
public class FileException extends BaseException {

    @Serial
    private static final long serialVersionUID = 1L;

    public FileException(String code, Object[] args) {
        super("file", code, args, null);
    }

}
