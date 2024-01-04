package vn.udn.dut.cinema.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.cinema.common.excel.annotation.ExcelDictFormat;
import vn.udn.dut.cinema.common.excel.convert.ExcelDictConvert;
import vn.udn.dut.cinema.system.domain.SysTenantPackage;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


/**
 * Tenant package view object sys_tenant_package
 *
 * @author HoaLD
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysTenantPackage.class)
public class SysTenantPackageVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * tenant package id
     */
    @ExcelProperty(value = "Package ID")
    private Long packageId;

    /**
     * package name
     */
    @ExcelProperty(value = "Package name")
    private String packageName;

    /**
     * context menu id
     */
    @ExcelProperty(value = "Menu IDs")
    private String menuIds;

    /**
     * Remark
     */
    @ExcelProperty(value = "Remark")
    private String remark;

    /**
     * Whether the menu tree selection items are displayed in association
     */
    @ExcelProperty(value = "Whether the menu tree selection items are displayed in association")
    private Boolean menuCheckStrictly;

    /**
     * Status (0 normal 1 disabled)
     */
    @ExcelProperty(value = "Status", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=normal, 1=disabled")
    private String status;

    /**
     * 
     */
    private Date createTime;
}
