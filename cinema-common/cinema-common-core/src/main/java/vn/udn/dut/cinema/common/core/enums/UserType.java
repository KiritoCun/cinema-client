package vn.udn.dut.cinema.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import vn.udn.dut.cinema.common.core.utils.StringUtils;

/**
 * Equipment type
 * For multiple sets of user systems
 *
 * @author HoaLD
 */
@Getter
@AllArgsConstructor
public enum UserType {

    /**
     * pc side
     */
    SYS_USER("sys_user"),

    /**
     * app side
     */
    APP_USER("app_user");

    private final String userType;

    public static UserType getUserType(String str) {
        for (UserType value : values()) {
            if (StringUtils.contains(str, value.getUserType())) {
                return value;
            }
        }
        return UserType.SYS_USER;
    }
}
