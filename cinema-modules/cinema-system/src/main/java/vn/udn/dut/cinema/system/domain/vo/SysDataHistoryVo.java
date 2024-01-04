package vn.udn.dut.cinema.system.domain.vo;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.cinema.system.domain.SysDataHistory;



/**
 * System data history view object sys_data_history
 *
 * @author HoaLD
 * @date 2023-08-23
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysDataHistory.class)
public class SysDataHistoryVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * System data history id
     */
    @ExcelProperty(value = "ID", order = 1)
    private Long id;

    /**
     * Reference of record by record 
     */
    private Long refId;

    /**
     * New value of changed field
     */
    @ExcelProperty(value = "New value", order = 8)
    private String newValue;

    /**
     * Old value of changed field
     */
    @ExcelProperty(value = "Old value", order = 7)
    private String oldValue;

    /**
     * Data field
     */
    @ExcelProperty(value = "Data field", order = 6)
    private String dataField;

    /**
     * History type
     */
    @ExcelProperty(value = "History type", order = 5)
    private String histType;

    /**
     * Table name
     */
    @ExcelProperty(value = "Module", order = 3)
    private String tableName;

    /**
     * System type
     */
    @ExcelProperty(value = "System type", order = 2)
    private String systemType;

    /**
     * Creation time
     */
    @ExcelProperty(value = "Operation time", order = 4)
    private Date createTime;
    
    /**
     * 
     */
    @ExcelProperty(value = "Operator", order = 9)
    private String username;
}
