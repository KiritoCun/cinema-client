package vn.udn.dut.cinema.admin.controller.portCustomer;

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
import vn.udn.dut.cinema.port.domain.bo.PromotionBo;
import vn.udn.dut.cinema.port.domain.vo.PromotionVo;
import vn.udn.dut.cinema.port.service.IPromotionService;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/portCustomer/promotion")
public class PromotionController extends BaseController{
	private final IPromotionService promotionService;
	
	@SaCheckPermission("portCustomer:promotion:list")
	@GetMapping("/list")
	public TableDataInfo<PromotionVo> list(PromotionBo bo, PageQuery pageQuery) {
		return promotionService.queryPageList(bo, pageQuery);
	}
	
	@SaCheckPermission("portCustomer:promotion:query")
	@GetMapping(value = { "/", "/{id}" })
	public R<PromotionVo> getInfo(@PathVariable(value = "id", required = false) Long id) {
		return R.ok(promotionService.queryById(id));
	}
	
	@SaCheckPermission("portCustomer:promotion:add")
	@Log(title = "Promotion", businessType = BusinessType.INSERT)
	@RepeatSubmit()
	@PostMapping()
	public R<Void> add(@Validated(AddGroup.class) @RequestBody PromotionBo bo) {
		return toAjax(promotionService.insertByBo(bo));
	}
	
	@SaCheckPermission("portCustomer:promotion:edit")
	@Log(title = "Promotion", businessType = BusinessType.UPDATE)
	@RepeatSubmit()
	@PutMapping()
	public R<Void> edit(@Validated(EditGroup.class) @RequestBody PromotionBo bo) {
		return toAjax(promotionService.updateByBo(bo));
	}
	
	@SaCheckPermission("portCustomer:promotion:remove")
	@Log(title = "Promotion", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public R<Void> remove(@NotEmpty(message = "Primary key cannot be empty") @PathVariable Long[] ids) {
		return toAjax(promotionService.deleteWithValidByIds(List.of(ids), true));
	}
}
