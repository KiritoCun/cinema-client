package vn.udn.dut.cinema.admin.controller.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import vn.udn.dut.cinema.common.core.domain.R;
import vn.udn.dut.cinema.common.excel.utils.ExcelUtil;
import vn.udn.dut.cinema.common.log.annotation.Log;
import vn.udn.dut.cinema.common.log.enums.BusinessType;
import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.common.web.core.BaseController;
import vn.udn.dut.cinema.system.domain.bo.SysPostBo;
import vn.udn.dut.cinema.system.domain.vo.SysPostVo;
import vn.udn.dut.cinema.system.service.ISysPostService;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Job Information Operation and Processing
 *
 * @author HoaLD
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/post")
public class SysPostController extends BaseController {

    private final ISysPostService postService;

    /**
     * Get job list
     */
    @SaCheckPermission("system:post:list")
    @GetMapping("/list")
    public TableDataInfo<SysPostVo> list(SysPostBo post, PageQuery pageQuery) {
        return postService.selectPagePostList(post, pageQuery);
    }

    /**
     * Export job list
     */
    @Log(title = "Position management", businessType = BusinessType.EXPORT)
    @SaCheckPermission("system:post:export")
    @PostMapping("/export")
    public void export(SysPostBo post, HttpServletResponse response) {
        List<SysPostVo> list = postService.selectPostList(post);
        ExcelUtil.exportExcel(list, "job_data", SysPostVo.class, response);
    }

    /**
     * Get detailed information by job number
     *
     * @param postId Job ID
     */
    @SaCheckPermission("system:post:query")
    @GetMapping(value = "/{postId}")
    public R<SysPostVo> getInfo(@PathVariable Long postId) {
        return R.ok(postService.selectPostById(postId));
    }

    /**
     * new jobs
     */
    @SaCheckPermission("system:post:add")
    @Log(title = "Position management", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Void> add(@Validated @RequestBody SysPostBo post) {
        if (!postService.checkPostNameUnique(post)) {
            return R.fail("Add position '" + post.getPostName() + "' failed, the position name already exists");
        } else if (!postService.checkPostCodeUnique(post)) {
            return R.fail("Add position '" + post.getPostName() + "' failed, the position code already exists");
        }
        return toAjax(postService.insertPost(post));
    }

    /**
     * modify position
     */
    @SaCheckPermission("system:post:edit")
    @Log(title = "Position management", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Void> edit(@Validated @RequestBody SysPostBo post) {
        if (!postService.checkPostNameUnique(post)) {
            return R.fail("Edit position '" + post.getPostName() + "' failed, the position name already exists");
        } else if (!postService.checkPostCodeUnique(post)) {
            return R.fail("Edit position '" + post.getPostName() + "' failed, the position code already exists");
        }
        return toAjax(postService.updatePost(post));
    }

    /**
     * delete post
     *
     * @param postIds Post ID string
     */
    @SaCheckPermission("system:post:remove")
    @Log(title = "Position management", businessType = BusinessType.DELETE)
    @DeleteMapping("/{postIds}")
    public R<Void> remove(@PathVariable Long[] postIds) {
        return toAjax(postService.deletePostByIds(postIds));
    }

    /**
     * Get the job selection box list
     */
    @GetMapping("/optionselect")
    public R<List<SysPostVo>> optionselect() {
        List<SysPostVo> posts = postService.selectPostAll();
        return R.ok(posts);
    }
}
