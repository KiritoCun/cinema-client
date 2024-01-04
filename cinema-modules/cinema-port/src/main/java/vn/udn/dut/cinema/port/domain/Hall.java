package vn.udn.dut.cinema.port.domain;

import java.io.Serial;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.cinema.common.tenant.core.TenantEntity;

/**
 * Hall object
 *
 * @author HoaLD
 * @date 2023-11-07
 */
@Data
@TableName("hall")
@EqualsAndHashCode(callSuper = true)
public class Hall extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	@TableId(value = "id")
	private Long id;
	
	private Long cinemaId;

	private String hallName;

	private Boolean capacity;

	private Long rowNumber;
	
	private Date createTime;
	
	private String remark;
}
