package vn.udn.dut.cinema.customer.controller.common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.dev33.satoken.annotation.SaIgnore;
import lombok.RequiredArgsConstructor;
import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.system.constant.SystemConstants;
import vn.udn.dut.cinema.system.domain.bo.SysNoticeBo;
import vn.udn.dut.cinema.system.domain.vo.SysNoticeVo;
import vn.udn.dut.cinema.system.service.ISysNoticeService;

/**
 * Front page
 *
 * @author HoaLD
 */
@SaIgnore
@RequiredArgsConstructor
@RestController
@RequestMapping("/bulletin")
public class BulletinController {

    private final ISysNoticeService noticeService;

    
    @GetMapping("/list")
    public TableDataInfo<SysNoticeVo> list(SysNoticeBo bo, PageQuery pageQuery) {
    	noticeService.addFilterNoticeType(bo, SystemConstants.NOTICE_TYPE_SHIPPINGLINE);
    	bo.setStatus(SystemConstants.NOTICE_STATUS_ENABLE);
        return noticeService.selectPageNoticeList(bo, pageQuery);
    }
}
