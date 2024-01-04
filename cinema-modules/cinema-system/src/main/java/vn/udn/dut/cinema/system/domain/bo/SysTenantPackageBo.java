package vn.udn.dut.cinema.system.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import io.github.linpeilie.annotations.AutoMapping;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.cinema.common.core.validate.AddGroup;
import vn.udn.dut.cinema.common.core.validate.EditGroup;
import vn.udn.dut.cinema.common.mybatis.core.domain.BaseEntity;
import vn.udn.dut.cinema.system.domain.SysTenantPackage;
import jakarta.validation.constraints.*;

/**
 * Tenant Package Business Object sys_tenant_package
 *
 * @author HoaLD
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysTenantPackage.class, reverseConvertGenerate = false)
public class SysTenantPackageBo extends BaseEntity {

    private static final long serialVersionUID = -7695623921911878057L;

	/**
     * tenant package id
     */
    @NotNull(message = "Package id cannot be empty", groups = { EditGroup.class })
    private Long packageId;

    /**
     * package name
     */
    @NotBlank(message = "Package name cannot be empty", groups = { AddGroup.class, EditGroup.class })
    private String packageName;

    /**
     * context menu id
     */
    @AutoMapping(target = "menuIds", expression = "java(vn.udn.dut.cinema.common.core.utils.StringUtils.join(source.getMenuIds(), \",\"))")
    private Long[] menuIds;

    /**
     * Remark
     */
    private String remark;

    /**
     * Whether the menu tree selection items are displayed in association
     */
    private Boolean menuCheckStrictly;

    /**
     * Status (0 normal 1 disabled)
     */
    private String status;


}
