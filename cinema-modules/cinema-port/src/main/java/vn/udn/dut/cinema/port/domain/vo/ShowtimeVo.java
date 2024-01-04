package vn.udn.dut.cinema.port.domain.vo;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.cinema.port.domain.Showtime;

/**
 * Showtime view object
 *
 * @author HoaLD
 * @date 2023-11-07
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = Showtime.class)
public class ShowtimeVo implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Seat ID
	 */
	private Long id;

	private Long cinemaId;

	private Long movieId;

	private Long hallId;
	
	private String cinemaName;
	
	private String hallName;
	
	private String movieName;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date startTime;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date endTime;

	private String remark;
}
