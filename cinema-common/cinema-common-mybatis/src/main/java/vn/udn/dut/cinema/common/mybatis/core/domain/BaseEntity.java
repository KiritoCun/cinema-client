package vn.udn.dut.cinema.common.mybatis.core.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Entity base class
 *
 * @author HoaLD
 */

@Data
public class BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * search value
     */
    @JsonIgnore
    @TableField(exist = false)
    private String searchValue;

    /**
     * create department
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createDept;

    /**
     * creator
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    /**
     * creation time
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * updater
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;

    /**
     * update time
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * request parameters
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();

}
