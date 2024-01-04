package vn.udn.dut.cinema.system.domain.bo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import vn.udn.dut.cinema.common.core.xss.Xss;
import vn.udn.dut.cinema.common.mybatis.core.domain.BaseEntity;
import vn.udn.dut.cinema.common.sensitive.annotation.Sensitive;
import vn.udn.dut.cinema.common.sensitive.core.SensitiveStrategy;

/**
 * Personal information business processing
 *
 * @author HoaLD
 */

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SysUserProfileBo extends BaseEntity {

    private static final long serialVersionUID = -979345255470029431L;

	/**
     * User ID
     */
    private Long userId;

    /**
     * User's Nickname
     */
    @Xss(message = "User name cannot contain script characters")
    @Size(min = 0, max = 30, message = "User name length cannot exceed {max} characters")
    private String nickName;

    /**
     * user mailbox
     */
    @Sensitive(strategy = SensitiveStrategy.EMAIL)
    @Email(message = "Email format is incorrect")
    @Size(min = 0, max = 50, message = "The email length cannot exceed {max} characters")
    private String email;

    /**
     * phone number
     */
    @Sensitive(strategy = SensitiveStrategy.PHONE)
    private String phonenumber;

    /**
     * User gender (0 male, 1 female, 2 unknown)
     */
    private String sex;

}
