package vn.udn.dut.cinema.system.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * Operational logging table oper_log
 *
 * @author HoaLD
 */

@Data
@TableName("sys_oper_log")
public class SysOperLog implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * log primary key
     */
    @TableId(value = "oper_id")
    private Long operId;

    /**
     * tenant number
     */
    private String tenantId;

    /**
     * Operation module
     */
    private String title;

    /**
     * Business Type (0 Others 1 Add 2 Edit 3 Delete)
     */
    private Integer businessType;

    /**
     * request method
     */
    private String method;

    /**
     * request method
     */
    private String requestMethod;

    /**
     * Operation category (0 others 1 system user 2 mobile user)
     */
    private Integer operatorType;

    /**
     * operator
     */
    private String operName;

    /**
     * Department name
     */
    private String deptName;

    /**
     * request url
     */
    private String operUrl;

    /**
     * operation address
     */
    private String operIp;

    /**
     * operating location
     */
    private String operLocation;

    /**
     * request parameters
     */
    private String operParam;

    /**
     * return parameter
     */
    private String jsonResult;

    /**
     * Operating status (0 normal 1 abnormal)
     */
    private Integer status;

    /**
     * Error message
     */
    private String errorMsg;

    /**
     * operating time
     */
    private Date operTime;

    /**
     * time consuming
     */
    private Long costTime;

    /**
     * System type
     */
    private String systemType;
}
