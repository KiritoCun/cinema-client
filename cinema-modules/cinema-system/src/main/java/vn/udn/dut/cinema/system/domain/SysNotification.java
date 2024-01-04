package vn.udn.dut.cinema.system.domain;

import java.io.Serial;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.cinema.common.tenant.core.TenantEntity;

/**
 * Notification table object sys_notification
 *
 * @author HoaLD
 * @date 2023-11-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_notification")
public class SysNotification extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Notification id
     */
    @TableId(value = "id")
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
