package vn.udn.dut.cinema.port.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.cinema.common.core.validate.AddGroup;
import vn.udn.dut.cinema.common.core.validate.EditGroup;
import vn.udn.dut.cinema.common.tenant.core.TenantEntity;
import vn.udn.dut.cinema.port.domain.Cinema;

/**
 * Cinema business object
 *
 * @author HoaLD
 * @date 2023-11-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = Cinema.class, reverseConvertGenerate = false)
public class CinemaBo extends TenantEntity {

	private static final long serialVersionUID = -2323039814896662171L;

	/**
	 * Cinema id
	 */
	@NotNull(message = "Id cannot be empty", groups = { EditGroup.class })
	private Long id;

	@NotBlank(message = "Cinema name cannot be empty", groups = { AddGroup.class, EditGroup.class })
	private String cinemaName;

	@NotBlank(message = "Cinema address cannot be empty", groups = { AddGroup.class, EditGroup.class })
	private String cinemaAddress;

	private String remark;
}
