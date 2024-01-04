package vn.udn.dut.cinema.system.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.cinema.common.mybatis.core.domain.BaseEntity;

import java.io.Serial;
import java.util.Date;

/**
 * Tenant object sys_tenant
 *
 * @author HoaLD
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_tenant")
public class SysTenant extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * tenant number
     */
    private String tenantId;

    /**
     * contact person
     */
    private String contactUserName;

    /**
     * contact person
     */
    private String contactPhone;

    /**
     * Company Name
     */
    private String companyName;

    /**
     * Unified Social Credit Code
     */
    private String licenseNumber;

    /**
     * address
     */
    private String address;

    /**
     * domain name
     */
    private String domain;

    /**
     * company profile
     */
    private String intro;

    /**
     * Remark
     */
    private String remark;

    /**
     * Tenant Package Number
     */
    private Long packageId;

    /**
     * Expiration
     */
    private Date expireTime;

    /**
     * Number of users (-1 is unlimited)
     */
    private Long accountCount;

    /**
     * Tenant status (0 normal 1 disabled)
     */
    private String status;

    /**
     * Delete flag (0 means exist, 2 means delete)
     */
    @TableLogic
    private String delFlag;

}
