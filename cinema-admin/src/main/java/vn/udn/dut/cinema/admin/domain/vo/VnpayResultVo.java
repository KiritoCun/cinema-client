package vn.udn.dut.cinema.admin.domain.vo;

import lombok.Data;
import vn.udn.dut.cinema.common.core.utils.StringUtils;

/**
 * Response after
 *
 * @author HoaLD
 */
@Data
public class VnpayResultVo {

	private String vnp_TmnCode;

	private Long vnp_Amount;

	private String vnp_BankCode;

	private String vnp_BankTranNo;

	private String vnp_CardType;

	private String vnp_PayDate;

	private String vnp_OrderInfo;

	private String vnp_TransactionNo;

	private String vnp_ResponseCode;

	private String vnp_TransactionStatus;

	private String vnp_TxnRef;

	private String vnp_SecureHashType;

	private String vnp_SecureHash;
	
	public boolean isSuccess() {
		if (StringUtils.isEmpty(this.vnp_TransactionStatus)) {
			return false;
		}
		if (!"00".equalsIgnoreCase(this.vnp_TransactionStatus)) {
			return false;
		}
		return true;
	}
}
