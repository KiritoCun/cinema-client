package vn.udn.dut.cinema.port.domain.bo;

import java.util.Date;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.cinema.common.mybatis.core.domain.BaseEntity;
import vn.udn.dut.cinema.port.domain.VnpHistory;

/**
 * VN PAY History business object vnp_history
 *
 * @author HoaLD
 * @date 2023-12-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = VnpHistory.class, reverseConvertGenerate = false)
public class VnpHistoryBo extends BaseEntity {
		
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
}
