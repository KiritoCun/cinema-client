package vn.udn.dut.cinema.customer.controller;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.captcha.generator.CodeGenerator;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vn.udn.dut.cinema.common.core.constant.Constants;
import vn.udn.dut.cinema.common.core.constant.GlobalConstants;
import vn.udn.dut.cinema.common.core.domain.R;
import vn.udn.dut.cinema.common.core.utils.SpringUtils;
import vn.udn.dut.cinema.common.core.utils.StringUtils;
import vn.udn.dut.cinema.common.core.utils.reflect.ReflectUtils;
import vn.udn.dut.cinema.common.redis.utils.RedisUtils;
import vn.udn.dut.cinema.common.sms.config.properties.SmsProperties;
import vn.udn.dut.cinema.common.sms.core.SmsTemplate;
import vn.udn.dut.cinema.common.sms.entity.SmsResult;
import vn.udn.dut.cinema.common.web.config.properties.CaptchaProperties;
import vn.udn.dut.cinema.common.web.enums.CaptchaType;
import vn.udn.dut.cinema.customer.domain.vo.CaptchaVo;

/**
 * Captcha operation processing
 *
 * @author HoaLD
 */
@SaIgnore
@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
public class CaptchaController {

	private final CaptchaProperties captchaProperties;
	private final SmsProperties smsProperties;

	/**
	 * SMS verification code
	 *
	 * @param phonenumber User phone number
	 */
	@GetMapping("/resource/sms/code")
	public R<Void> smsCode(@NotBlank(message = "{user.phonenumber.not.blank}") String phonenumber) {
		if (!smsProperties.getEnabled()) {
			return R.fail("The current system does not enable SMS function!");
		}
		String key = GlobalConstants.CAPTCHA_CODE_KEY + phonenumber;
		String code = RandomUtil.randomNumbers(4);
		RedisUtils.setCacheObject(key, code, Duration.ofMinutes(Constants.CAPTCHA_EXPIRATION));
		// The verification code template id can be processed by itself (check the
		// database or write it to death)
		String templateId = "";
		Map<String, String> map = new HashMap<>(1);
		map.put("code", code);
		SmsTemplate smsTemplate = SpringUtils.getBean(SmsTemplate.class);
		SmsResult result = smsTemplate.send(phonenumber, templateId, map);
		if (!result.isSuccess()) {
			log.error("Verification code SMS sending exception => {}", result);
			return R.fail(result.getMessage());
		}
		return R.ok();
	}

	/**
	 * generate verification code
	 */
	@GetMapping("/code")
	public R<CaptchaVo> getCode() {
		CaptchaVo captchaVo = new CaptchaVo();
		boolean captchaEnabled = captchaProperties.getEnable();
		if (!captchaEnabled) {
			captchaVo.setCaptchaEnabled(false);
			return R.ok(captchaVo);
		}
		// Save verification code information
		String uuid = IdUtil.simpleUUID();
		String verifyKey = GlobalConstants.CAPTCHA_CODE_KEY + uuid;
		// generate verification code
		CaptchaType captchaType = captchaProperties.getType();
		boolean isMath = CaptchaType.MATH == captchaType;
		Integer length = isMath ? captchaProperties.getNumberLength() : captchaProperties.getCharLength();
		CodeGenerator codeGenerator = ReflectUtils.newInstance(captchaType.getClazz(), length);
		AbstractCaptcha captcha = SpringUtils.getBean(captchaProperties.getCategory().getClazz());
		captcha.setGenerator(codeGenerator);
		captcha.createCode();
		String code = captcha.getCode();
		if (isMath) {
			ExpressionParser parser = new SpelExpressionParser();
			Expression exp = parser.parseExpression(StringUtils.remove(code, "="));
			code = exp.getValue(String.class);
		}
		RedisUtils.setCacheObject(verifyKey, code, Duration.ofMinutes(Constants.CAPTCHA_EXPIRATION));
		captchaVo.setUuid(uuid);
		captchaVo.setImg(captcha.getImageBase64());
		return R.ok(captchaVo);
	}

}
