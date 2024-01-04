package vn.udn.dut.cinema.system.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.RequiredArgsConstructor;
import vn.udn.dut.cinema.common.core.utils.MapstructUtils;
import vn.udn.dut.cinema.common.core.utils.StringUtils;
import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.system.domain.SysDocument;
import vn.udn.dut.cinema.system.domain.bo.SysDocumentBo;
import vn.udn.dut.cinema.system.domain.vo.SysDocumentVo;
import vn.udn.dut.cinema.system.mapper.SysDocumentMapper;
import vn.udn.dut.cinema.system.service.ISysDocumentService;
import vn.udn.dut.cinema.system.service.ISysOssService;

/**
 * System documentService business layer processing
 *
 * @author HoaLD
 * @date 2023-08-23
 */
@RequiredArgsConstructor
@Service
public class SysDocumentServiceImpl implements ISysDocumentService {

    private final SysDocumentMapper baseMapper;
    private final ISysOssService ossService;

    /**
     * Query System document
     */
    @Override
    public SysDocumentVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * Query System document list
     */
    @Override
    public TableDataInfo<SysDocumentVo> queryPageList(SysDocumentBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<SysDocument> lqw = buildQueryWrapper(bo);
        Page<SysDocumentVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * Query System document list
     */
    @Override
    public List<SysDocumentVo> queryList(SysDocumentBo bo) {
        LambdaQueryWrapper<SysDocument> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<SysDocument> buildQueryWrapper(SysDocumentBo bo) {
//        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<SysDocument> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getDocType()), SysDocument::getDocType, bo.getDocType());
        lqw.like(StringUtils.isNotBlank(bo.getDocTitle()), SysDocument::getDocTitle, bo.getDocTitle());
        lqw.eq(StringUtils.isNotBlank(bo.getDocUrl()), SysDocument::getDocUrl, bo.getDocUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getDocDescription()), SysDocument::getDocDescription, bo.getDocDescription());
        lqw.eq(StringUtils.isNotBlank(bo.getDocTarget()), SysDocument::getDocTarget, bo.getDocTarget());
        lqw.eq(StringUtils.isNotBlank(bo.getImageType()), SysDocument::getImageType, bo.getImageType());
        lqw.eq(StringUtils.isNotBlank(bo.getIsExternalLink()), SysDocument::getIsExternalLink, bo.getIsExternalLink());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), SysDocument::getStatus, bo.getStatus());
        lqw.eq(bo.getOssId() != null, SysDocument::getOssId, bo.getOssId());
        return lqw;
    }

    /**
     * Add System document
     */
    @Override
    public Boolean insertByBo(SysDocumentBo bo) {
        SysDocument add = MapstructUtils.convert(bo, SysDocument.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * Edit System document
     */
    @Override
    public Boolean updateByBo(SysDocumentBo bo) {
        SysDocument update = MapstructUtils.convert(bo, SysDocument.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * Data verification before saving
     */
    private void validEntityBeforeSave(SysDocument entity){
        //Do some data validation, such as unique constraints
    }

    /**
     * Batch delete System document
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //Do some business verification to determine whether verification is required
        }
        for (Long id : ids) {
        	SysDocumentVo vo = queryById(id);
        	if (vo.getOssId() != null) {
        		List<Long> ossIds = new ArrayList<>();
        		ossIds.add(vo.getOssId());
        		ossService.deleteWithValidByIds(ids, true);
        	}
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
    
    /**
     * modify document status
     *
     * @param id Document ID
     * @param status document status
     * @return result
     */
    @Override
    public int updateDocumentStatus(Long id, String status) {
		return baseMapper.update(null,
				new LambdaUpdateWrapper<SysDocument>().set(SysDocument::getStatus, status).eq(SysDocument::getId, id));
    }
}
