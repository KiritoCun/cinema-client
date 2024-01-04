package vn.udn.dut.cinema.port.domain.vo;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.cinema.port.domain.Booking;

/**
 * Booking view object
 *
 * @author HoaLD
 * @date 2023-11-07
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = Booking.class)
public class BookingVo implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	private String tenantId;

	private Long id;

	private Long cinemaId;
	
	private String cinemaName;

	private Long customerId;

	private String nickName;
	
	private Long numTicket;

	private Long promotionId;

	private Long totalPrice;

	private String paymentFlag;

	private Long showtimeId;
	
	private String movieName;

	private Date createTime;
}
