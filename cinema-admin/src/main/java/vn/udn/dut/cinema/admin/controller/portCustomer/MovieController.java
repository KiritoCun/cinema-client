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
import vn.udn.dut.cinema.port.domain.bo.MovieBo;
import vn.udn.dut.cinema.port.domain.vo.MovieVo;
import vn.udn.dut.cinema.port.service.IMovieService;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/portCustomer/movie")
public class MovieController extends BaseController {
	private final IMovieService movieService;
	
	@SaCheckPermission("portCustomer:movie:list")
	@GetMapping("/list")
	public TableDataInfo<MovieVo> list(MovieBo bo, PageQuery pageQuery) {
		return movieService.queryPageList(bo, pageQuery);
	}
	
	@SaCheckPermission("portCustomer:movie:query")
	@GetMapping(value = { "/", "/{id}" })
	public R<MovieVo> getInfo(@PathVariable(value = "id", required = false) Long id) {
		return R.ok(movieService.queryById(id));
	}
	
	@SaCheckPermission("portCustomer:movie:add")
	@Log(title = "Movie", businessType = BusinessType.INSERT)
	@RepeatSubmit()
	@PostMapping()
	public R<Void> add(@Validated(AddGroup.class) @RequestBody MovieBo bo) {
		return toAjax(movieService.insertByBo(bo));
	}
	
	@SaCheckPermission("portCustomer:movie:edit")
	@Log(title = "Movie", businessType = BusinessType.UPDATE)
	@RepeatSubmit()
	@PutMapping()
	public R<Void> edit(@Validated(EditGroup.class) @RequestBody MovieBo bo) {
		return toAjax(movieService.updateByBo(bo));
	}
	
	@SaCheckPermission("portCustomer:movie:remove")
	@Log(title = "Movie", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public R<Void> remove(@NotEmpty(message = "Primary key cannot be empty") @PathVariable Long[] ids) {
		return toAjax(movieService.deleteWithValidByIds(List.of(ids), true));
	}
}
