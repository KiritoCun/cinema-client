package vn.udn.dut.cinema.system.domain.vo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.cinema.common.translation.annotation.Translation;
import vn.udn.dut.cinema.common.translation.constant.TransConstant;
import vn.udn.dut.cinema.system.domain.SysNotice;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;



/**
 * Notification notice view object sys_notice
 *
 * @author HoaLD
 */
@Data
@AutoMapper(target = SysNotice.class)
public class SysNoticeVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Notice id
     */
    private Long noticeId;

    /**
     * Notice title
     */
    private String noticeTitle;

    /**
     * announcement type（1 notice 2 Announcement）
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
     * creator
     */
    private Long createBy;

    /**
     * creator name
     */
    @Translation(type = TransConstant.USER_ID_TO_NAME, mapper = "createBy")
    private String createByName;

    /**
     * Creation time
     */
    private Date createTime;

}
