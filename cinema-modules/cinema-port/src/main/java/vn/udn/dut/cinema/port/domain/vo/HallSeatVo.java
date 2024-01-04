package vn.udn.dut.cinema.port.domain.vo;

import java.io.Serial;
import java.io.Serializable;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.cinema.port.domain.HallSeat;

@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = HallSeat.class)
public class HallSeatVo implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	private Long id;

	private Long hallId;
	
	private Long seatTypeId;

	private String rowCode;

	private Integer rowSeatNumber;
}
