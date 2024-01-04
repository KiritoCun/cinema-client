package vn.udn.dut.cinema.port.domain.vo;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.cinema.port.domain.VnpHistory;

/**
 * VN PAY History view object vnp_history
 *
 * @author HoaLD
 * @date 2023-12-26
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = VnpHistory.class)
public class VnpHistoryVo implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
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

	/**
	 *
	 */
	private Long createBy;
}
