package vn.udn.dut.cinema.port.enums;

public enum VnpayResponseCode {
	TRANSACTION_SUCCESS("00", "Giao dịch thành công"),
    TRANSACTION_SUSPECTED("07", "Trừ tiền thành công. Giao dịch bị nghi ngờ (liên quan tới lừa đảo, giao dịch bất thường)"),
    CUSTOMER_NOT_REGISTERED("09", "Giao dịch không thành công do: Thẻ/Tài khoản của khách hàng chưa đăng ký dịch vụ InternetBanking tại ngân hàng"),
    INCORRECT_AUTHENTICATION("10", "Giao dịch không thành công do: Khách hàng xác thực thông tin thẻ/tài khoản không đúng quá 3 lần"),
    EXPIRED_TRANSACTION("11", "Giao dịch không thành công do: Đã hết hạn chờ thanh toán. Xin quý khách vui lòng thực hiện lại giao dịch"),
    LOCKED_ACCOUNT("12", "Giao dịch không thành công do: Thẻ/Tài khoản của khách hàng bị khóa"),
    INCORRECT_OTP("13", "Giao dịch không thành công do Quý khách nhập sai mật khẩu xác thực giao dịch (OTP). Xin quý khách vui lòng thực hiện lại giao dịch"),
    CANCELED_TRANSACTION("24", "Giao dịch không thành công do: Khách hàng hủy giao dịch"),
    INSUFFICIENT_BALANCE("51", "Giao dịch không thành công do: Tài khoản của quý khách không đủ số dư để thực hiện giao dịch"),
    EXCEEDED_DAILY_LIMIT("65", "Giao dịch không thành công do: Tài khoản của Quý khách đã vượt quá hạn mức giao dịch trong ngày"),
    BANK_UNDER_MAINTENANCE("75", "Ngân hàng thanh toán đang bảo trì"),
    INCORRECT_PAYMENT_PASSWORD("79", "Giao dịch không thành công do: KH nhập sai mật khẩu thanh toán quá số lần quy định. Xin quý khách vui lòng thực hiện lại giao dịch"),
    OTHER_ERRORS("99", "Các lỗi khác (lỗi còn lại, không có trong danh sách mã lỗi đã liệt kê)");

    private final String vnpResponseCode;
    private final String description;

    VnpayResponseCode(String vnpResponseCode, String description) {
        this.vnpResponseCode = vnpResponseCode;
        this.description = description;
    }

    public String getVnpResponseCode() {
        return vnpResponseCode;
    }

    public String getDescription() {
        return description;
    }
}
