package vn.udn.dut.cinema.system.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import io.github.linpeilie.annotations.AutoMappers;
import lombok.Data;
import vn.udn.dut.cinema.common.log.event.OperLogEvent;
import vn.udn.dut.cinema.system.domain.SysOperLog;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Operational logging business object sys_oper_log
 *
 * @author HoaLD
 * @date 2023-02-07
 */

@Data
@AutoMappers({
    @AutoMapper(target = SysOperLog.class, reverseConvertGenerate = false),
    @AutoMapper(target = OperLogEvent.class)
})
public class SysOperLogBo {

    /**
     * log primary key
     */
    private Long operId;

    /**
     * tenant number
     */
    private String tenantId;

    /**
     * module title
     */
    private String title;

    /**
     * Business Type (0 Others 1 Add 2 Modify 3 Delete)
     */
    private Integer businessType;

    /**
     * business type array
     */
    private Integer[] businessTypes;

    /**
     * method name
     */
    private String method;

    /**
     * request method
     */
    private String requestMethod;

    /**
     * Operation category (0 others 1 background user 2 mobile terminal user)
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
     * Request URL
     */
    private String operUrl;

    /**
     * host address
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

    /**
     * request parameters
     */
    private Map<String, Object> params = new HashMap<>();

}
