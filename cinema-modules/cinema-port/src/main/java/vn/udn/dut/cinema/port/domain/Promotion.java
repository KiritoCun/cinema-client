package vn.udn.dut.cinema.port.domain;

import java.io.Serial;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.cinema.common.tenant.core.TenantEntity;

/**
 * Promotion object
 *
 * @author HoaLD
 * @date 2023-11-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("promotion")
public class Promotion extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 3137132635640597264L;

	@TableId(value = "id")
	private Long id;

	private String title;

	private String promotionDescription;

	private String imageUrl;

	private Long discount;

	private Date fromDate;

	private Date toDate;

	private String remark;
}
