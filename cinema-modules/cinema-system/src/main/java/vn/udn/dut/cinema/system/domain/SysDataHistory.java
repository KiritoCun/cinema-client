package vn.udn.dut.cinema.system.domain;

import vn.udn.dut.cinema.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * System data history object sys_data_history
 *
 * @author HoaLD
 * @date 2023-08-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_data_history")
public class SysDataHistory extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * System data history id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * Reference of record by record 
     */
    private Long refId;

    /**
     * New value of changed field 
     */
    private String newValue;

    /**
     * Old value of changed field
     */
    private String oldValue;

    /**
     * Data field
     */
    private String dataField;

    /**
     * 
     */
    private String histType;

    /**
     * Table name
     */
    private String tableName;

    /**
     * 
     */
    private String systemType;


}
