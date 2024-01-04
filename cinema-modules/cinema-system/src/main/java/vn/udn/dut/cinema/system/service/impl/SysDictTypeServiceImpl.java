package vn.udn.dut.cinema.system.service.impl;

import cn.dev33.satoken.context.SaHolder;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.RequiredArgsConstructor;
import vn.udn.dut.cinema.common.core.constant.CacheConstants;
import vn.udn.dut.cinema.common.core.constant.CacheNames;
import vn.udn.dut.cinema.common.core.exception.ServiceException;
import vn.udn.dut.cinema.common.core.service.DictService;
import vn.udn.dut.cinema.common.core.utils.MapstructUtils;
import vn.udn.dut.cinema.common.core.utils.SpringUtils;
import vn.udn.dut.cinema.common.core.utils.StreamUtils;
import vn.udn.dut.cinema.common.core.utils.StringUtils;
import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.common.redis.utils.CacheUtils;
import vn.udn.dut.cinema.system.domain.SysDictData;
import vn.udn.dut.cinema.system.domain.SysDictType;
import vn.udn.dut.cinema.system.domain.bo.SysDictTypeBo;
import vn.udn.dut.cinema.system.domain.vo.SysDictDataVo;
import vn.udn.dut.cinema.system.domain.vo.SysDictTypeVo;
import vn.udn.dut.cinema.system.mapper.SysDictDataMapper;
import vn.udn.dut.cinema.system.mapper.SysDictTypeMapper;
import vn.udn.dut.cinema.system.service.ISysDictTypeService;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Dictionary business layer processing
 *
 * @author HoaLD
 */
@RequiredArgsConstructor
@Service
public class SysDictTypeServiceImpl implements ISysDictTypeService, DictService {

    private final SysDictTypeMapper baseMapper;
    private final SysDictDataMapper dictDataMapper;

    @Override
    public TableDataInfo<SysDictTypeVo> selectPageDictTypeList(SysDictTypeBo dictType, PageQuery pageQuery) {
        LambdaQueryWrapper<SysDictType> lqw = buildQueryWrapper(dictType);
        Page<SysDictTypeVo> page = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(page);
    }

    /**
     * Pagination query dictionary type according to conditions
     *
     * @param dictType Dictionary type information
     * @return Dictionary type collection information
     */
    @Override
    public List<SysDictTypeVo> selectDictTypeList(SysDictTypeBo dictType) {
        LambdaQueryWrapper<SysDictType> lqw = buildQueryWrapper(dictType);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<SysDictType> buildQueryWrapper(SysDictTypeBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<SysDictType> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getDictName()), SysDictType::getDictName, bo.getDictName());
        lqw.like(StringUtils.isNotBlank(bo.getDictType()), SysDictType::getDictType, bo.getDictType());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), SysDictType::getStatus, bo.getStatus());
        lqw.between(params.get("beginTime") != null && params.get("endTime") != null,
            SysDictType::getCreateTime, params.get("beginTime"), params.get("endTime"));
        return lqw;
    }

    /**
     * According to all dictionary types
     *
     * @return Dictionary type collection information
     */
    @Override
    public List<SysDictTypeVo> selectDictTypeAll() {
        return baseMapper.selectVoList();
    }

    /**
     * Query dictionary data according to dictionary type
     *
     * @param dictType dictionary type
     * @return Dictionary data set information
     */
    @Cacheable(cacheNames = CacheNames.SYS_DICT, key = "#dictType")
    @Override
    public List<SysDictDataVo> selectDictDataByType(String dictType) {
        List<SysDictDataVo> dictDatas = dictDataMapper.selectDictDataByType(dictType);
        if (CollUtil.isNotEmpty(dictDatas)) {
            return dictDatas;
        }
        return null;
    }

    /**
     * Query information based on dictionary type ID
     *
     * @param dictId dictionary type ID
     * @return dictionary type
     */
    @Override
    public SysDictTypeVo selectDictTypeById(Long dictId) {
        return baseMapper.selectVoById(dictId);
    }

    /**
     * Query information based on dictionary type
     *
     * @param dictType dictionary type
     * @return dictionary type
     */
    @Cacheable(cacheNames = CacheNames.SYS_DICT, key = "#dictType")
    @Override
    public SysDictTypeVo selectDictTypeByType(String dictType) {
        return baseMapper.selectVoById(new LambdaQueryWrapper<SysDictType>().eq(SysDictType::getDictType, dictType));
    }

    /**
     * Batch delete dictionary type information
     *
     * @param dictIds The dictionary ID to delete
     */
    @Override
    public void deleteDictTypeByIds(Long[] dictIds) {
        for (Long dictId : dictIds) {
            SysDictType dictType = baseMapper.selectById(dictId);
            if (dictDataMapper.exists(new LambdaQueryWrapper<SysDictData>()
                .eq(SysDictData::getDictType, dictType.getDictType()))) {
                throw new ServiceException(String.format("%1$s已分配,不能删除", dictType.getDictName()));
            }
            CacheUtils.evict(CacheNames.SYS_DICT, dictType.getDictType());
        }
        baseMapper.deleteBatchIds(Arrays.asList(dictIds));
    }

    /**
     * Reset dictionary cache data
     */
    @Override
    public void resetDictCache() {
        CacheUtils.clear(CacheNames.SYS_DICT);
    }

    /**
     * Add save dictionary type information
     *
     * @param bo Dictionary type information
     * @return result
     */
    @CachePut(cacheNames = CacheNames.SYS_DICT, key = "#bo.dictType")
    @Override
    public List<SysDictTypeVo> insertDictType(SysDictTypeBo bo) {
        SysDictType dict = MapstructUtils.convert(bo, SysDictType.class);
        int row = baseMapper.insert(dict);
        if (row > 0) {
            return new ArrayList<>();
        }
        throw new ServiceException("Operation failed");
    }

    /**
     * Modify and save dictionary type information
     *
     * @param bo Dictionary type information
     * @return result
     */
    @CachePut(cacheNames = CacheNames.SYS_DICT, key = "#bo.dictType")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<SysDictDataVo> updateDictType(SysDictTypeBo bo) {
        SysDictType dict = MapstructUtils.convert(bo, SysDictType.class);
        SysDictType oldDict = baseMapper.selectById(dict.getDictId());
        dictDataMapper.update(null, new LambdaUpdateWrapper<SysDictData>()
            .set(SysDictData::getDictType, dict.getDictType())
            .eq(SysDictData::getDictType, oldDict.getDictType()));
        int row = baseMapper.updateById(dict);
        if (row > 0) {
            CacheUtils.evict(CacheNames.SYS_DICT, oldDict.getDictType());
            return dictDataMapper.selectDictDataByType(dict.getDictType());
        }
        throw new ServiceException("Operation failed");
    }

    /**
     * Check whether the dictionary type name is unique
     *
     * @param dictType dictionary type
     * @return result
     */
    @Override
    public boolean checkDictTypeUnique(SysDictTypeBo dictType) {
        boolean exist = baseMapper.exists(new LambdaQueryWrapper<SysDictType>()
            .eq(SysDictType::getDictType, dictType.getDictType())
            .ne(ObjectUtil.isNotNull(dictType.getDictId()), SysDictType::getDictId, dictType.getDictId()));
        return !exist;
    }

    /**
     * Get dictionary labels based on dictionary type and dictionary value
     *
     * @param dictType  dictionary type
     * @param dictValue dictionary value
     * @param separator delimiter
     * @return dictionary tag
     */
    @Override
    public String getDictLabel(String dictType, String dictValue, String separator) {
        // Get it from the local cache first
        @SuppressWarnings("unchecked")
		List<SysDictDataVo> datas = (List<SysDictDataVo>) SaHolder.getStorage().get(CacheConstants.SYS_DICT_KEY + dictType);
        if (ObjectUtil.isNull(datas)) {
            datas = SpringUtils.getAopProxy(this).selectDictDataByType(dictType);
            SaHolder.getStorage().set(CacheConstants.SYS_DICT_KEY + dictType, datas);
        }

        Map<String, String> map = StreamUtils.toMap(datas, SysDictDataVo::getDictValue, SysDictDataVo::getDictLabel);
        if (StringUtils.containsAny(dictValue, separator)) {
            return Arrays.stream(dictValue.split(separator))
                .map(v -> map.getOrDefault(v, StringUtils.EMPTY))
                .collect(Collectors.joining(separator));
        } else {
            return map.getOrDefault(dictValue, StringUtils.EMPTY);
        }
    }

    /**
     * Get dictionary value based on dictionary type and dictionary label
     *
     * @param dictType  dictionary type
     * @param dictLabel dictionary tag
     * @param separator delimiter
     * @return dictionary value
     */
    @Override
    public String getDictValue(String dictType, String dictLabel, String separator) {
        // Get it from the local cache first
        @SuppressWarnings("unchecked")
		List<SysDictDataVo> datas = (List<SysDictDataVo>) SaHolder.getStorage().get(CacheConstants.SYS_DICT_KEY + dictType);
        if (ObjectUtil.isNull(datas)) {
            datas = SpringUtils.getAopProxy(this).selectDictDataByType(dictType);
            SaHolder.getStorage().set(CacheConstants.SYS_DICT_KEY + dictType, datas);
        }

        Map<String, String> map = StreamUtils.toMap(datas, SysDictDataVo::getDictLabel, SysDictDataVo::getDictValue);
        if (StringUtils.containsAny(dictLabel, separator)) {
            return Arrays.stream(dictLabel.split(separator))
                .map(l -> map.getOrDefault(l, StringUtils.EMPTY))
                .collect(Collectors.joining(separator));
        } else {
            return map.getOrDefault(dictLabel, StringUtils.EMPTY);
        }
    }

}
