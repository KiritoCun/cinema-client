package vn.udn.dut.cinema.port.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.cinema.common.core.validate.AddGroup;
import vn.udn.dut.cinema.common.core.validate.EditGroup;
import vn.udn.dut.cinema.common.tenant.core.TenantEntity;
import vn.udn.dut.cinema.port.domain.SeatType;

import java.util.Date;

/**
 * Seat type business object
 *
 * @author HoaLD
 * @date 2023-11-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SeatType.class, reverseConvertGenerate = false)
public class SeatTypeBo extends TenantEntity {

	private static final long serialVersionUID = -2323039814896662171L;

	/**
	 * Seat type id
	 */
	@NotNull(message = "Id cannot be empty", groups = { EditGroup.class })
	private Long id;

	@NotNull(message = "Seat type name cannot be empty", groups = { AddGroup.class, EditGroup.class })
	private String seatTypeName;

	@NotNull(message = "Price cannot be empty", groups = { AddGroup.class, EditGroup.class })
	private Long price;

	private Date createTime;

	private String remark;
}
