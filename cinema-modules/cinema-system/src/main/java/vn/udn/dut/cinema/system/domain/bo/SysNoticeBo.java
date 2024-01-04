package vn.udn.dut.cinema.system.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.cinema.common.core.validate.AddGroup;
import vn.udn.dut.cinema.common.core.validate.EditGroup;
import vn.udn.dut.cinema.common.core.xss.Xss;
import vn.udn.dut.cinema.common.mybatis.core.domain.BaseEntity;
import vn.udn.dut.cinema.system.domain.SysNotice;

/**
 * Notification Announcement Business Object sys_notice
 *
 * @author HoaLD
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysNotice.class, reverseConvertGenerate = false)
public class SysNoticeBo extends BaseEntity {

    private static final long serialVersionUID = -4160897156963653201L;

	/**
     * Bulletin ID
     */
    @NotNull(message = "Notice id cannot be empty", groups = { EditGroup.class })
    private Long noticeId;

    /**
     * bulletin title
     */
    @Xss(message = "Notice title cannot contain script characters")
    @NotBlank(message = "Notice title cannot be empty", groups = { AddGroup.class, EditGroup.class })
    @Size(min = 0, max = 255, message = "Notice title cannot exceed {max} characters")
    private String noticeTitle;

    /**
     * Announcement Type (1 Notice 2 Bulletins)
     */
    private String noticeType;

    /**
     * Announcement content
     */
    private String noticeContent;

    /**
     * Announcement status (0 normal 1 closed)
     */
    private String status;

    /**
     * Remark
     */
    private String remark;

    /**
     * creator name
     */
    private String createByName;

}
