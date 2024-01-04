package vn.udn.dut.cinema.common.excel.core;

import java.util.List;

/**
 * excel return object
 *
 * @author HoaLD
 */
public interface ExcelResult<T> {

    /**
     * object list
     */
    List<T> getList();

    /**
     * error list
     */
    List<String> getErrorList();

    /**
     * import receipt
     */
    String getAnalysis();
}
