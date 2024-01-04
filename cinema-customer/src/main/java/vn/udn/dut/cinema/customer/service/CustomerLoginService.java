package vn.udn.dut.cinema.customer.service;

import java.time.Duration;
import java.util.Date;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vn.udn.dut.cinema.common.core.constant.GlobalConstants;
import vn.udn.dut.cinema.common.core.constant.TenantConstants;
import vn.udn.dut.cinema.common.core.domain.model.LoginUser;
import vn.udn.dut.cinema.common.core.domain.model.XcxLoginUser;
import vn.udn.dut.cinema.common.core.enums.DeviceType;
import vn.udn.dut.cinema.common.core.enums.LoginType;
import vn.udn.dut.cinema.common.core.enums.TenantStatus;
import vn.udn.dut.cinema.common.core.enums.UserStatus;
import vn.udn.dut.cinema.common.core.exception.user.CaptchaException;
import vn.udn.dut.cinema.common.core.exception.user.CaptchaExpireException;
import vn.udn.dut.cinema.common.core.exception.user.UserException;
import vn.udn.dut.cinema.common.core.utils.ServletUtils;
import vn.udn.dut.cinema.common.core.utils.SpringUtils;
import vn.udn.dut.cinema.common.core.utils.StringUtils;
import vn.udn.dut.cinema.common.log.event.LogininforEvent;
import vn.udn.dut.cinema.common.redis.utils.RedisUtils;
import vn.udn.dut.cinema.common.satoken.utils.LoginHelper;
import vn.udn.dut.cinema.common.tenant.exception.TenantException;
import vn.udn.dut.cinema.common.tenant.helper.TenantHelper;
import vn.udn.dut.cinema.port.domain.Customer;
import vn.udn.dut.cinema.port.domain.vo.CustomerVo;
import vn.udn.dut.cinema.port.mapper.CustomerMapper;
import vn.udn.dut.cinema.system.domain.vo.SysTenantVo;
import vn.udn.dut.cinema.system.domain.vo.SysUserVo;
import vn.udn.dut.cinema.system.service.ISysTenantService;

/**
 * Login verification method
 *
 * @author HoaLD
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class CustomerLoginService {

	private final CustomerMapper customerMapper;
	private final ISysTenantService tenantService;

	@Value("${user.password.maxRetryCount}")
	private Integer maxRetryCount;

	@Value("${user.password.lockTime}")
	private Integer lockTime;

	/**
	 * Login authentication
	 *
	 * @param username username
	 * @param password password
	 * @param code     verification code
	 * @param uuid     Uniquely identifies
	 * @return result
	 */
	public String login(String tenantId, String username, String password, String code, String uuid) {
		// Framework login does not limit what table to query from as long as LoginUser
		// is finally constructed
		CustomerVo customerUser = loadUserByUsername(tenantId, username);
		checkLogin(LoginType.PASSWORD, tenantId, username, () -> !BCrypt.checkpw(password, customerUser.getPassword()));
		// Here you can create the loginUser attribute by yourself according to the different data of the login user. It is not enough to inherit the extension.
        LoginUser loginUser = buildLoginUser(customerUser);
        // generate token
        LoginHelper.loginByDevice(loginUser, DeviceType.PC);

        recordLogininfor(loginUser.getTenantId(), username, "Success", "Đăng nhập thành công");
        recordLoginInfo(customerUser.getUserId());
		return StpUtil.getTokenValue();
	}

	public String xcxLogin(String xcxCode) {
		// xcxCode is obtained after calling wx.login authorization for the applet
		// The following todos are self-implemented
		// Verify appid + appsrcret + xcxCode Call the login credential verification
		// interface to get session_key and openid
		String openid = "";
		// Framework login does not limit what table to query from as long as LoginUser
		// is finally constructed
		SysUserVo user = loadUserByOpenid(openid);
		// verify tenant
		checkTenant(user.getTenantId());

		// Here you can create the loginUser attribute by yourself according to the
		// different data of the login user. It is not enough to inherit the extension.
		XcxLoginUser loginUser = new XcxLoginUser();
		loginUser.setTenantId(user.getTenantId());
		loginUser.setUserId(user.getUserId());
		loginUser.setUsername(user.getUserName());
		loginUser.setUserType(user.getUserType());
		loginUser.setOpenid(openid);
		// generate token
		LoginHelper.loginByDevice(loginUser, DeviceType.XCX);
		return StpUtil.getTokenValue();
	}

	/**
	 * sign out
	 */
	public void logout() {
		try {
			if (TenantHelper.isEnable() && LoginHelper.isSuperAdmin()) {
				// Customer log out to clear dynamic tenants
				TenantHelper.clearDynamic();
			}
			StpUtil.logout();
		} catch (NotLoginException ignored) {
		}
	}
	
    /**
     * Record login information
     *
     * @param tenantId tenant ID
     * @param username username
     * @param status   status
     * @param message  Message content
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
    
    /**
     * Record login information
     *
     * @param userId User ID
     */
    public void recordLoginInfo(Long userId) {
        Customer customerUser = new Customer();
        customerUser.setUserId(userId);
        customerUser.setUpdateBy(userId);
        customerMapper.updateById(customerUser);
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
			throw new CaptchaExpireException();
		}
		if (!code.equalsIgnoreCase(captcha)) {
			throw new CaptchaException();
		}
	}

	private CustomerVo loadUserByUsername(String tenantId, String username) {
		Customer customerUser = customerMapper.selectOne(new LambdaQueryWrapper<Customer>()
				.select(Customer::getUserName, Customer::getStatus)
				.eq(TenantHelper.isEnable(), Customer::getTenantId, tenantId).eq(Customer::getUserName, username));
		if (ObjectUtil.isNull(customerUser)) {
			log.info("Login user: {} does not exist.", username);
			throw new UserException("user.not.exists", username);
		} else if (UserStatus.DISABLE.getCode().equals(customerUser.getStatus())) {
			log.info("Login user: {} has been deactivated.", username);
			throw new UserException("user.blocked", username);
		}
		return customerMapper.selectUserCustomerByUserName(username);
	}

	private SysUserVo loadUserByOpenid(String openid) {
		// Use openid to query the bound user. If the user is not bound, handle it
		// according to the business. For example, create a default user
		// implements userService.selectUserByOpenid(openid);
		SysUserVo user = new SysUserVo();
		if (ObjectUtil.isNull(user)) {
			log.info("Login user: {} does not exist.", openid);
			// The user does not exist, the business logic is implemented by itself
		} else if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
			log.info("Login user: {} has been deactivated.", openid);
			// User has been deactivated Business logic implements itself
		}
		return user;
	}

	/**
	 * build login user
	 */
	private LoginUser buildLoginUser(CustomerVo customerUser) {
		LoginUser loginUser = new LoginUser();
		loginUser.setUserId(customerUser.getUserId());
		loginUser.setUsername(customerUser.getUserName());
		return loginUser;
	}

	/**
	 * login verification
	 */
	private void checkLogin(LoginType loginType, String tenantId, String username, Supplier<Boolean> supplier) {
		String errorKey = GlobalConstants.PWD_ERR_CNT_KEY + username;

		// Obtain the number of user login errors (customizable limit strategy, such as:
		// key + username + ip)
		Integer errorNumber = RedisUtils.getCacheObject(errorKey);
		// If you log in during the lockout time, you will be kicked out
		if (ObjectUtil.isNotNull(errorNumber) && errorNumber.equals(maxRetryCount)) {
			throw new UserException(loginType.getRetryLimitExceed(), maxRetryCount, lockTime);
		}

		if (supplier.get()) {
			// Is it the first time
			errorNumber = ObjectUtil.isNull(errorNumber) ? 1 : errorNumber + 1;
			// When the specified number of errors is reached, the login will be locked
			if (errorNumber.equals(maxRetryCount)) {
				RedisUtils.setCacheObject(errorKey, errorNumber, Duration.ofMinutes(lockTime));
				throw new UserException(loginType.getRetryLimitExceed(), maxRetryCount, lockTime);
			} else {
				// Increment if the specified number of errors is not reached
				RedisUtils.setCacheObject(errorKey, errorNumber);
				throw new UserException(loginType.getRetryLimitCount(), errorNumber);
			}
		}

		// Log in successfully Clear the number of errors
		RedisUtils.deleteObject(errorKey);
	}

	private void checkTenant(String tenantId) {
		if (!TenantHelper.isEnable()) {
			return;
		}
		if (TenantConstants.DEFAULT_TENANT_ID.equals(tenantId)) {
			return;
		}
		SysTenantVo tenant = tenantService.queryByTenantId(tenantId);
		if (ObjectUtil.isNull(tenant)) {
			log.info("Login tenant: {} does not exist.", tenantId);
			throw new TenantException("tenant.not.exists");
		} else if (TenantStatus.DISABLE.getCode().equals(tenant.getStatus())) {
			log.info("Login tenant: {} has been disabled.", tenantId);
			throw new TenantException("tenant.blocked");
		} else if (ObjectUtil.isNotNull(tenant.getExpireTime()) && new Date().after(tenant.getExpireTime())) {
			log.info("Login tenant: {} has expired.", tenantId);
			throw new TenantException("tenant.expired");
		}
	}

}
