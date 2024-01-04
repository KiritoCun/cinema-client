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
import vn.udn.dut.cinema.port.domain.bo.BookingDetailBo;
import vn.udn.dut.cinema.port.domain.vo.BookingDetailVo;
import vn.udn.dut.cinema.port.service.IBookingDetailService;
import vn.udn.dut.cinema.port.service.IBookingService;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/portCustomer/bookingDetail")
public class BookingDetailController extends BaseController{
	private final IBookingDetailService bookingDetailService;
	
	private final IBookingService bookingService;
	
	@SaCheckPermission("portCustomer:bookingDetail:list")
	@GetMapping("/list")
	public TableDataInfo<BookingDetailVo> list(BookingDetailBo bo, PageQuery pageQuery) {
		return bookingDetailService.queryPageList(bo, pageQuery);
	}

	@SaCheckPermission("portCustomer:bookingDetail:query")
	@GetMapping(value = { "/", "/{id}" })
	public R<BookingDetailVo> getInfo(@PathVariable(value = "id", required = false) Long id) {
		return R.ok(bookingDetailService.queryById(id));
	}
	
	@SaCheckPermission("portCustomer:bookingDetail:add")
	@Log(title = "BookingDetail", businessType = BusinessType.INSERT)
	@RepeatSubmit()
	@PostMapping()
	public R<Void> add(@Validated(AddGroup.class) @RequestBody BookingDetailBo bo) {
		return toAjax(bookingDetailService.insertByBo(bo));
	}

	@SaCheckPermission("portCustomer:bookingDetail:edit")
	@Log(title = "BookingDetail", businessType = BusinessType.UPDATE)
	@RepeatSubmit()
	@PutMapping()
	public R<Void> edit(@Validated(EditGroup.class) @RequestBody BookingDetailBo bo) {
		return toAjax(bookingDetailService.updateByBo(bo));
	}

	@SaCheckPermission("portCustomer:bookingDetail:remove")
	@Log(title = "BookingDetail", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public R<Void> remove(@NotEmpty(message = "Primary key cannot be empty") @PathVariable Long[] ids) {
		return toAjax(bookingDetailService.deleteWithValidByIds(List.of(ids), true));
	}
}
