package vn.udn.dut.cinema.port.domain.vo;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;

import lombok.Data;

/**
 * Seat view object
 *
 * @author HoaLD
 * @date 2023-11-07
 */
@Data
@ExcelIgnoreUnannotated
public class SeatOrderVo implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Seat ID
	 */
	private Long id;

	private String rowCode;
	
	private Long price;
	
	private List<SeatVo> seatList;
}
