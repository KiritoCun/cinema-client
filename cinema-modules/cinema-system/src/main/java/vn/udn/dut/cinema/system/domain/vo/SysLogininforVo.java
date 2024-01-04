package vn.udn.dut.cinema.system.domain.vo;

import java.util.Date;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.cinema.common.excel.annotation.ExcelDictFormat;
import vn.udn.dut.cinema.common.excel.convert.ExcelDictConvert;
import vn.udn.dut.cinema.system.domain.SysLogininfor;

import java.io.Serial;
import java.io.Serializable;



/**
 * System access record view object sys_logininfor
 *
 * @author HoaLD
 * @date 2023-02-07
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysLogininfor.class)
public class SysLogininforVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * access ID
     */
    @ExcelProperty(value = "ID")
    private Long infoId;

    /**
     * tenant number
     */
    private String tenantId;

    /**
     * user account
     */
    @ExcelProperty(value = "User account")
    private String userName;

    /**
     * Login status (0 success, 1 failure)
     */
    @ExcelProperty(value = "Login status", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_common_status")
    private String status;

    /**
     * login IP address
     */
    @ExcelProperty(value = "Login address")
    private String ipaddr;

    /**
     * login location
     */
    @ExcelProperty(value = "Login location")
    private String loginLocation;

    /**
     * browser type
     */
    @ExcelProperty(value = "Browser")
    private String browser;

    /**
     * operating system
     */
    @ExcelProperty(value = "Operating system")
    private String os;


    /**
     * Prompt message
     */
    @ExcelProperty(value = "Message")
    private String msg;

    /**
     * Login time
     */
    @ExcelProperty(value = "Login time")
    private Date loginTime;


}
