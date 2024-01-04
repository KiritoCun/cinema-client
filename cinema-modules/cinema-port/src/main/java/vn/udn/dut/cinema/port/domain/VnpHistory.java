package vn.udn.dut.cinema.port.domain;

import java.io.Serial;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.cinema.common.tenant.core.TenantEntity;

/**
 * VN PAY History object vnp_history
 *
 * @author HoaLD
 * @date 2023-12-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("vnp_history")
public class VnpHistory extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId(value = "id")
	private Long id;

	/**
	*
	*/
	private Long cinemaId;

	/**
	 *
	 */
	private String transactionId;

	/**
	 *
	 */
	private Long amount;

	/**
	 *
	 */
	private String secureHashType;

	/**
	 *
	 */
	private String secureHash;

	/**
	 *
	 */
	private String orderInfo;

	/**
	 *
	 */
	private String status;

	/**
	 *
	 */
	private Long vnpAmount;

	/**
	 *
	 */
	private String vnpTransactionId;

	/**
	 *
	 */
	private String vnpOrderInfo;

	/**
	 *
	 */
	private String vnpBankCode;

	/**
	 *
	 */
	private String vnpBankTranNo;

	/**
	 *
	 */
	private String vnpCardType;

	/**
	 *
	 */
	private Date vnpPayDate;

	/**
	 *
	 */
	private String vnpTransactionNo;

	/**
	 *
	 */
	private String vnpResponseCode;

	/**
	 *
	 */
	private String vnpTransactionStatus;

	/**
	 *
	 */
	private String vnpTxnRef;

	/**
	 *
	 */
	private String vnpSecureHashType;

	/**
	 *
	 */
	private String vnpSecureHash;
}
