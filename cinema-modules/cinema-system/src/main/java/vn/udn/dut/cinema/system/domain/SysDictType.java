package vn.udn.dut.cinema.system.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.cinema.common.tenant.core.TenantEntity;

/**
 * Dictionary type table sys_dict_type
 *
 * @author HoaLD
 */

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dict_type")
public class SysDictType extends TenantEntity {

    private static final long serialVersionUID = -2102844400460310912L;

	/**
     * dictionary primary key
     */
    @TableId(value = "dict_id")
    private Long dictId;

    /**
     * dictionary name
     */
    private String dictName;

    /**
     * dictionary type
     */
    private String dictType;

    /**
     * Status (0 normal 1 disabled)
     */
    private String status;

    /**
     * Remark
     */
    private String remark;

}
