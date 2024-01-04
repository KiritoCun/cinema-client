package vn.udn.dut.cinema.system.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.cinema.common.core.validate.AddGroup;
import vn.udn.dut.cinema.common.core.validate.EditGroup;
import vn.udn.dut.cinema.common.mybatis.core.domain.BaseEntity;
import vn.udn.dut.cinema.system.domain.SysDept;

/**
 * Department business object sys_dept
 *
 * @author HoaLD
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysDept.class, reverseConvertGenerate = false)
public class SysDeptBo extends BaseEntity {

    private static final long serialVersionUID = 5747177031674231501L;

	/**
     * department id
     */
    @NotNull(message = "Department id cannot be empty", groups = { EditGroup.class })
    private Long deptId;

    /**
     * Parent Department ID
     */
    private Long parentId;

    /**
     * Department name
     */
    @NotBlank(message = "Department name cannot be empty", groups = { AddGroup.class, EditGroup.class })
    @Size(min = 0, max = 30, message = "Department name length cannot exceed {max} characters")
    private String deptName;

    /**
     * display order
     */
    @NotNull(message = "Display order cannot be empty")
    private Integer orderNum;

    /**
     * principal
     */
    private String leader;

    /**
     * contact number
     */
    @Size(min = 0, max = 11, message = "The contact phone number cannot exceed {max} characters")
    private String phone;

    /**
     * Email
     */
    @Email(message = "Email format is incorrect")
    @Size(min = 0, max = 50, message = "The mailbox length cannot exceed {max} characters")
    private String email;

    /**
     * Department status (0 normal 1 disabled)
     */
    private String status;

}
