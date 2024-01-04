package vn.udn.dut.cinema.system.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.cinema.common.core.validate.AddGroup;
import vn.udn.dut.cinema.common.core.validate.EditGroup;
import vn.udn.dut.cinema.common.mybatis.core.domain.BaseEntity;
import vn.udn.dut.cinema.system.domain.SysConfig;
import jakarta.validation.constraints.*;

/**
 * Parameter configuration business object sys_config
 *
 * @author HoaLD
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysConfig.class, reverseConvertGenerate = false)
public class SysConfigBo extends BaseEntity {

    private static final long serialVersionUID = 8123917485952036157L;

	/**
     * parameter primary key
     */
    @NotNull(message = "Parameter primary key cannot be empty", groups = { EditGroup.class })
    private Long configId;

    /**
     * parameter name
     */
    @NotBlank(message = "Parameter name cannot be empty", groups = { AddGroup.class, EditGroup.class })
    @Size(min = 0, max = 100, message = "Parameter name cannot exceed {max} characters")
    private String configName;

    /**
     * parameter key name
     */
    @NotBlank(message = "Parameter key name cannot be empty", groups = { AddGroup.class, EditGroup.class })
    @Size(min = 0, max = 100, message = "The length of the parameter key name cannot exceed {max} characters")
    private String configKey;

    /**
     * parameter key
     */
    @NotBlank(message = "Parameter key value cannot be empty", groups = { AddGroup.class, EditGroup.class })
    private String configValue;

    /**
     * System built-in (Y yes N no)
     */
    private String configType;

    /**
     * Remark
     */
    private String remark;

}
