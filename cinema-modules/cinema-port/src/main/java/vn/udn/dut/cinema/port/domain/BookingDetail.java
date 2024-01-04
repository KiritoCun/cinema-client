package vn.udn.dut.cinema.port.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * Booking detail object
 *
 * @author HoaLD
 * @date 2023-11-07
 */
@Data
@TableName("booking_detail")
public class BookingDetail {

	private String tenantId;

	@TableId(value = "id")
	private Long id;

	private Long cinemaId;

	private Long bookingId;

	private Long seatId;
}
