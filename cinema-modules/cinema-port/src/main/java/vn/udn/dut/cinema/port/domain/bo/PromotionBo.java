package vn.udn.dut.cinema.port.domain.bo;

import java.util.Date;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.cinema.common.core.validate.AddGroup;
import vn.udn.dut.cinema.common.core.validate.EditGroup;
import vn.udn.dut.cinema.common.tenant.core.TenantEntity;
import vn.udn.dut.cinema.port.domain.Promotion;

/**
 * Promotion business object promotion
 *
 * @author HoaLD
 * @date 2023-11-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = Promotion.class, reverseConvertGenerate = false)
public class PromotionBo extends TenantEntity {

	private static final long serialVersionUID = -2323039814896662171L;

	/**
	 * Promotion id
	 */
	@NotNull(message = "Id cannot be empty", groups = { EditGroup.class })
	private Long id;

	/**
	 * 
	 */
	@NotBlank(message = "Title cannot be empty", groups = { AddGroup.class, EditGroup.class })
	private String title;

	/**
	 * 
	 */
	@NotBlank(message = "Promotion description cannot be empty", groups = { AddGroup.class, EditGroup.class })
	private String promotionDescription;

	/**
	 * 
	 */
	private Long discount;

	/**
	 * 
	 */
	private String imageUrl;

	/**
	 * 
	 */
	private Date fromDate;

	/**
	 * 
	 */
	private Date toDate;

	/**
	 * 
	 */
	private String remark;

}
