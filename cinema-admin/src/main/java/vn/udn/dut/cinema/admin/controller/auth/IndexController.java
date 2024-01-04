package vn.udn.dut.cinema.admin.controller.auth;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.dev33.satoken.annotation.SaIgnore;
import lombok.RequiredArgsConstructor;
import vn.udn.dut.cinema.common.core.config.CinemaConfig;
import vn.udn.dut.cinema.common.core.domain.R;
import vn.udn.dut.cinema.common.core.utils.StringUtils;
import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.common.satoken.utils.LoginHelper;
import vn.udn.dut.cinema.system.domain.vo.SysNotificationVo;
import vn.udn.dut.cinema.system.service.ISysNotificationService;

/**
 * Front page
 *
 * @author HoaLD
 */
@SaIgnore
@RequiredArgsConstructor
@RestController
public class IndexController {

    /**
     * System basic configuration
     */
    private final CinemaConfig cinemaConfig;
    private final ISysNotificationService notificationService;

    /**
     * Visit home page, prompt
     */
    @GetMapping("/")
    public String index() {
        return StringUtils.format("Welcome to {} Cinema Admin, current version: v{}, please visit through the front-end address.", cinemaConfig.getName(), cinemaConfig.getVersion());
    }
    
    /**
     * Get notification
     *
     * @return routing information
     */
    @GetMapping("/system/notifications")
    public TableDataInfo<SysNotificationVo> getNotifications(PageQuery pageQuery) {
        return notificationService.queryPageListByUserId(LoginHelper.getUserId(), pageQuery);
    }
    
    /**
     * Get notification unseen count 
     *
     * @return routing information
     */
    @GetMapping("/system/notification/unseenCount")
    public R<Long> getNotificationUnseenCount() {
        return R.ok(notificationService.queryUnseenCountByUserId(LoginHelper.getUserId()));
    }
    
    /**
     * 
     * @return
     */
    @PutMapping("/system/notification/seen")
    public R<Void> seen() {
    	notificationService.seenAllNotification(LoginHelper.getUserId());
    	return R.ok();
    }
    
    /**
     * 
     * @return
     */
    @PutMapping("/system/notification/{id}/seen")
    public R<Void> seenById(@PathVariable Long id) {
    	notificationService.seenById(LoginHelper.getUserId(), id);
    	return R.ok();
    }
}
