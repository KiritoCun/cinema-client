package vn.udn.dut.cinema.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.cinema.common.mybatis.core.domain.BaseEntity;

import java.io.Serial;

/**
 * Tenant package object sys_tenant_package
 *
 * @author HoaLD
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_tenant_package")
public class SysTenantPackage extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * tenant package id
     */
    @TableId(value = "package_id")
    private Long packageId;
    /**
     * package name
     */
    private String packageName;
    /**
     * context menu id
     */
    private String menuIds;
    /**
     * Remark
     */
    private String remark;
    /**
     * Whether the menu tree selection items are displayed in association (0: parent and child are not associated with each other, 1: parent and child are associated with each other)
     */
    private Boolean menuCheckStrictly;
    /**
     * Status (0 normal 1 disabled)
     */
    private String status;
    /**
     * Delete flag (0 means exist, 2 means delete)
     */
    @TableLogic
    private String delFlag;

}
