package vn.udn.dut.cinema.customer.controller;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.hutool.core.util.ObjectUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import vn.udn.dut.cinema.common.core.domain.R;
import vn.udn.dut.cinema.common.core.validate.QueryGroup;
import vn.udn.dut.cinema.common.log.annotation.Log;
import vn.udn.dut.cinema.common.log.enums.BusinessType;
import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.common.web.core.BaseController;
import vn.udn.dut.cinema.system.domain.bo.SysOssBo;
import vn.udn.dut.cinema.system.domain.vo.SysOssUploadVo;
import vn.udn.dut.cinema.system.domain.vo.SysOssVo;
import vn.udn.dut.cinema.system.service.ISysOssService;

/**
 * File upload control layer
 *
 * @author HoaLD
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/resource/oss")
public class SysOssController extends BaseController {

    private final ISysOssService ossService;

    /**
     * Query the list of OSS object storage
     */
//    @SaCheckPermission("system:oss:list")
    @GetMapping("/list")
    public TableDataInfo<SysOssVo> list(@Validated(QueryGroup.class) SysOssBo bo, PageQuery pageQuery) {
        return ossService.queryPageList(bo, pageQuery);
    }

    /**
     * Query OSS objects based on id string
     *
     * @param ossIds OSS object ID string
     */
//    @SaCheckPermission("system:oss:list")
    @GetMapping("/listByIds/{ossIds}")
    public R<List<SysOssVo>> listByIds(@NotEmpty(message = "Primary key cannot be empty")
                                       @PathVariable Long[] ossIds) {
        List<SysOssVo> list = ossService.listByIds(Arrays.asList(ossIds));
        return R.ok(list);
    }

	/**
	 * Upload OSS object storage
	 *
	 * @param file document
	 */
//	@SaCheckPermission("system:oss:upload")
	@Log(title = "OSS object storage", businessType = BusinessType.INSERT)
	@PostMapping(value = { "/upload", "/upload/{configKey}" }, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public R<SysOssUploadVo> upload(@RequestPart("file") MultipartFile file,
			@PathVariable(value = "configKey", required = false) String configKey) {
		if (ObjectUtil.isNull(file)) {
			return R.fail("Upload file cannot be empty");
		}
		SysOssVo oss = null;
		if (configKey == null) {
			oss = ossService.upload(file);
		} else {
			oss = ossService.upload(file, configKey);
		}
		SysOssUploadVo uploadVo = new SysOssUploadVo();
		uploadVo.setUrl(oss.getUrl());
		uploadVo.setFileName(oss.getOriginalName());
		uploadVo.setOssId(oss.getOssId().toString());
		return R.ok(uploadVo);
	}

	/**
	 * Download OSS objects
	 *
	 * @param ossId OSS object ID
	 */
//	@SaCheckPermission("system:oss:download")
	@GetMapping(value = { "/download/{ossId}", "/download/{ossId}/{configKey}" })
	public void download(@PathVariable Long ossId, HttpServletResponse response,
			@PathVariable(value = "configKey", required = false) String configKey) throws IOException {
		if (configKey == null) {
			ossService.download(ossId, response);
		} else {
			ossService.download(ossId, response, configKey);
		}
	}

    /**
     * Delete OSS object storage
     *
     * @param ossIds OSS object ID string
     */
//    @SaCheckPermission("system:oss:remove")
    @Log(title = "OSS object storage", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ossIds}")
    public R<Void> remove(@NotEmpty(message = "Primary key cannot be empty")
                          @PathVariable Long[] ossIds) {
        return toAjax(ossService.deleteWithValidByIds(List.of(ossIds), true));
    }

}
