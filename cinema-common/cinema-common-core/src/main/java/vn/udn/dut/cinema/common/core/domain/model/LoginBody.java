package vn.udn.dut.cinema.common.core.domain.model;

import lombok.Data;
import vn.udn.dut.cinema.common.core.constant.UserConstants;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;

/**
 * user login object
 *
 * @author HoaLD
 */

@Data
public class LoginBody {

    /**
     * tenant ID
     */
    private String tenantId;

    /**
     * username
     */
    @NotBlank(message = "{user.username.not.blank}")
    @Length(min = UserConstants.USERNAME_MIN_LENGTH, max = UserConstants.USERNAME_MAX_LENGTH, message = "{user.username.length.valid}")
    private String username;

    /**
     * user password
     */
    @NotBlank(message = "{user.password.not.blank}")
    @Length(min = UserConstants.PASSWORD_MIN_LENGTH, max = UserConstants.PASSWORD_MAX_LENGTH, message = "{user.password.length.valid}")
    private String password;

    /**
     * verification code
     */
    private String code;

    /**
     * Uniquely identifies
     */
    private String uuid;

}
