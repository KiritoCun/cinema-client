package vn.udn.dut.cinema.common.core.domain.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * email login object
 *
 * @author HoaLD
 */

@Data
public class EmailLoginBody {

    /**
     * tenant ID
     */
    @NotBlank(message = "{tenant.number.not.blank}")
    private String tenantId;

    /**
     * email
     */
    @NotBlank(message = "{user.email.not.blank}")
    @Email(message = "{user.email.not.valid}")
    private String email;

    /**
     * email code
     */
    @NotBlank(message = "{email.code.not.blank}")
    private String emailCode;

}
