package vn.udn.dut.cinema.system.domain.vo;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.cinema.system.domain.SysNotification;



/**
 * Notification table view object sys_notification
 *
 * @author HoaLD
 * @date 2023-11-10
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysNotification.class)
public class SysNotificationVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Notification id
     */
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
    
    /**
     * 
     */
    private Date createTime;
}
