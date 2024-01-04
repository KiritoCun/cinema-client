package vn.udn.dut.cinema.common.core.domain.model;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * SMS login object
 *
 * @author HoaLD
 */

@Data
public class SmsLoginBody {

    /**
     * tenant ID
     */
    @NotBlank(message = "{tenant.number.not.blank}")
    private String tenantId;

    /**
     * Phone number
     */
    @NotBlank(message = "{user.phonenumber.not.blank}")
    private String phonenumber;

    /**
     * SMS code
     */
    @NotBlank(message = "{sms.code.not.blank}")
    private String smsCode;

}
