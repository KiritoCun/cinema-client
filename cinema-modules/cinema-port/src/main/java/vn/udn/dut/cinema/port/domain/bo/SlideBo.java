package vn.udn.dut.cinema.port.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.cinema.common.core.validate.AddGroup;
import vn.udn.dut.cinema.common.core.validate.EditGroup;
import vn.udn.dut.cinema.common.tenant.core.TenantEntity;
import vn.udn.dut.cinema.port.domain.Slide;

@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = Slide.class, reverseConvertGenerate = false)
public class SlideBo extends TenantEntity {
	private static final long serialVersionUID = -2323039814896662171L;

	@NotNull(message = "Id cannot be empty", groups = { EditGroup.class })
	private Long id;

	@NotBlank(message = "Slide URL cannot be empty", groups = { AddGroup.class, EditGroup.class })
	private String slideUrl;
	
	private String remark;
}
