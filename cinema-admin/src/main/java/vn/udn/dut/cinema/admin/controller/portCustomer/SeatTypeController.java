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
import vn.udn.dut.cinema.port.domain.bo.SeatTypeBo;
import vn.udn.dut.cinema.port.domain.vo.SeatTypeVo;
import vn.udn.dut.cinema.port.service.ISeatTypeService;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/portCustomer/seatType")
public class SeatTypeController extends BaseController{
	private final ISeatTypeService seatTypeService;
	
	@SaCheckPermission("portCustomer:seatType:list")
	@GetMapping("/list")
	public TableDataInfo<SeatTypeVo> list(SeatTypeBo bo, PageQuery pageQuery) {
		return seatTypeService.queryPageList(bo, pageQuery);
	}
	
	@SaCheckPermission("portCustomer:seatType:query")
	@GetMapping(value = { "/", "/{id}" })
	public R<SeatTypeVo> getInfo(@PathVariable(value = "id", required = false) Long id) {
		return R.ok(seatTypeService.queryById(id));
	}
	
	@SaCheckPermission("portCustomer:seatType:add")
	@Log(title = "Seat type", businessType = BusinessType.INSERT)
	@RepeatSubmit()
	@PostMapping()
	public R<Void> add(@Validated(AddGroup.class) @RequestBody SeatTypeBo bo) {
		return toAjax(seatTypeService.insertByBo(bo));
	}
	
	@SaCheckPermission("portCustomer:seatType:edit")
	@Log(title = "Seat type", businessType = BusinessType.UPDATE)
	@RepeatSubmit()
	@PutMapping()
	public R<Void> edit(@Validated(EditGroup.class) @RequestBody SeatTypeBo bo) {
		return toAjax(seatTypeService.updateByBo(bo));
	}
	
	@SaCheckPermission("portCustomer:seatType:remove")
	@Log(title = "Seat type", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public R<Void> remove(@NotEmpty(message = "Primary key cannot be empty") @PathVariable Long[] ids) {
		return toAjax(seatTypeService.deleteWithValidByIds(List.of(ids), true));
	}
}
