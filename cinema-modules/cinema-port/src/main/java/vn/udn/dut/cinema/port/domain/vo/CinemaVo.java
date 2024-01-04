package vn.udn.dut.cinema.port.domain.vo;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.cinema.port.domain.Cinema;

/**
 * Cinema view object
 *
 * @author HoaLD
 * @date 2023-11-07
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = Cinema.class)
public class CinemaVo implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Cinema id
	 */
	private Long id;

	private String cinemaName;

	private String cinemaAddress;

	private String remark;
	
	private Date createTime;
	
	
}
