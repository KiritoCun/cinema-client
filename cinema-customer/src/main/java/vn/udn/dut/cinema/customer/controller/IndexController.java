package vn.udn.dut.cinema.customer.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import vn.udn.dut.cinema.common.core.config.CinemaConfig;
import vn.udn.dut.cinema.common.core.domain.R;
import vn.udn.dut.cinema.common.core.domain.model.LoginUser;
import vn.udn.dut.cinema.common.core.utils.StringUtils;
import vn.udn.dut.cinema.common.satoken.utils.LoginHelper;
import vn.udn.dut.cinema.port.domain.vo.CustomerInfoVo;
import vn.udn.dut.cinema.port.domain.vo.CustomerVo;
import vn.udn.dut.cinema.port.service.ICustomerService;
import vn.udn.dut.cinema.system.constant.SystemConstants;
import vn.udn.dut.cinema.system.domain.SysMenu;
import vn.udn.dut.cinema.system.domain.vo.RouterVo;
import vn.udn.dut.cinema.system.domain.vo.SysDictDataVo;
import vn.udn.dut.cinema.system.service.ISysDictTypeService;
import vn.udn.dut.cinema.system.service.ISysMenuService;

/**
 * Front page
 *
 * @author HoaLD
 */
@SaIgnore
@RequiredArgsConstructor
@RestController
public class IndexController {
	
	private final ISysMenuService menuService;
	private final ISysDictTypeService dictTypeService;
	private final ICustomerService customerService;

	/**
	 * System basic configuration
	 */
	private final CinemaConfig cinemaConfig;

	/**
	 * Visit home page, prompt
	 */
	@GetMapping("/")
	public String index() {
		return StringUtils.format(
				"Welcome to {} Cinema Customer, current version: v{}, please visit through the front-end address.",
				cinemaConfig.getName(), cinemaConfig.getVersion());
	}

	/**
	 * Get user information
	 *
	 * @return User Info
	 */
	@GetMapping("/system/user/getInfo")
	public R<CustomerInfoVo> getInfo() {
		CustomerInfoVo userInfoVo = new CustomerInfoVo();
		LoginUser loginUser = LoginHelper.getLoginUser();
		CustomerVo customerUser = customerService.selectByUserId(loginUser.getUserId());
		userInfoVo.setUser(customerUser);
		return R.ok(userInfoVo);
	}

	/**
	 * Get routing information
	 *
	 * @return routing information
	 */
	@GetMapping("/system/menu/getRouters")
	public R<List<RouterVo>> getRouters() {
		List<SysMenu> menus = menuService.selectMenuTreeByUserId(LoginHelper.getUserId(),
				SystemConstants.SYSTEM_TYPE_CUSTOMER);
		return R.ok(menuService.buildMenus(menus));
	}

	/**
	 * Query dictionary data information according to dictionary type
	 *
	 * @param dictType dictionary type
	 */
	@GetMapping(value = "/system/dict/data/type/{dictType}")
	public R<List<SysDictDataVo>> dictType(@PathVariable String dictType) {
		List<SysDictDataVo> data = dictTypeService.selectDictDataByType(dictType);
		if (ObjectUtil.isNull(data)) {
			data = new ArrayList<>();
		}
		return R.ok(data);
	}

}
