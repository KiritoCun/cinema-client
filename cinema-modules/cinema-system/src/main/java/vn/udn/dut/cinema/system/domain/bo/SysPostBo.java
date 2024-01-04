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
import vn.udn.dut.cinema.system.domain.SysPost;

/**
 * Post information business object sys_post
 *
 * @author HoaLD
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysPost.class, reverseConvertGenerate = false)
public class SysPostBo extends BaseEntity {

    private static final long serialVersionUID = 4791285917608900143L;

	/**
     * Job ID
     */
    @NotNull(message = "Job id cannot be empty", groups = { EditGroup.class })
    private Long postId;

    /**
     * job code
     */
    @NotBlank(message = "Job code cannot be empty", groups = { AddGroup.class, EditGroup.class })
    @Size(min = 0, max = 64, message = "The length of the post code cannot exceed {max} characters")
    private String postCode;

    /**
     * Position Title
     */
    @NotBlank(message = "Job title cannot be empty", groups = { AddGroup.class, EditGroup.class })
    @Size(min = 0, max = 50, message = "Job title length cannot exceed {max} characters")
    private String postName;

    /**
     * display order
     */
    @NotNull(message = "Display order cannot be empty", groups = { AddGroup.class, EditGroup.class })
    private Integer postSort;

    /**
     * Status (0 normal 1 disabled)
     */
    private String status;

    /**
     * Remark
     */
    private String remark;


}
