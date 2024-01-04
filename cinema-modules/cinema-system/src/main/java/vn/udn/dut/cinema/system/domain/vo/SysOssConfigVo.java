package vn.udn.dut.cinema.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.cinema.system.domain.SysOssConfig;

import java.io.Serial;
import java.io.Serializable;


/**
 * Object storage configuration view object sys_oss_config
 *
 * @author HoaLD
 * @date 2021-08-13
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysOssConfig.class)
public class SysOssConfigVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * main building
     */
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
     * Endpoint
     */
    private String endpoint;

    /**
     * custom domain name
     */
    private String domain;

    /**
     * Whether https (Y=yes, N=no)
     */
    private String isHttps;

    /**
     * Region
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
