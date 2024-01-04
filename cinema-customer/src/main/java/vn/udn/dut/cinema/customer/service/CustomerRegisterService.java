package vn.udn.dut.cinema.customer.service;

import org.springframework.stereotype.Service;

import cn.dev33.satoken.secure.BCrypt;
import lombok.RequiredArgsConstructor;
import vn.udn.dut.cinema.common.core.constant.Constants;
import vn.udn.dut.cinema.common.core.constant.GlobalConstants;
import vn.udn.dut.cinema.common.core.domain.model.RegisterBody;
import vn.udn.dut.cinema.common.core.exception.user.CaptchaException;
import vn.udn.dut.cinema.common.core.exception.user.CaptchaExpireException;
import vn.udn.dut.cinema.common.core.exception.user.UserException;
import vn.udn.dut.cinema.common.core.utils.MessageUtils;
import vn.udn.dut.cinema.common.core.utils.ServletUtils;
import vn.udn.dut.cinema.common.core.utils.SpringUtils;
import vn.udn.dut.cinema.common.core.utils.StringUtils;
import vn.udn.dut.cinema.common.log.event.LogininforEvent;
import vn.udn.dut.cinema.common.redis.utils.RedisUtils;
import vn.udn.dut.cinema.port.domain.bo.CustomerBo;
import vn.udn.dut.cinema.port.service.ICustomerService;

/**
 * Registration verification method
 *
 * @author HoaLD
 */
@RequiredArgsConstructor
@Service
public class CustomerRegisterService {

    private final ICustomerService customerService;

    /**
     * register
     */
    public void register(RegisterBody registerBody) {
        String username = registerBody.getUsername();
        String password = registerBody.getPassword();
        CustomerBo customerUser = new CustomerBo();
        customerUser.setUserName(username);
        customerUser.setNickName(username);
        customerUser.setPassword(BCrypt.hashpw(password));

        if (!customerService.checkUserNameUnique(customerUser)) {
            throw new UserException("user.register.save.error", username);
        }
        boolean regFlag = customerService.registerUser(customerUser);
        if (!regFlag) {
            throw new UserException("user.register.error");
        }
    }

    /**
     * Verify verification code
     *
     * @param username username
     * @param code     verification code
     * @param uuid     Uniquely identifies
     */
    public void validateCaptcha(String tenantId, String username, String code, String uuid) {
        String verifyKey = GlobalConstants.CAPTCHA_CODE_KEY + StringUtils.defaultString(uuid, "");
        String captcha = RedisUtils.getCacheObject(verifyKey);
        RedisUtils.deleteObject(verifyKey);
        if (captcha == null) {
            recordLogininfor(tenantId, username, Constants.REGISTER, MessageUtils.message("user.jcaptcha.expire"));
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha)) {
            recordLogininfor(tenantId, username, Constants.REGISTER, MessageUtils.message("user.jcaptcha.error"));
            throw new CaptchaException();
        }
    }

    /**
     * Record login information
     *
     * @param tenantId tenant ID
     * @param username username
     * @param status   status
     * @param message  Message content
     * @return
     */
    private void recordLogininfor(String tenantId, String username, String status, String message) {
        LogininforEvent logininforEvent = new LogininforEvent();
        logininforEvent.setTenantId(tenantId);
        logininforEvent.setUsername(username);
        logininforEvent.setStatus(status);
        logininforEvent.setMessage(message);
        logininforEvent.setRequest(ServletUtils.getRequest());
        SpringUtils.context().publishEvent(logininforEvent);
    }

}
