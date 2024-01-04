package vn.udn.dut.cinema.system.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.cinema.common.tenant.core.TenantEntity;

/**
 * Parameter configuration table sys_config
 *
 * @author HoaLD
 */

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_config")
public class SysConfig extends TenantEntity {

    private static final long serialVersionUID = 5645816143672840281L;

	/**
     * parameter primary key
     */
    @TableId(value = "config_id")
    private Long configId;

    /**
     * parameter name
     */
    private String configName;

    /**
     * parameter key name
     */
    private String configKey;

    /**
     * parameter key
     */
    private String configValue;

    /**
     * System built-in (Y yes N no)
     */
    private String configType;

    /**
     * Remark
     */
    private String remark;

}
