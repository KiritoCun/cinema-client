package vn.udn.dut.cinema.admin.controller.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import vn.udn.dut.cinema.common.core.domain.R;
import vn.udn.dut.cinema.common.excel.utils.ExcelUtil;
import vn.udn.dut.cinema.common.log.annotation.Log;
import vn.udn.dut.cinema.common.log.enums.BusinessType;
import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.common.web.core.BaseController;
import vn.udn.dut.cinema.system.domain.bo.SysDictDataBo;
import vn.udn.dut.cinema.system.domain.vo.SysDictDataVo;
import vn.udn.dut.cinema.system.service.ISysDictDataService;
import vn.udn.dut.cinema.system.service.ISysDictTypeService;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Dictionary Information
 *
 * @author HoaLD
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/dict/data")
public class SysDictDataController extends BaseController {

    private final ISysDictDataService dictDataService;
    private final ISysDictTypeService dictTypeService;

    /**
     * Query dictionary data list
     */
    @SaCheckPermission("system:dict:list")
    @GetMapping("/list")
    public TableDataInfo<SysDictDataVo> list(SysDictDataBo dictData, PageQuery pageQuery) {
        return dictDataService.selectPageDictDataList(dictData, pageQuery);
    }

    /**
     * Export list of dictionary data
     */
    @Log(title = "Dictionary data", businessType = BusinessType.EXPORT)
    @SaCheckPermission("system:dict:export")
    @PostMapping("/export")
    public void export(SysDictDataBo dictData, HttpServletResponse response) {
        List<SysDictDataVo> list = dictDataService.selectDictDataList(dictData);
        ExcelUtil.exportExcel(list, "dictionary_data", SysDictDataVo.class, response);
    }

    /**
     * Query dictionary data details
     *
     * @param dictCode dictionary code
     */
    @SaCheckPermission("system:dict:query")
    @GetMapping(value = "/{dictCode}")
    public R<SysDictDataVo> getInfo(@PathVariable Long dictCode) {
        return R.ok(dictDataService.selectDictDataById(dictCode));
    }

    /**
     * Query dictionary data information according to dictionary type
     *
     * @param dictType dictionary type
     */
    @GetMapping(value = "/type/{dictType}")
    public R<List<SysDictDataVo>> dictType(@PathVariable String dictType) {
        List<SysDictDataVo> data = dictTypeService.selectDictDataByType(dictType);
        if (ObjectUtil.isNull(data)) {
            data = new ArrayList<>();
        }
        return R.ok(data);
    }

    /**
     * New dictionary type
     */
    @SaCheckPermission("system:dict:add")
    @Log(title = "Dictionary data", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Void> add(@Validated @RequestBody SysDictDataBo dict) {
        dictDataService.insertDictData(dict);
        return R.ok();
    }

    /**
     * Modify save dictionary type
     */
    @SaCheckPermission("system:dict:edit")
    @Log(title = "Dictionary data", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Void> edit(@Validated @RequestBody SysDictDataBo dict) {
        dictDataService.updateDictData(dict);
        return R.ok();
    }

    /**
     * Delete dictionary type
     *
     * @param dictCodes Dictionary code string
     */
    @SaCheckPermission("system:dict:remove")
    @Log(title = "Dictionary type", businessType = BusinessType.DELETE)
    @DeleteMapping("/{dictCodes}")
    public R<Void> remove(@PathVariable Long[] dictCodes) {
        dictDataService.deleteDictDataByIds(dictCodes);
        return R.ok();
    }
}
