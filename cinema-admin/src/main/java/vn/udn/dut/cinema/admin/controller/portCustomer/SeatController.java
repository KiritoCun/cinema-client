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
import vn.udn.dut.cinema.port.domain.bo.SeatBo;
import vn.udn.dut.cinema.port.domain.vo.SeatVo;
import vn.udn.dut.cinema.port.service.ISeatService;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/portCustomer/seat")
public class SeatController extends BaseController{
	private final ISeatService seatService;
	
	@SaCheckPermission("portCustomer:seat:list")
	@GetMapping("/list")
	public TableDataInfo<SeatVo> list(SeatBo bo, PageQuery pageQuery) {
		return seatService.queryPageList(bo, pageQuery);
	}
	
	@SaCheckPermission("portCustomer:seat:query")
	@GetMapping(value = { "/", "/{id}" })
	public R<SeatVo> getInfo(@PathVariable(value = "id", required = false) Long id) {
		return R.ok(seatService.queryById(id));
	}
	
	@SaCheckPermission("portCustomer:seat:add")
	@Log(title = "Seat", businessType = BusinessType.INSERT)
	@RepeatSubmit()
	@PostMapping()
	public R<Void> add(@Validated(AddGroup.class) @RequestBody SeatBo bo) {
		return toAjax(seatService.insertByBo(bo));
	}
	
	@SaCheckPermission("portCustomer:seat:edit")
	@Log(title = "Seat", businessType = BusinessType.UPDATE)
	@RepeatSubmit()
	@PutMapping()
	public R<Void> edit(@Validated(EditGroup.class) @RequestBody SeatBo bo) {
		return toAjax(seatService.updateByBo(bo));
	}
	
	@SaCheckPermission("portCustomer:seat:remove")
	@Log(title = "Seat", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public R<Void> remove(@NotEmpty(message = "Primary key cannot be empty") @PathVariable Long[] ids) {
		return toAjax(seatService.deleteWithValidByIds(List.of(ids), true));
	}

}
