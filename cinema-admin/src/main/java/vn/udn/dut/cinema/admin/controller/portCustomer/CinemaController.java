package vn.udn.dut.cinema.admin.controller.portCustomer;

import java.util.ArrayList;
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
import cn.hutool.core.util.ObjectUtil;
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
import vn.udn.dut.cinema.port.domain.bo.CinemaBo;
import vn.udn.dut.cinema.port.domain.vo.CinemaVo;
import vn.udn.dut.cinema.port.service.ICinemaService;

/**
 * Cinema api
 *
 * @author HoaLD
 * @date 2023-12-02
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/portCustomer/cinema")
public class CinemaController extends BaseController {

	private final ICinemaService cinemaService;
	
	@GetMapping(value = { "support/", "support/{cinemaId}" })
	public R<List<CinemaVo>> getCinemaInfo(@PathVariable(value = "userId", required = false) Long cinemaId) {
		if (ObjectUtil.isNotNull(cinemaId)) {
			List<CinemaVo> cinemas = new ArrayList<>();
			cinemas.add(cinemaService.queryById(cinemaId));
			return R.ok(cinemas);
		}
		return R.ok(cinemaService.queryList(new CinemaBo()));
	}

	/**
	 * Query Cinema list
	 */
	@SaCheckPermission("portCustomer:cinema:list")
	@GetMapping("/list")
	public TableDataInfo<CinemaVo> list(CinemaBo bo, PageQuery pageQuery) {
		return cinemaService.queryPageList(bo, pageQuery);
	}

	/**
	 * Get Cinema
	 *
	 * @param id primary key
	 */
	@SaCheckPermission("portCustomer:cinema:query")
	@GetMapping(value = { "/", "/{id}" })
	public R<CinemaVo> getInfo(@PathVariable(value = "id", required = false) Long id) {
		return R.ok(cinemaService.queryById(id));
	}

	/**
	 * Add Cinema
	 */
	@SaCheckPermission("portCustomer:cinema:add")
	@Log(title = "Cinema", businessType = BusinessType.INSERT)
	@RepeatSubmit()
	@PostMapping()
	public R<Void> add(@Validated(AddGroup.class) @RequestBody CinemaBo bo) {
		return toAjax(cinemaService.insertByBo(bo));
	}

	/**
	 * Edit Cinema
	 */
	@SaCheckPermission("portCustomer:cinema:edit")
	@Log(title = "Cinema", businessType = BusinessType.UPDATE)
	@RepeatSubmit()
	@PutMapping()
	public R<Void> edit(@Validated(EditGroup.class) @RequestBody CinemaBo bo) {
		return toAjax(cinemaService.updateByBo(bo));
	}

	/**
	 * Delete Cinema
	 *
	 * @param ids primary key string
	 */
	@SaCheckPermission("portCustomer:cinema:remove")
	@Log(title = "Cinema", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public R<Void> remove(@NotEmpty(message = "Primary key cannot be empty") @PathVariable Long[] ids) {
		return toAjax(cinemaService.deleteWithValidByIds(List.of(ids), true));
	}
}
