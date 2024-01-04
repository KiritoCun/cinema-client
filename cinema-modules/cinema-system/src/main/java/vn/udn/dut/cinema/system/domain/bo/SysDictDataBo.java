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
import vn.udn.dut.cinema.system.domain.SysDictData;

/**
 * Dictionary data business object sys_dict_data
 *
 * @author HoaLD
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysDictData.class, reverseConvertGenerate = false)
public class SysDictDataBo extends BaseEntity {

    private static final long serialVersionUID = 6001799618657300054L;

	/**
     * dictionary encoding
     */
    @NotNull(message = "Dictionary encoding cannot be empty", groups = { EditGroup.class })
    private Long dictCode;

    /**
     * dictionary sort
     */
    private Integer dictSort;

    /**
     * dictionary tag
     */
    @NotBlank(message = "Dictionary tag cannot be empty", groups = { AddGroup.class, EditGroup.class })
    @Size(min = 0, max = 100, message = "Dictionary tag cannot exceed {max} characters in length")
    private String dictLabel;

    /**
     * Dictionary key value
     */
    @NotBlank(message = "Dictionary key cannot be empty", groups = { AddGroup.class, EditGroup.class })
    @Size(min = 0, max = 100, message = "Dictionary key length cannot exceed {max} characters")
    private String dictValue;

    /**
     * dictionary type
     */
    @NotBlank(message = "Dictionary type cannot be empty", groups = { AddGroup.class, EditGroup.class })
    @Size(min = 0, max = 100, message = "The length of the dictionary type cannot exceed {max} characters")
    private String dictType;

    /**
     * Style properties (additional style extensions)
     */
    @Size(min = 0, max = 100, message = "Style attributes cannot exceed {max} characters in length")
    private String cssClass;

    /**
     * Table echo style
     */
    private String listClass;

    /**
     * Is it the default (Y is N No)
     */
    private String isDefault;

    /**
     * Status (0 normal 1 disabled)
     */
    private String status;

    /**
     * create department
     */
    private Long createDept;

    /**
     * Remark
     */
    private String remark;

}
