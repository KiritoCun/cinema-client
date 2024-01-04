package vn.udn.dut.cinema.common.web.enums;

import cn.hutool.captcha.generator.CodeGenerator;
import cn.hutool.captcha.generator.RandomGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import vn.udn.dut.cinema.common.web.utils.UnsignedMathGenerator;

/**
 * Captcha type
 *
 * @author HoaLD
 */
@Getter
@AllArgsConstructor
public enum CaptchaType {

    /**
     * number
     */
    MATH(UnsignedMathGenerator.class),

    /**
     * character
     */
    CHAR(RandomGenerator.class);

    private final Class<? extends CodeGenerator> clazz;
}
