package vn.udn.dut.cinema.system.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.cinema.common.tenant.core.TenantEntity;

/**
 * OSS object storage object
 *
 * @author HoaLD
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_oss")
public class SysOss extends TenantEntity {

    private static final long serialVersionUID = 5717560851420038569L;

	/**
     * Object storage primary key
     */
    @TableId(value = "oss_id")
    private Long ossId;

    /**
     * file name
     */
    private String fileName;

    /**
     * original name
     */
    private String originalName;

    /**
     * file extension
     */
    private String fileSuffix;

    /**
     * URL address
     */
    private String url;

    /**
     * service provider
     */
    private String service;

}
