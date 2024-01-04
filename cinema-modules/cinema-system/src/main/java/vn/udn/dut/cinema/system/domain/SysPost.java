package vn.udn.dut.cinema.system.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.cinema.common.tenant.core.TenantEntity;

/**
 * Post table sys_post
 *
 * @author HoaLD
 */

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_post")
public class SysPost extends TenantEntity {

    private static final long serialVersionUID = -9068771034592727003L;

	/**
     * job number
     */
    @TableId(value = "post_id")
    private Long postId;

    /**
     * job code
     */
    private String postCode;

    /**
     * Position Title
     */
    private String postName;

    /**
     * job sorting
     */
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
