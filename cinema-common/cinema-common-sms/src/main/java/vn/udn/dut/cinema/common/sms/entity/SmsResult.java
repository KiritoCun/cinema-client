package vn.udn.dut.cinema.common.sms.entity;

import lombok.Builder;
import lombok.Data;

/**
 * upload return body
 *
 * @author HoaLD
 */
@Data
@Builder
public class SmsResult {

    /**
     * whether succeed
     */
    private boolean isSuccess;

    /**
     * response message
     */
    private String message;

    /**
     * actual response body
     * <p>
     * Can be converted to SendSmsResponse corresponding to SDK
     */
    private String response;
}
