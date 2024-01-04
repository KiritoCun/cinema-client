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
import vn.udn.dut.cinema.port.domain.bo.SlideBo;
import vn.udn.dut.cinema.port.domain.vo.SlideVo;
import vn.udn.dut.cinema.port.service.ISlideService;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/portCustomer/slide")
public class SlideController extends BaseController{
	private final ISlideService slideService;
	
	@SaCheckPermission("portCustomer:slide:list")
	@GetMapping("/list")
	public TableDataInfo<SlideVo> list(SlideBo bo, PageQuery pageQuery) {
		return slideService.queryPageList(bo, pageQuery);
	}
	
	@SaCheckPermission("portCustomer:slide:query")
	@GetMapping(value = { "/", "/{id}" })
	public R<SlideVo> getInfo(@PathVariable(value = "id", required = false) Long id) {
		return R.ok(slideService.queryById(id));
	}
	
	@SaCheckPermission("portCustomer:slide:add")
	@Log(title = "Slide", businessType = BusinessType.INSERT)
	@RepeatSubmit()
	@PostMapping()
	public R<Void> add(@Validated(AddGroup.class) @RequestBody SlideBo bo) {
		return toAjax(slideService.insertByBo(bo));
	}
	
	@SaCheckPermission("portCustomer:slide:edit")
	@Log(title = "Slide", businessType = BusinessType.UPDATE)
	@RepeatSubmit()
	@PutMapping()
	public R<Void> edit(@Validated(EditGroup.class) @RequestBody SlideBo bo) {
		return toAjax(slideService.updateByBo(bo));
	}
	
	@SaCheckPermission("portCustomer:slide:remove")
	@Log(title = "Slide", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public R<Void> remove(@NotEmpty(message = "Primary key cannot be empty") @PathVariable Long[] ids) {
		return toAjax(slideService.deleteWithValidByIds(List.of(ids), true));
	}
}
