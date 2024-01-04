package vn.udn.dut.cinema.system.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.cinema.common.tenant.core.TenantEntity;

/**
 * Object storage configuration object sys_oss_config
 *
 * @author HoaLD
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_oss_config")
public class SysOssConfig extends TenantEntity {

    private static final long serialVersionUID = -6679919735148616660L;

	/**
     * main building
     */
    @TableId(value = "oss_config_id")
    private Long ossConfigId;

    /**
     * configuration key
     */
    private String configKey;

    /**
     * accessKey
     */
    private String accessKey;

    /**
     * Secret key
     */
    private String secretKey;

    /**
     * bucket name
     */
    private String bucketName;

    /**
     * prefix
     */
    private String prefix;

    /**
     * visit site
     */
    private String endpoint;

    /**
     * custom domain name
     */
    private String domain;

    /**
     * Whether https (0 no 1 yes)
     */
    private String isHttps;

    /**
     * area
     */
    private String region;

    /**
     * Is it the default (0=yes, 1=no)
     */
    private String status;

    /**
     * no
     */
    private String ext1;

    /**
     * Remark
     */
    private String remark;

    /**
     * Bucket permission type (0 private 1 public 2 custom)
     */
    private String accessPolicy;
}
