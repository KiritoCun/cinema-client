package vn.udn.dut.cinema.port.domain;

import java.io.Serial;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.cinema.common.tenant.core.TenantEntity;

/**
 * Seat type object
 *
 * @author HoaLD
 * @date 2023-11-07
 */
@Data
@TableName("seat_type")
@EqualsAndHashCode(callSuper = true)
public class SeatType extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	@TableId(value = "id")
	private Long id;

	private String seatTypeName;

	private Long price;

	private Date createTime;

	private String remark;
}
