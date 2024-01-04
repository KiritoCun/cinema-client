package vn.udn.dut.cinema.port.domain.bo;

import java.util.Date;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.cinema.common.core.validate.EditGroup;
import vn.udn.dut.cinema.common.tenant.core.TenantEntity;
import vn.udn.dut.cinema.port.domain.Showtime;

/**
 * Showtime business object
 *
 * @author HoaLD
 * @date 2023-11-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = Showtime.class, reverseConvertGenerate = false)
public class ShowtimeBo extends TenantEntity {

	private static final long serialVersionUID = -2323039814896662171L;

	/**
	 * Movie id
	 */
	@NotNull(message = "Id cannot be empty", groups = { EditGroup.class })
	private Long id;

	private Long cinemaId;

	private Long movieId;
	
	private String movieName;

	private Long hallId;
	
	private String hallName;

	private Date startTime;

	private Date endTime;

	private String remark;
}
