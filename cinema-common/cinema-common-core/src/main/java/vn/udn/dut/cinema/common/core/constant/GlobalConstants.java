package vn.udn.dut.cinema.common.core.constant;

/**
 * Global key constants (business-independent keys)
 *
 * @author HoaLD
 */
public interface GlobalConstants {

    /**
     * Global redis key (business-independent key)
     */
    String GLOBAL_REDIS_KEY = "global:";

    /**
     * verification code redis key
     */
    String CAPTCHA_CODE_KEY = GLOBAL_REDIS_KEY + "captcha_codes:";

    /**
     * Anti-resubmission redis key
     */
    String REPEAT_SUBMIT_KEY = GLOBAL_REDIS_KEY + "repeat_submit:";

    /**
     * Current limiting redis key
     */
    String RATE_LIMIT_KEY = GLOBAL_REDIS_KEY + "rate_limit:";

    /**
     * Login account password error times redis key
     */
    String PWD_ERR_CNT_KEY = GLOBAL_REDIS_KEY + "pwd_err_cnt:";
}
