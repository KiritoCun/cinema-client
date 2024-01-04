package vn.udn.dut.cinema.system.domain.vo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * Container size type
 *
 * @author HoaLD
 */
@Data
@AutoMapper(target = ContainerSztpVo.class)
public class ContainerSztpVo {

	/**
	 * Len
	 */
	private String len;

	/**
	 * Type
	 */
	private String type;

	/**
	 * Size type
	 */
	private String sztp;

	/**
	 * Description
	 */
	private String description;
}
