package vn.udn.dut.cinema.port.domain.vo;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.cinema.port.domain.Seat;

/**
 * Seat view object
 *
 * @author HoaLD
 * @date 2023-11-07
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = Seat.class)
public class SeatVo implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Seat ID
	 */
	private Long id;

	private Long seatTypeId;

	private Long hallId;
	
	private Long showtimeId;

    private String rowCode;
    
    private Integer columnCode;
	
	private String status;
	
	private Long price;
	
	private String seatTypeName;
	
	private Date updateTime;

	private String remark;
}
