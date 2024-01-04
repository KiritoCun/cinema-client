package vn.udn.dut.cinema.customer.domain.vo;

import lombok.Data;

@Data
public class PaymentInfoVo {
	
	private Long showtimeId;
	
	private String orderInfo;
	
	private Long amount;
	
	private String transactionId;
}
