package vn.udn.dut.cinema.port.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.cinema.common.core.validate.EditGroup;
import vn.udn.dut.cinema.common.tenant.core.TenantEntity;
import vn.udn.dut.cinema.port.domain.Seat;

/**
 * Seat business object
 *
 * @author HoaLD
 * @date 2023-11-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = Seat.class, reverseConvertGenerate = false)
public class SeatBo extends TenantEntity {

	private static final long serialVersionUID = -2323039814896662171L;

	/**
	 * Seat id
	 */
	@NotNull(message = "Id cannot be empty", groups = { EditGroup.class })
	private Long id;

	private Long seatTypeId;

	private Long hallId;
	
	private Long showtimeId;

    private String rowCode;
    
    private Integer columnCode;
	
	private String status;

	private String remark;
}
