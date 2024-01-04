package vn.udn.dut.cinema.admin.controller.auth;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.collection.CollUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import vn.udn.dut.cinema.admin.domain.vo.LoginTenantVo;
import vn.udn.dut.cinema.admin.domain.vo.LoginVo;
import vn.udn.dut.cinema.admin.domain.vo.TenantListVo;
import vn.udn.dut.cinema.admin.service.SysLoginService;
import vn.udn.dut.cinema.admin.service.SysRegisterService;
import vn.udn.dut.cinema.common.core.domain.R;
import vn.udn.dut.cinema.common.core.domain.model.EmailLoginBody;
import vn.udn.dut.cinema.common.core.domain.model.LoginBody;
import vn.udn.dut.cinema.common.core.domain.model.RegisterBody;
import vn.udn.dut.cinema.common.core.domain.model.SmsLoginBody;
import vn.udn.dut.cinema.common.core.utils.MapstructUtils;
import vn.udn.dut.cinema.common.core.utils.StreamUtils;
import vn.udn.dut.cinema.common.core.utils.StringUtils;
import vn.udn.dut.cinema.common.tenant.helper.TenantHelper;
import vn.udn.dut.cinema.system.domain.bo.SysTenantBo;
import vn.udn.dut.cinema.system.domain.vo.SysTenantVo;
import vn.udn.dut.cinema.system.service.ISysConfigService;
import vn.udn.dut.cinema.system.service.ISysTenantService;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.util.List;

/**
 * Certified
 *
 * @author HoaLD
 */
@SaIgnore
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final SysLoginService loginService;
    private final SysRegisterService registerService;
    private final ISysConfigService configService;
    private final ISysTenantService tenantService;

    /**
     * login method
     *
     * @param body login information
     * @return result
     */
    @PostMapping("/login")
    public R<LoginVo> login(@Validated @RequestBody LoginBody body) {
        LoginVo loginVo = new LoginVo();
        // generate token
        String token = loginService.login(
                body.getTenantId(),
                body.getUsername(), body.getPassword(),
                body.getCode(), body.getUuid());
        loginVo.setToken(token);
        return R.ok(loginVo);
    }

    /**
     * SMS login
     *
     * @param body login information
     * @return result
     */
    @PostMapping("/smsLogin")
    public R<LoginVo> smsLogin(@Validated @RequestBody SmsLoginBody body) {
        LoginVo loginVo = new LoginVo();
        // generate token
        String token = loginService.smsLogin(body.getTenantId(), body.getPhonenumber(), body.getSmsCode());
        loginVo.setToken(token);
        return R.ok(loginVo);
    }

    /**
     * email login
     *
     * @param body login information
     * @return result
     */
    @PostMapping("/emailLogin")
    public R<LoginVo> emailLogin(@Validated @RequestBody EmailLoginBody body) {
        LoginVo loginVo = new LoginVo();
        // generate token
        String token = loginService.emailLogin(body.getTenantId(), body.getEmail(), body.getEmailCode());
        loginVo.setToken(token);
        return R.ok(loginVo);
    }

    /**
     * Mini Program Login (Example)
     *
     * @param xcxCode Applet code
     * @return result
     */
    @PostMapping("/xcxLogin")
    public R<LoginVo> xcxLogin(@NotBlank(message = "{xcx.code.not.blank}") String xcxCode) {
        LoginVo loginVo = new LoginVo();
        // generate token
        String token = loginService.xcxLogin(xcxCode);
        loginVo.setToken(token);
        return R.ok(loginVo);
    }

    /**
     * sign out
     */
    @PostMapping("/logout")
    public R<Void> logout() {
        loginService.logout();
        return R.ok("Logout successfully");
    }

    /**
     * user registration
     */
    @PostMapping("/register")
    public R<Void> register(@Validated @RequestBody RegisterBody user) {
        if (!configService.selectRegisterEnabled(user.getTenantId())) {
            return R.fail("The current system does not open the registration function!");
        }
        registerService.register(user);
        return R.ok();
    }

    /**
     * Login page tenant drop-down box
     *
     * @return tenant list
     */
    @GetMapping("/tenant/list")
    public R<LoginTenantVo> tenantList(HttpServletRequest request) throws Exception {
        List<SysTenantVo> tenantList = tenantService.queryList(new SysTenantBo());
        List<TenantListVo> voList = MapstructUtils.convert(tenantList, TenantListVo.class);
        // get a domain name
        String host = new URL(request.getRequestURL().toString()).getHost();
        // filter by domain name
        List<TenantListVo> list = StreamUtils.filter(voList, vo -> StringUtils.equals(vo.getDomain(), host));
        // return object
        LoginTenantVo vo = new LoginTenantVo();
        vo.setVoList(CollUtil.isNotEmpty(list) ? list : voList);
        vo.setTenantEnabled(TenantHelper.isEnable());
        return R.ok(vo);
    }

}
