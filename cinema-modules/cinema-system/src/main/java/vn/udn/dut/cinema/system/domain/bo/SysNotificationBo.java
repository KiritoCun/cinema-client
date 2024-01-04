package vn.udn.dut.cinema.system.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.cinema.common.core.validate.EditGroup;
import vn.udn.dut.cinema.common.mybatis.core.domain.BaseEntity;
import vn.udn.dut.cinema.system.domain.SysNotification;

/**
 * Notification table business object sys_notification
 *
 * @author HoaLD
 * @date 2023-11-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysNotification.class, reverseConvertGenerate = false)
public class SysNotificationBo extends BaseEntity {
		
	private static final long serialVersionUID = 1L;
	
    /**
     * Notification id
     */
    @NotNull(message = "Notification id cannot be empty", groups = { EditGroup.class })
    private Long id;

    /**
     *
     */
    private Long userId;

    /**
     *
     */
    private String title;

    /**
     *
     */
    private String content;

    /**
     *
     */
    private String componentPath;

    /**
     *
     */
    private String seenFlag;
}
