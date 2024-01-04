package vn.udn.dut.cinema.admin.controller.portCustomer;

import java.util.List;
import java.util.Map;

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
import vn.udn.dut.cinema.port.domain.bo.HallBo;
import vn.udn.dut.cinema.port.domain.vo.HallVo;
import vn.udn.dut.cinema.port.service.IHallService;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/portCustomer/hall")
public class HallController extends BaseController{
	private final IHallService hallService;
	
	@SaCheckPermission("portCustomer:hall:list")
	@GetMapping("/list")
	public TableDataInfo<HallVo> list(HallBo bo, PageQuery pageQuery) {
		return hallService.queryPageList(bo, pageQuery);
	}
	
	@SaCheckPermission("portCustomer:hall:query")
	@GetMapping(value = { "/", "/{id}" })
	public R<HallVo> getInfo(@PathVariable(value = "id", required = false) Long id) {
		return R.ok(hallService.queryById(id));
	}
	
	@SaCheckPermission("portCustomer:hall:add")
	@Log(title = "Hall", businessType = BusinessType.INSERT)
	@RepeatSubmit()
	@PostMapping()
	public R<Void> add(@Validated(AddGroup.class) @RequestBody HallBo bo) {
		return toAjax(hallService.insertByBo(bo));
	}
	
	@SaCheckPermission("portCustomer:hall:edit")
	@Log(title = "Hall", businessType = BusinessType.UPDATE)
	@RepeatSubmit()
	@PutMapping()
	public R<Void> edit(@Validated(EditGroup.class) @RequestBody HallBo bo) {
		return toAjax(hallService.updateByBo(bo));
	}
	
	@SaCheckPermission("portCustomer:hall:remove")
	@Log(title = "Hall", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public R<Void> remove(@NotEmpty(message = "Primary key cannot be empty") @PathVariable Long[] ids) {
		return toAjax(hallService.deleteWithValidByIds(List.of(ids), true));
	}
	
	@GetMapping("/map")
	public R<Map<Long, List<HallVo>>> getHallMap() {
		return R.ok(hallService.getHallMap());
	}
}
