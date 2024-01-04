package vn.udn.dut.cinema.port.domain.bo;

import java.util.Date;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.cinema.port.domain.Booking;

/**
 * Booking business object
 *
 * @author HoaLD
 * @date 2023-11-07
 */
@Data
@AutoMapper(target = Booking.class, reverseConvertGenerate = false)
public class BookingBo {

	private String tenantId;

	private Long id;

	private Long cinemaId;

	private Long customerId;

	private Long numTicket;

	private Long promotionId;

	private Long totalPrice;

	private String paymentFlag;

	private Long showtimeId;

	private Date createTime;
}
