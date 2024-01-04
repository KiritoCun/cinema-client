package vn.udn.dut.cinema.admin.controller.customer;

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
import vn.udn.dut.cinema.port.domain.bo.CustomerBo;
import vn.udn.dut.cinema.port.domain.vo.CustomerVo;
import vn.udn.dut.cinema.port.service.ICustomerService;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/customer/account")

public class AccountController extends BaseController{
	private final ICustomerService customerService;
	
	@SaCheckPermission("customer:account:list")
	@GetMapping("/list")
	public TableDataInfo<CustomerVo> list(CustomerBo bo, PageQuery pageQuery) {
		return customerService.queryPageList(bo, pageQuery);
	}
	
	@SaCheckPermission("customer:account:query")
	@GetMapping(value = { "/", "/{id}" })
	public R<CustomerVo> getInfo(@PathVariable(value = "id", required = false) Long id) {
		return R.ok(customerService.queryById(id));
	}
	
	@SaCheckPermission("customer:account:add")
	@Log(title = "Customer", businessType = BusinessType.INSERT)
	@RepeatSubmit()
	@PostMapping()
	public R<Void> add(@Validated(AddGroup.class) @RequestBody CustomerBo bo) {
		return toAjax(customerService.insertByBo(bo));
	}
	
	@SaCheckPermission("customer:account:edit")
	@Log(title = "Customer", businessType = BusinessType.UPDATE)
	@RepeatSubmit()
	@PutMapping()
	public R<Void> edit(@Validated(EditGroup.class) @RequestBody CustomerBo bo) {
		return toAjax(customerService.updateByBo(bo));
	}
	
	@SaCheckPermission("customer:account:remove")
	@Log(title = "Customer", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public R<Void> remove(@NotEmpty(message = "Primary key cannot be empty") @PathVariable Long[] ids) {
		return toAjax(customerService.deleteWithValidByIds(List.of(ids), true));
	}
}
