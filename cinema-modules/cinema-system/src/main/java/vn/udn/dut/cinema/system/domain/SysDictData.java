package vn.udn.dut.cinema.system.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.cinema.common.core.constant.UserConstants;
import vn.udn.dut.cinema.common.tenant.core.TenantEntity;

/**
 * Dictionary data table sys_dict_data
 *
 * @author HoaLD
 */

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dict_data")
public class SysDictData extends TenantEntity {

    private static final long serialVersionUID = 1L;

	/**
     * dictionary encoding
     */
    @TableId(value = "dict_code")
    private Long dictCode;

    /**
     * dictionary sort
     */
    private Integer dictSort;

    /**
     * dictionary tag
     */
    private String dictLabel;

    /**
     * Dictionary key value
     */
    private String dictValue;

    /**
     * dictionary type
     */
    private String dictType;

    /**
     * Style properties (additional style extensions)
     */
    private String cssClass;

    /**
     * table dictionary style
     */
    private String listClass;

    /**
     * Is it the default (Y is N No)
     */
    private String isDefault;

    /**
     * Status (0 normal 1 disabled)
     */
    private String status;

    /**
     * Remark
     */
    private String remark;

    public boolean getDefault() {
        return UserConstants.YES.equals(this.isDefault);
    }

}
