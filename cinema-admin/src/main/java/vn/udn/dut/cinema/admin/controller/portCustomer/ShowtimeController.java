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
import vn.udn.dut.cinema.port.domain.bo.ShowtimeBo;
import vn.udn.dut.cinema.port.domain.vo.ShowtimeVo;
import vn.udn.dut.cinema.port.service.IShowtimeService;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/portCustomer/showtimeManagement")
public class ShowtimeController extends BaseController{
	private final IShowtimeService showtimeService;
	
	@SaCheckPermission("portCustomer:showtime:list")
	@GetMapping("/list")
	public TableDataInfo<ShowtimeVo> list(ShowtimeBo bo, PageQuery pageQuery) {
		return showtimeService.queryPageList(bo, pageQuery);
	}
	
	@SaCheckPermission("portCustomer:showtime:query")
	@GetMapping(value = { "/", "/{id}" })
	public R<ShowtimeVo> getInfo(@PathVariable(value = "id", required = false) Long id) {
		return R.ok(showtimeService.queryById(id));
	}
	
	@SaCheckPermission("portCustomer:showtime:add")
	@Log(title = "Showtime", businessType = BusinessType.INSERT)
	@RepeatSubmit()
	@PostMapping()
	public R<Void> add(@Validated(AddGroup.class) @RequestBody ShowtimeBo bo) {
		return toAjax(showtimeService.insertByBo(bo));
	}
	
	@SaCheckPermission("portCustomer:showtime:edit")
	@Log(title = "Showtime", businessType = BusinessType.UPDATE)
	@RepeatSubmit()
	@PutMapping()
	public R<Void> edit(@Validated(EditGroup.class) @RequestBody ShowtimeBo bo) {
		return toAjax(showtimeService.updateByBo(bo));
	}
	
	@SaCheckPermission("portCustomer:showtime:remove")
	@Log(title = "Showtime", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public R<Void> remove(@NotEmpty(message = "Primary key cannot be empty") @PathVariable Long[] ids) {
		return toAjax(showtimeService.deleteWithValidByIds(List.of(ids), true));
	}
	
	@SaCheckPermission("portCustomer:showtime:release")
	@Log(title = "Seat table", businessType = BusinessType.INSERT)
	@RepeatSubmit()
	@PostMapping("/release")
	public R<Void> releaseShowtime(@RequestBody List<ShowtimeBo> showtimes) {
		showtimeService.releaseShowtimes(showtimes);
		return R.ok();
	}
}
