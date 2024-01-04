package vn.udn.dut.cinema.port.domain;

import java.io.Serial;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.cinema.common.tenant.core.TenantEntity;

/**
 * Cinema object
 *
 * @author HoaLD
 * @date 2023-11-07
 */
@Data
@TableName("cinema")
@EqualsAndHashCode(callSuper = true)
public class Cinema extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	@TableId(value = "id")
	private Long id;

	private String cinemaName;

	private String cinemaAddress;

	private String remark;
}
