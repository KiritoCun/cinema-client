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
import vn.udn.dut.cinema.port.domain.bo.BookingBo;
import vn.udn.dut.cinema.port.domain.vo.BookingVo;
import vn.udn.dut.cinema.port.service.IBookingService;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("portCustomer/booking")
public class BookingController extends BaseController{
	private final IBookingService bookingService;
	
	@SaCheckPermission("portCustomer:booking:list")
	@GetMapping("/list")
	public TableDataInfo<BookingVo> list(BookingBo bo, PageQuery pageQuery) {
		return bookingService.queryPageList(bo, pageQuery);
	}
	
	@SaCheckPermission("portCustomer:booking:query")
	@GetMapping(value = { "/", "/{id}" })
	public R<BookingVo> getInfo(@PathVariable(value = "id", required = false) Long id) {
		return R.ok(bookingService.queryById(id));
	}
	
	@SaCheckPermission("portCustomer:booking:add")
	@Log(title = "Booking", businessType = BusinessType.INSERT)
	@RepeatSubmit()
	@PostMapping()
	public R<Void> add(@Validated(AddGroup.class) @RequestBody BookingBo bo) {
		return toAjax(bookingService.insertByBo(bo));
	}
	
	@SaCheckPermission("portCustomer:booking:edit")
	@Log(title = "Booking", businessType = BusinessType.UPDATE)
	@RepeatSubmit()
	@PutMapping()
	public R<Void> edit(@Validated(EditGroup.class) @RequestBody BookingBo bo) {
		return toAjax(bookingService.updateByBo(bo));
	}
	
	@SaCheckPermission("portCustomer:booking:remove")
	@Log(title = "Booking", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public R<Void> remove(@NotEmpty(message = "Primary key cannot be empty") @PathVariable Long[] ids) {
		return toAjax(bookingService.deleteWithValidByIds(List.of(ids), true));
	}
}
