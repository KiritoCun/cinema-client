package vn.udn.dut.cinema.system.service.impl;

import java.util.List;

import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.RequiredArgsConstructor;
import vn.udn.dut.cinema.common.core.constant.CacheNames;
import vn.udn.dut.cinema.common.core.exception.ServiceException;
import vn.udn.dut.cinema.common.core.utils.MapstructUtils;
import vn.udn.dut.cinema.common.core.utils.StringUtils;
import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.common.redis.utils.CacheUtils;
import vn.udn.dut.cinema.system.domain.SysDictData;
import vn.udn.dut.cinema.system.domain.bo.ContainerSztpBo;
import vn.udn.dut.cinema.system.domain.bo.SysDictDataBo;
import vn.udn.dut.cinema.system.domain.vo.ContainerSztpVo;
import vn.udn.dut.cinema.system.domain.vo.SysDictDataVo;
import vn.udn.dut.cinema.system.mapper.SysDictDataMapper;
import vn.udn.dut.cinema.system.service.ISysDictDataService;

/**
 * Dictionary business layer processing
 *
 * @author HoaLD
 */
@RequiredArgsConstructor
@Service
public class SysDictDataServiceImpl implements ISysDictDataService {

    private final SysDictDataMapper baseMapper;

    @Override
    public TableDataInfo<SysDictDataVo> selectPageDictDataList(SysDictDataBo dictData, PageQuery pageQuery) {
        LambdaQueryWrapper<SysDictData> lqw = buildQueryWrapper(dictData);
        Page<SysDictDataVo> page = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(page);
    }

    /**
     * Paging query dictionary data according to conditions
     *
     * @param dictData Dictionary data information
     * @return Dictionary data set information
     */
    @Override
    public List<SysDictDataVo> selectDictDataList(SysDictDataBo dictData) {
        LambdaQueryWrapper<SysDictData> lqw = buildQueryWrapper(dictData);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<SysDictData> buildQueryWrapper(SysDictDataBo bo) {
        LambdaQueryWrapper<SysDictData> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getDictSort() != null, SysDictData::getDictSort, bo.getDictSort());
        lqw.like(StringUtils.isNotBlank(bo.getDictLabel()), SysDictData::getDictLabel, bo.getDictLabel());
        lqw.eq(StringUtils.isNotBlank(bo.getDictType()), SysDictData::getDictType, bo.getDictType());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), SysDictData::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * Query dictionary data information according to dictionary type and dictionary key value
     *
     * @param dictType  dictionary type
     * @param dictValue Dictionary key value
     * @return dictionary tag
     */
    @Override
    public String selectDictLabel(String dictType, String dictValue) {
        return baseMapper.selectList(new LambdaQueryWrapper<SysDictData>()
				.select(SysDictData::getDictLabel)
				.eq(SysDictData::getDictType, dictType)
				.eq(SysDictData::getDictValue, dictValue))
        	.get(0)
            .getDictLabel();
    }

    /**
     * Query information based on dictionary data ID
     *
     * @param dictCode Dictionary data ID
     * @return dictionary data
     */
    @Override
    public SysDictDataVo selectDictDataById(Long dictCode) {
        return baseMapper.selectVoById(dictCode);
    }

    /**
     * Batch delete dictionary data information
     *
     * @param dictCodes The ID of the dictionary data that needs to be deleted
     */
    @Override
    public void deleteDictDataByIds(Long[] dictCodes) {
        for (Long dictCode : dictCodes) {
            SysDictData data = baseMapper.selectById(dictCode);
            baseMapper.deleteById(dictCode);
            CacheUtils.evict(CacheNames.SYS_DICT, data.getDictType());
        }
    }

    /**
     * Added saving dictionary data information
     *
     * @param bo Dictionary data information
     * @return result
     */
    @CachePut(cacheNames = CacheNames.SYS_DICT, key = "#bo.dictType")
    @Override
    public List<SysDictDataVo> insertDictData(SysDictDataBo bo) {
        SysDictData data = MapstructUtils.convert(bo, SysDictData.class);
        int row = baseMapper.insert(data);
        if (row > 0) {
            return baseMapper.selectDictDataByType(data.getDictType());
        }
        throw new ServiceException("Operation failed");
    }

    /**
     * Modify and save dictionary data information
     *
     * @param bo Dictionary data information
     * @return result
     */
    @CachePut(cacheNames = CacheNames.SYS_DICT, key = "#bo.dictType")
    @Override
    public List<SysDictDataVo> updateDictData(SysDictDataBo bo) {
        SysDictData data = MapstructUtils.convert(bo, SysDictData.class);
        int row = baseMapper.updateById(data);
        if (row > 0) {
            return baseMapper.selectDictDataByType(data.getDictType());
        }
        throw new ServiceException("Operation failed");
    }

	@Override
	public TableDataInfo<ContainerSztpVo> queryContainerSztpPageList(ContainerSztpBo bo, PageQuery pageQuery) {
		return TableDataInfo.build(baseMapper.selectContainerSztpsByBo(pageQuery.build(), buildQueryWrapper(bo)));
	}

	private Wrapper<ContainerSztpVo> buildQueryWrapper(ContainerSztpBo bo) {
		QueryWrapper<ContainerSztpVo> wrapper = Wrappers.query();
		wrapper.like(StringUtils.isNotBlank(bo.getLen()), "s1.dict_value", bo.getLen())
			   .like(StringUtils.isNotBlank(bo.getType()), "s2.dict_value", bo.getType())
			   .like(StringUtils.isNotBlank(bo.getSztp()), "s1.dict_label", bo.getSztp())
			   .like(StringUtils.isNotBlank(bo.getDescription()), "s3.dict_value", bo.getDescription())
			   .eq("s1.dict_type", "sys_cont_len_tms")
			   .eq("s2.dict_type", "sys_cont_type_tms")
			   .eq("s3.dict_type", "sys_cont_sztp");
		return wrapper;
	}
}
