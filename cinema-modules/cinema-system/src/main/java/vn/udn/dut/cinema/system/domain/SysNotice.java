package vn.udn.dut.cinema.system.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.cinema.common.tenant.core.TenantEntity;


/**
 * Notification notice table sys_notice
 *
 * @author HoaLD
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_notice")
public class SysNotice extends TenantEntity {

    private static final long serialVersionUID = -2040111917068251726L;

	/**
     * Notice ID
     */
    @TableId(value = "notice_id")
    private Long noticeId;

    /**
     * Notice title
     */
    private String noticeTitle;

    /**
     * Announcement type（1 Notice 2 Announcement）
     */
    private String noticeType;

    /**
     * Notice content
     */
    private String noticeContent;

    /**
     * Announcement status（0 normal 1 off）
     */
    private String status;

    /**
     * Remark
     */
    private String remark;

}
