package vn.udn.dut.cinema.common.excel.core;

import com.alibaba.excel.read.listener.ReadListener;

/**
 * Excel import listener
 *
 * @author HoaLD
 */
public interface ExcelListener<T> extends ReadListener<T> {

    ExcelResult<T> getExcelResult();

}
