package vn.udn.dut.cinema.port.enums;

public enum VnpayTransactionStatus {
	
	SUCCESS("00", "Giao dịch thành công"), 
	INCOMPLETE("01", "Giao dịch chưa hoàn tất"),
	ERROR("02", "Giao dịch bị lỗi"),
	REVERSED("04", "Giao dịch đảo (Khách hàng đã bị trừ tiền tại Ngân hàng nhưng GD chưa thành công ở VNPAY)"),
	PROCESSING("05", "VNPAY đang xử lý giao dịch này (GD hoàn tiền)"),
	REQUEST_SENT("06", "VNPAY đã gửi yêu cầu hoàn tiền sang Ngân hàng (GD hoàn tiền)"),
	SUSPECTED_FRAUD("07", "Giao dịch bị nghi ngờ gian lận"), 
	REFUND_DENIED("09", "GD Hoàn trả bị từ chối");

	private final String code;
	private final String description;

	VnpayTransactionStatus(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
}
