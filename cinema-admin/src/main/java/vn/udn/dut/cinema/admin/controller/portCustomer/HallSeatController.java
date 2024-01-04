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
import vn.udn.dut.cinema.port.domain.bo.HallSeatBo;
import vn.udn.dut.cinema.port.domain.vo.HallSeatVo;
import vn.udn.dut.cinema.port.domain.vo.HallVo;
import vn.udn.dut.cinema.port.service.IHallSeatService;
import vn.udn.dut.cinema.port.service.IHallService;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/portCustomer/hallSeat")
public class HallSeatController extends BaseController {
	
	private final IHallSeatService hallSeatService;
	
	private final IHallService hallService;

	@SaCheckPermission("portCustomer:hallSeat:list")
	@GetMapping("/list")
	public TableDataInfo<HallSeatVo> list(HallSeatBo bo, PageQuery pageQuery) {
		return hallSeatService.queryPageList(bo, pageQuery);
	}

	@SaCheckPermission("portCustomer:hallSeat:query")
	@GetMapping(value = { "/", "/{id}" })
	public R<HallSeatVo> getInfo(@PathVariable(value = "id", required = false) Long id) {
		return R.ok(hallSeatService.queryById(id));
	}

	@SaCheckPermission("portCustomer:hallSeat:add")
	@Log(title = "HallSeat", businessType = BusinessType.INSERT)
	@RepeatSubmit()
	@PostMapping()
	public R<Void> add(@Validated(AddGroup.class) @RequestBody HallSeatBo bo) {
		return toAjax(hallSeatService.insertByBo(bo));
	}

	@SaCheckPermission("portCustomer:hallSeat:edit")
	@Log(title = "HallSeat", businessType = BusinessType.UPDATE)
	@RepeatSubmit()
	@PutMapping()
	public R<Void> edit(@Validated(EditGroup.class) @RequestBody HallSeatBo bo) {
		return toAjax(hallSeatService.updateByBo(bo));
	}

	@SaCheckPermission("portCustomer:hallSeat:remove")
	@Log(title = "HallSeat", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public R<Void> remove(@NotEmpty(message = "Primary key cannot be empty") @PathVariable Long[] ids) {
		return toAjax(hallSeatService.deleteWithValidByIds(List.of(ids), true));
	}
	
	/**
	 * Save hall seat information
	 */
	@SaCheckPermission("portCustomer:hallSeat:edit")
	@Log(title = "Insert HallSeat list", businessType = BusinessType.UPDATE)
	@RepeatSubmit()
	@PutMapping("/hall/{hallId}")
	public R<Void> updateHallSeatList(@PathVariable Long hallId, @RequestBody List<HallSeatBo> hallSeats) {
		HallVo hall = hallService.queryById(hallId);
		return toAjax(hallSeatService.updateHallSeatListBo(hallSeats, hall));
	}
}
