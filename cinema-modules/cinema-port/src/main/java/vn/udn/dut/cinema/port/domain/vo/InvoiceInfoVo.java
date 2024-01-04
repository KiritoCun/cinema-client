package vn.udn.dut.cinema.port.domain.vo;

import java.io.Serial;
import java.io.Serializable;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;

import lombok.Data;

/**
 * Invoice view object
 *
 * @author HoaLD
 * @date 2023-11-07
 */
@Data
@ExcelIgnoreUnannotated
public class InvoiceInfoVo implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	private Long ticketId;

	private String startTime;
	
	private String cinemaName;

	private String hallName;

	private String seatName;
	
	private String promotionName;

	private String seatDescription;
	
	private Long totalAmount;

	private Long discountAmount;
	
	private Long actualAmount;
}
