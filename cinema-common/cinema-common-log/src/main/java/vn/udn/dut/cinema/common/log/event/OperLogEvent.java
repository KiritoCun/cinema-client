package vn.udn.dut.cinema.common.log.event;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * Operation Log Event
 *
 * @author HoaLD
 */

@Data
public class OperLogEvent implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * log primary key
     */
    private Long operId;

    /**
     * tenant ID
     */
    private String tenantId;

    /**
     * Operation module
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
     * request method
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
     * wrong information
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
