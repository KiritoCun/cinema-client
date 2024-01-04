package vn.udn.dut.cinema.customer.domain.vo;

import lombok.Data;

/**
 * Verification code information
 *
 * @author HoaLD
 */
@Data
public class CaptchaVo {

	/**
	 * Whether to open the verification code
	 */
	private Boolean captchaEnabled = true;

	private String uuid;

	/**
	 * Verification code image
	 */
	private String img;

}
