package vn.udn.dut.cinema.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * login type
 *
 * @author HoaLD
 */
@Getter
@AllArgsConstructor
public enum LoginType {

    /**
     * password login
     */
    PASSWORD("user.password.retry.limit.exceed", "user.password.retry.limit.count"),

    /**
     * SMS login
     */
    SMS("sms.code.retry.limit.exceed", "sms.code.retry.limit.count"),

    /**
     * Email Login
     */
    EMAIL("email.code.retry.limit.exceed", "email.code.retry.limit.count"),

    /**
     * Mini Program Login
     */
    XCX("", "");

    /**
     * Login retry limit exceeded prompt
     */
    final String retryLimitExceed;

    /**
     * Login Retry Limit Count Prompt
     */
    final String retryLimitCount;
}
