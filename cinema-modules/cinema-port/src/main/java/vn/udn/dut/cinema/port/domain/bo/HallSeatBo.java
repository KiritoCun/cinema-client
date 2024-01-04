package vn.udn.dut.cinema.port.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.cinema.port.domain.HallSeat;

@Data
@AutoMapper(target = HallSeat.class, reverseConvertGenerate = false)
public class HallSeatBo {

	private Long id;

	private Long hallId;
	
	private Long seatTypeId;

	private String rowCode;

	private Integer rowSeatNumber;
}
