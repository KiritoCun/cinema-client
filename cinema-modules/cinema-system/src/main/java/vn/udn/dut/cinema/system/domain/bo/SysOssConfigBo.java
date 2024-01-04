package vn.udn.dut.cinema.system.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.cinema.common.core.validate.AddGroup;
import vn.udn.dut.cinema.common.core.validate.EditGroup;
import vn.udn.dut.cinema.common.mybatis.core.domain.BaseEntity;
import vn.udn.dut.cinema.system.domain.SysOssConfig;

/**
 * Object storage configuration business object sys_oss_config
 *
 * @author HoaLD
 * @date 2021-08-13
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysOssConfig.class, reverseConvertGenerate = false)
public class SysOssConfigBo extends BaseEntity {

    private static final long serialVersionUID = 4763393624701649352L;

	/**
     * main building
     */
    @NotNull(message = "OSS config cannot be empty", groups = {EditGroup.class})
    private Long ossConfigId;

    /**
     * configuration key
     */
    @NotBlank(message = "The configuration key cannot be empty", groups = {AddGroup.class, EditGroup.class})
    @Size(min = 2, max = 100, message = "Config key length must be between {min} and {max}")
    private String configKey;

    /**
     * accessKey
     */
    @NotBlank(message = "Access key cannot be empty", groups = {AddGroup.class, EditGroup.class})
    @Size(min = 2, max = 100, message = "Access key length must be between {min} and {max}")
    private String accessKey;

    /**
     * Secret key
     */
    @NotBlank(message = "Secret key cannot be empty", groups = {AddGroup.class, EditGroup.class})
    @Size(min = 2, max = 100, message = "Secret key length must be between {min} and {max}")
    private String secretKey;

    /**
     * bucket name
     */
    @NotBlank(message = "Bucket name cannot be empty", groups = {AddGroup.class, EditGroup.class})
    @Size(min = 2, max = 100, message = "Bucket name length must be between {min} and {max}")
    private String bucketName;

    /**
     * prefix
     */
    private String prefix;

    /**
     * visit site
     */
    @NotBlank(message = "The endpoint cannot be empty", groups = {AddGroup.class, EditGroup.class})
    @Size(min = 2, max = 100, message = "The endpoint length must be between {min} and {max}")
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
     * Is it the default (0=yes, 1=no)
     */
    private String status;

    /**
     * area
     */
    private String region;

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
    @NotBlank(message = "Bucket permission type cannot be empty", groups = {AddGroup.class, EditGroup.class})
    private String accessPolicy;

}
