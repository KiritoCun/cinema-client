package vn.udn.dut.cinema.system.domain;

import java.io.Serial;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.cinema.common.tenant.core.TenantEntity;

/**
 * System document object sys_document
 *
 * @author HoaLD
 * @date 2023-08-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_document")
public class SysDocument extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * System document id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 
     */
    private String docType;

    /**
     * 
     */
    private String docTitle;

    /**
     * 
     */
    private String docUrl;

    /**
     * 
     */
    private String docDescription;

    /**
     * 
     */
    private String docTarget;

    /**
     * 
     */
    private String imageType;

    /**
     * 
     */
    private String isExternalLink;

    /**
     * 
     */
    private String status;
    
    /**
     * 
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Long ossId;

    /**
     * Remark
     */
    private String remark;
}
