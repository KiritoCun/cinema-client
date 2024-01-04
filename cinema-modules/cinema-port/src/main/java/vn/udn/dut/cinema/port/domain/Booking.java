package vn.udn.dut.cinema.port.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * Booking object
 *
 * @author HoaLD
 * @date 2023-11-07
 */
@Data
@TableName("booking")
public class Booking {
	
	private String tenantId;

	@TableId(value = "id")
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
