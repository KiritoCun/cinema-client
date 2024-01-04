package vn.udn.dut.cinema.system.domain.vo;

import java.util.Date;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.cinema.common.excel.annotation.ExcelDictFormat;
import vn.udn.dut.cinema.common.excel.convert.ExcelDictConvert;
import vn.udn.dut.cinema.system.domain.SysTenant;

import java.io.Serial;
import java.io.Serializable;


/**
 * Tenant view object sys_tenant
 *
 * @author HoaLD
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysTenant.class)
public class SysTenantVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ExcelProperty(value = "id")
    private Long id;

    /**
     * Tenant ID
     */
    @ExcelProperty(value = "Tenant ID")
    private String tenantId;

    /**
     * contact person
     */
    @ExcelProperty(value = "Contact person")
    private String contactUserName;

    /**
     * contact number
     */
    @ExcelProperty(value = "Contact number")
    private String contactPhone;

    /**
     * Company Name
     */
    @ExcelProperty(value = "Company name")
    private String companyName;

    /**
     * Unified Social Credit Code
     */
    @ExcelProperty(value = "License number")
    private String licenseNumber;

    /**
     * address
     */
    @ExcelProperty(value = "Address")
    private String address;

    /**
     * domain name
     */
    @ExcelProperty(value = "Domain name")
    private String domain;

    /**
     * company profile
     */
    @ExcelProperty(value = "Company profile")
    private String intro;

    /**
     * Remark
     */
    @ExcelProperty(value = "Remark")
    private String remark;

    /**
     * Tenant Package Number
     */
    @ExcelProperty(value = "Package ID")
    private Long packageId;

    /**
     * Expire time
     */
    @ExcelProperty(value = "Expire time")
    private Date expireTime;

    /**
     * Number of users (-1 unlimited）
     */
    @ExcelProperty(value = "Amount of users")
    private Long accountCount;

    /**
     * Tenant status (0 normal 1 disabled)
     */
    @ExcelProperty(value = "Tenant status", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=Normal, 1=Disabled")
    private String status;

    /**
     * 
     */
    private Date createTime;
}
