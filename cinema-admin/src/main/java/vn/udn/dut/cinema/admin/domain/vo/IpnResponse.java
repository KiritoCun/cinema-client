package vn.udn.dut.cinema.admin.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Response after
 *
 * @author HoaLD
 */
@Data
@AllArgsConstructor
public class IpnResponse {

	@JsonProperty("RspCode")
	private String rspCode;

	@JsonProperty("Message")
	private String message;

	public static IpnResponse success() {
		return new IpnResponse("00", "Confirm Success");
	}

	public static IpnResponse orderAlreadyConfirmed() {
		return new IpnResponse("02", "Order already confirmed");
	}

	public static IpnResponse invalidAmount() {
		return new IpnResponse("04", "Invalid Amount");
	}

	public static IpnResponse orderNotFound() {
		return new IpnResponse("01", "Order not Found");
	}

	public static IpnResponse invalidChecksum() {
		return new IpnResponse("97", "Invalid Checksum");
	}

	public static IpnResponse unknowError() {
		return new IpnResponse("99", "Unknow error");
	}
}
