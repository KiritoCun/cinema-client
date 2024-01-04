package vn.udn.dut.cinema.common.web.enums;

import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.ShearCaptcha;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Captcha category
 *
 * @author HoaLD
 */
@Getter
@AllArgsConstructor
public enum CaptchaCategory {

    /**
     * line interference
     */
    LINE(LineCaptcha.class),

    /**
     * circle interference
     */
    CIRCLE(CircleCaptcha.class),

    /**
     * distortion interference
     */
    SHEAR(ShearCaptcha.class);

    private final Class<? extends AbstractCaptcha> clazz;
}
