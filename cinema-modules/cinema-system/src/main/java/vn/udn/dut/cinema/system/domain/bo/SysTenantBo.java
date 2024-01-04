package vn.udn.dut.cinema.system.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.cinema.common.core.validate.AddGroup;
import vn.udn.dut.cinema.common.core.validate.EditGroup;
import vn.udn.dut.cinema.common.mybatis.core.domain.BaseEntity;
import vn.udn.dut.cinema.system.domain.SysTenant;
import jakarta.validation.constraints.*;

import java.util.Date;

/**
 * Tenant business object sys_tenant
 *
 * @author HoaLD
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysTenant.class, reverseConvertGenerate = false)
public class SysTenantBo extends BaseEntity {

    private static final long serialVersionUID = -25147032056977539L;

	/**
     * id
     */
    @NotNull(message = "ID cannot be empty", groups = { EditGroup.class })
    private Long id;

    /**
     * tenant number
     */
    private String tenantId;

    /**
     * contact person
     */
    @NotBlank(message = "Contact cannot be empty", groups = { AddGroup.class, EditGroup.class })
    private String contactUserName;

    /**
     * contact number
     */
    @NotBlank(message = "Phone number could not be empty", groups = { AddGroup.class, EditGroup.class })
    private String contactPhone;

    /**
     * Company Name
     */
    @NotBlank(message = "Company name cannot be empty", groups = { AddGroup.class, EditGroup.class })
    private String companyName;

    /**
     * username (create system user)
     */
    @NotBlank(message = "Username can not be empty", groups = { AddGroup.class })
    private String username;

    /**
     * password (create system user)
     */
    @NotBlank(message = "Password can not be blank", groups = { AddGroup.class })
    private String password;

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
    @NotNull(message = "Tenant package cannot be empty", groups = { AddGroup.class })
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


}
