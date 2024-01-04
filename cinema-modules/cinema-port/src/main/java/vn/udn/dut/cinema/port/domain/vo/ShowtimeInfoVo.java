package vn.udn.dut.cinema.port.domain.vo;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;

import lombok.Data;

/**
 * Showtime view object
 *
 * @author HoaLD
 * @date 2023-11-07
 */
@Data
@ExcelIgnoreUnannotated
public class ShowtimeInfoVo implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Cinema ID
	 */
	private Long id;

	private CinemaVo cinema;

	private List<ShowtimeVo> showtimeList;
}
