package vn.udn.dut.cinema.admin.controller.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import vn.udn.dut.cinema.common.core.domain.R;
import vn.udn.dut.cinema.common.excel.utils.ExcelUtil;
import vn.udn.dut.cinema.common.log.annotation.Log;
import vn.udn.dut.cinema.common.log.enums.BusinessType;
import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.common.web.core.BaseController;
import vn.udn.dut.cinema.system.domain.bo.SysConfigBo;
import vn.udn.dut.cinema.system.domain.vo.SysConfigVo;
import vn.udn.dut.cinema.system.service.ISysConfigService;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Parameter configuration Information operation processing
 *
 * @author HoaLD
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/config")
public class SysConfigController extends BaseController {

    private final ISysConfigService configService;

    /**
     * Get parameter configuration list
     */
    @SaCheckPermission("system:config:list")
    @GetMapping("/list")
    public TableDataInfo<SysConfigVo> list(SysConfigBo config, PageQuery pageQuery) {
        return configService.selectPageConfigList(config, pageQuery);
    }

    /**
     * Export parameter configuration list
     */
    @Log(title = "Parameter management", businessType = BusinessType.EXPORT)
    @SaCheckPermission("system:config:export")
    @PostMapping("/export")
    public void export(SysConfigBo config, HttpServletResponse response) {
        List<SysConfigVo> list = configService.selectConfigList(config);
        ExcelUtil.exportExcel(list, "parameter_data", SysConfigVo.class, response);
    }

    /**
     * Get detailed information by parameter number
     *
     * @param configId parameter ID
     */
    @SaCheckPermission("system:config:query")
    @GetMapping(value = "/{configId}")
    public R<SysConfigVo> getInfo(@PathVariable Long configId) {
        return R.ok(configService.selectConfigById(configId));
    }

    /**
     * Query the parameter value according to the parameter key name
     *
     * @param configKey ParameterKey
     */
    @GetMapping(value = "/configKey/{configKey}")
    public R<Void> getConfigKey(@PathVariable String configKey) {
        return R.ok(configService.selectConfigByKey(configKey));
    }

    /**
     * New parameter configuration
     */
    @SaCheckPermission("system:config:add")
    @Log(title = "Parameter management", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Void> add(@Validated @RequestBody SysConfigBo config) {
        if (!configService.checkConfigKeyUnique(config)) {
            return R.fail("Add parameter '" + config.getConfigName() + "' failed, the parameter key already exists");
        }
        configService.insertConfig(config);
        return R.ok();
    }

    /**
     * Edit parameter configuration
     */
    @SaCheckPermission("system:config:edit")
    @Log(title = "Parameter management", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Void> edit(@Validated @RequestBody SysConfigBo config) {
        if (!configService.checkConfigKeyUnique(config)) {
            return R.fail("Edit parameter '" + config.getConfigName() + "' failed, the parameter key already exists");
        }
        configService.updateConfig(config);
        return R.ok();
    }

    /**
     * Modify the parameter configuration according to the parameter key name
     */
    @SaCheckPermission("system:config:edit")
    @Log(title = "Parameter management", businessType = BusinessType.UPDATE)
    @PutMapping("/updateByKey")
    public R<Void> updateByKey(@RequestBody SysConfigBo config) {
        configService.updateConfig(config);
        return R.ok();
    }

    /**
     * Delete parameter configuration
     *
     * @param configIds parameter ID string
     */
    @SaCheckPermission("system:config:remove")
    @Log(title = "Parameter management", businessType = BusinessType.DELETE)
    @DeleteMapping("/{configIds}")
    public R<Void> remove(@PathVariable Long[] configIds) {
        configService.deleteConfigByIds(configIds);
        return R.ok();
    }

    /**
     * Refresh parameter cache
     */
    @SaCheckPermission("system:config:remove")
    @Log(title = "Parameter management", businessType = BusinessType.CLEAN)
    @DeleteMapping("/refreshCache")
    public R<Void> refreshCache() {
        configService.resetConfigCache();
        return R.ok();
    }
}
