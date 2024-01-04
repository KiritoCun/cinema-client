package vn.udn.dut.cinema.admin.controller.monitor;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import vn.udn.dut.cinema.common.core.domain.R;
import vn.udn.dut.cinema.common.excel.utils.ExcelUtil;
import vn.udn.dut.cinema.common.log.annotation.Log;
import vn.udn.dut.cinema.common.log.enums.BusinessType;
import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.common.web.core.BaseController;
import vn.udn.dut.cinema.system.domain.bo.SysDataHistoryBo;
import vn.udn.dut.cinema.system.domain.vo.SysDataHistoryVo;
import vn.udn.dut.cinema.system.service.ISysDataHistoryService;

/**
 * System data history
 *
 * @author HoaLD
 * @date 2023-08-23
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/monitor/dataHistory")
public class SysDataHistoryController extends BaseController {

    private final ISysDataHistoryService sysDataHistoryService;

    /**
     * Query System data history list
     */
    @SaCheckPermission("monitor:dataHistory:list")
    @GetMapping("/list")
    public TableDataInfo<SysDataHistoryVo> list(SysDataHistoryBo bo, PageQuery pageQuery) {
        return sysDataHistoryService.queryPageList(bo, pageQuery);
    }

    /**
     * Export System data history list
     */
    @SaCheckPermission("monitor:dataHistory:export")
    @Log(title = "System data history", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(SysDataHistoryBo bo, HttpServletResponse response) {
        List<SysDataHistoryVo> list = sysDataHistoryService.queryList(bo);
        ExcelUtil.exportExcel(list, "System data history", SysDataHistoryVo.class, response);
    }

    /**
     * Get System data history detail
     *
     * @param id primary key
     */
    @SaCheckPermission("monitor:dataHistory:query")
    @GetMapping("/{id}")
    public R<SysDataHistoryVo> getInfo(@NotNull(message = "Primary key cannot be empty")
                                     @PathVariable Long id) {
        return R.ok(sysDataHistoryService.queryById(id));
    }

    /**
     * Delete System data history
     *
     * @param ids primary key string
     */
    @SaCheckPermission("monitor:dataHistory:remove")
    @Log(title = "System data history", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "Primary key cannot be empty")
                          @PathVariable Long[] ids) {
        return toAjax(sysDataHistoryService.deleteWithValidByIds(List.of(ids), true));
    }
}
