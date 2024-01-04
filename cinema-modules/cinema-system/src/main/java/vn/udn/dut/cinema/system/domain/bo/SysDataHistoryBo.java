package vn.udn.dut.cinema.system.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.cinema.common.mybatis.core.domain.BaseEntity;
import vn.udn.dut.cinema.system.domain.SysDataHistory;

/**
 * System data history business object sys_data_history
 *
 * @author HoaLD
 * @date 2023-08-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysDataHistory.class, reverseConvertGenerate = false)
public class SysDataHistoryBo extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
     * System data history id
     */
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
     * History type
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
    
    /**
     * 
     */
    private String username;
}
