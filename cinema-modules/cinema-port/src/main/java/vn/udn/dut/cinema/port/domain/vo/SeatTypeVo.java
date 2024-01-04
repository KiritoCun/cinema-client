package vn.udn.dut.cinema.port.domain.vo;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.cinema.port.domain.SeatType;

/**
 * Seat type view object
 *
 * @author HoaLD
 * @date 2023-11-07
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SeatType.class)
public class SeatTypeVo implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Seat type ID
	 */
	private Long id;

	private String seatTypeName;

	private Long price;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date createTime;

	private String remark;
}
