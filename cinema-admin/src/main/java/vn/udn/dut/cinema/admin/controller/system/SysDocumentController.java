package vn.udn.dut.cinema.admin.controller.system;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import vn.udn.dut.cinema.common.core.domain.R;
import vn.udn.dut.cinema.common.core.validate.AddGroup;
import vn.udn.dut.cinema.common.core.validate.EditGroup;
import vn.udn.dut.cinema.common.idempotent.annotation.RepeatSubmit;
import vn.udn.dut.cinema.common.log.annotation.Log;
import vn.udn.dut.cinema.common.log.enums.BusinessType;
import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.common.web.core.BaseController;
import vn.udn.dut.cinema.system.domain.bo.SysDocumentBo;
import vn.udn.dut.cinema.system.domain.vo.SysDocumentVo;
import vn.udn.dut.cinema.system.service.ISysDocumentService;

/**
 * System document
 *
 * @author HoaLD
 * @date 2023-08-23
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/document")
public class SysDocumentController extends BaseController {

    private final ISysDocumentService sysDocumentService;

    /**
     * Query System document list
     */
    @SaCheckPermission("system:document:list")
    @GetMapping("/list")
    public TableDataInfo<SysDocumentVo> list(SysDocumentBo bo, PageQuery pageQuery) {
        return sysDocumentService.queryPageList(bo, pageQuery);
    }

    /**
     * Get System document detail
     *
     * @param id primary key
     */
    @SaCheckPermission("system:document:query")
    @GetMapping("/{id}")
    public R<SysDocumentVo> getInfo(@NotNull(message = "Primary key cannot be empty")
                                     @PathVariable Long id) {
        return R.ok(sysDocumentService.queryById(id));
    }

    /**
     * Add System document
     */
    @SaCheckPermission("system:document:add")
    @Log(title = "System document", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody SysDocumentBo bo) {
        return toAjax(sysDocumentService.insertByBo(bo));
    }

    /**
     * Edit System document
     */
    @SaCheckPermission("system:document:edit")
    @Log(title = "System document", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody SysDocumentBo bo) {
        return toAjax(sysDocumentService.updateByBo(bo));
    }

    /**
     * Delete System document
     *
     * @param ids primary key string
     */
    @SaCheckPermission("system:document:remove")
    @Log(title = "System document", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "Primary key cannot be empty")
                          @PathVariable Long[] ids) {
        return toAjax(sysDocumentService.deleteWithValidByIds(List.of(ids), true));
    }
    
    /**
     * status modification
     */
    @SaCheckPermission("system:document:edit")
    @Log(title = "System document", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public R<Void> changeStatus(@RequestBody SysDocumentBo document) {
        return toAjax(sysDocumentService.updateDocumentStatus(document.getId(), document.getStatus()));
    }
}
