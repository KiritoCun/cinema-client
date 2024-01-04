package vn.udn.dut.cinema.system.service;

import jakarta.servlet.http.HttpServletResponse;
import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.system.domain.bo.SysOssBo;
import vn.udn.dut.cinema.system.domain.vo.SysOssVo;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * File upload service layer
 *
 * @author HoaLD
 */
public interface ISysOssService {

    TableDataInfo<SysOssVo> queryPageList(SysOssBo sysOss, PageQuery pageQuery);

    List<SysOssVo> listByIds(Collection<Long> ossIds);

    SysOssVo getById(Long ossId);

    SysOssVo upload(MultipartFile file);
    
    SysOssVo upload(MultipartFile file, String configKey);

    void download(Long ossId, HttpServletResponse response) throws IOException;
    
    void download(Long ossId, HttpServletResponse response, String configKey) throws IOException;

    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

}
