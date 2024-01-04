package vn.udn.dut.cinema.system.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.RequiredArgsConstructor;
import vn.udn.dut.cinema.common.core.utils.StringUtils;
import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.system.domain.SysDataHistory;
import vn.udn.dut.cinema.system.domain.bo.SysDataHistoryBo;
import vn.udn.dut.cinema.system.domain.vo.SysDataHistoryVo;
import vn.udn.dut.cinema.system.mapper.SysDataHistoryMapper;
import vn.udn.dut.cinema.system.service.ISysDataHistoryService;

/**
 * System data historyService business layer processing
 *
 * @author HoaLD
 * @date 2023-08-23
 */
@RequiredArgsConstructor
@Service
public class SysDataHistoryServiceImpl implements ISysDataHistoryService {

    private final SysDataHistoryMapper baseMapper;

    /**
     * Query System data history
     */
    @Override
    public SysDataHistoryVo queryById(Long id){
        return baseMapper.selectDataHistoryById(id);
    }

    /**
     * Query System data history list
     */
    @Override
    public TableDataInfo<SysDataHistoryVo> queryPageList(SysDataHistoryBo bo, PageQuery pageQuery) {
    	Page<SysDataHistoryVo> page = baseMapper.selectPageDataHistoryList(pageQuery.build(), this.buildQueryWrapper(bo));
        return TableDataInfo.build(page);
    }

    /**
     * Query System data history list
     */
    @Override
    public List<SysDataHistoryVo> queryList(SysDataHistoryBo bo) {
    	return baseMapper.selectDataHistoryList(this.buildQueryWrapper(bo));
    }

    private Wrapper<SysDataHistory> buildQueryWrapper(SysDataHistoryBo bo) {
    	Map<String, Object> params = bo.getParams();
        QueryWrapper<SysDataHistory> wrapper = Wrappers.query();
        wrapper.eq(bo.getRefId() != null, "d.ref_id", bo.getRefId())
            .like(StringUtils.isNotBlank(bo.getNewValue()), "d.new_value", bo.getNewValue())
            .like(StringUtils.isNotBlank(bo.getOldValue()), "d.old_value", bo.getOldValue())
            .like(StringUtils.isNotBlank(bo.getDataField()), "d.data_field", bo.getDataField())
            .eq(StringUtils.isNotBlank(bo.getUsername()), "u.user_name", bo.getUsername())
            .eq(StringUtils.isNotBlank(bo.getHistType()), "d.hist_type", bo.getHistType())
            .eq(StringUtils.isNotBlank(bo.getTableName()), "d.table_name", bo.getTableName())
            .eq(StringUtils.isNotBlank(bo.getSystemType()), "d.system_type", bo.getSystemType())
            .between(params.get("beginTime") != null && params.get("endTime") != null,
                "d.create_time", params.get("beginTime"), params.get("endTime"));
        return wrapper;
    }

    /**
     * Batch delete System data history
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //Do some business verification to determine whether verification is required
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
