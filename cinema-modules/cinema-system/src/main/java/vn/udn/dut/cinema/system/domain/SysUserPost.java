package vn.udn.dut.cinema.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * User and post association sys_user_post
 *
 * @author HoaLD
 */

@Data
@TableName("sys_user_post")
public class SysUserPost {

    /**
     * User ID
     */
    @TableId(type = IdType.INPUT)
    private Long userId;

    /**
     * Job ID
     */
    private Long postId;

}
