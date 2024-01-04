package vn.udn.dut.cinema.common.web.config.properties;

import lombok.Data;
import vn.udn.dut.cinema.common.web.enums.CaptchaCategory;
import vn.udn.dut.cinema.common.web.enums.CaptchaType;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Captcha Configuration Properties
 *
 * @author HoaLD
 */
@Data
@ConfigurationProperties(prefix = "captcha")
public class CaptchaProperties {

    private Boolean enable;

    /**
     * Captcha type
     */
    private CaptchaType type;

    /**
     * Captcha category
     */
    private CaptchaCategory category;

    /**
     * Digits of digital verification code
     */
    private Integer numberLength;

    /**
     * character verification code length
     */
    private Integer charLength;
}
