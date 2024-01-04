package vn.udn.dut.cinema.system.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.cinema.common.core.validate.AddGroup;
import vn.udn.dut.cinema.common.core.validate.EditGroup;
import vn.udn.dut.cinema.common.mybatis.core.domain.BaseEntity;
import vn.udn.dut.cinema.system.domain.SysDictType;

/**
 * Dictionary type business object sys_dict_type
 *
 * @author HoaLD
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysDictType.class, reverseConvertGenerate = false)
public class SysDictTypeBo extends BaseEntity {

    private static final long serialVersionUID = -5648924786235785305L;

	/**
     * dictionary primary key
     */
    @NotNull(message = "Dictionary primary key cannot be empty", groups = { EditGroup.class })
    private Long dictId;

    /**
     * dictionary name
     */
    @NotBlank(message = "Dictionary name cannot be empty", groups = { AddGroup.class, EditGroup.class })
    @Size(min = 0, max = 100, message = "The dictionary name cannot exceed {max} characters in length")
    private String dictName;

    /**
     * dictionary type
     */
    @NotBlank(message = "Dictionary type cannot be empty", groups = { AddGroup.class, EditGroup.class })
    @Size(min = 0, max = 100, message = "The length of the dictionary type cannot exceed {max} characters")
    @Pattern(regexp = "^[a-z][a-z0-9_]*$", message = "The dictionary type must start with a letter and can only be (lowercase letters, numbers, underscores)")
    private String dictType;

    /**
     * Status (0 normal 1 disabled)
     */
    private String status;

    /**
     * Remark
     */
    private String remark;


}
