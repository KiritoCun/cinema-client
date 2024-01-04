package vn.udn.dut.cinema.common.web.config;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.ShearCaptcha;
import vn.udn.dut.cinema.common.web.config.properties.CaptchaProperties;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

import java.awt.*;

/**
 * Verification code configuration
 *
 * @author HoaLD
 */
@AutoConfiguration
@EnableConfigurationProperties(CaptchaProperties.class)
public class CaptchaConfig {

    private static final int WIDTH = 160;
    private static final int HEIGHT = 60;
    private static final Color BACKGROUND = Color.PINK;
    private static final Font FONT = new Font("Arial", Font.BOLD, 48);

    /**
     * Circle Interference Captcha
     */
    @Lazy
    @Bean
    public CircleCaptcha circleCaptcha() {
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(WIDTH, HEIGHT);
        captcha.setBackground(BACKGROUND);
        captcha.setFont(FONT);
        return captcha;
    }

    /**
     * Verification code for line segment interference
     */
    @Lazy
    @Bean
    public LineCaptcha lineCaptcha() {
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(WIDTH, HEIGHT);
        captcha.setBackground(BACKGROUND);
        captcha.setFont(FONT);
        return captcha;
    }

    /**
     * Distorted Interference Captcha
     */
    @Lazy
    @Bean
    public ShearCaptcha shearCaptcha() {
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(WIDTH, HEIGHT);
        captcha.setBackground(BACKGROUND);
        captcha.setFont(FONT);
        return captcha;
    }

}