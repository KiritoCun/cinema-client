package vn.udn.dut.cinema.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import vn.udn.dut.cinema.common.core.constant.CacheNames;
import vn.udn.dut.cinema.common.core.exception.ServiceException;
import vn.udn.dut.cinema.common.core.utils.MapstructUtils;
import vn.udn.dut.cinema.common.core.utils.StreamUtils;
import vn.udn.dut.cinema.common.core.utils.StringUtils;
import vn.udn.dut.cinema.common.json.utils.JsonUtils;
import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.common.oss.constant.OssConstant;
import vn.udn.dut.cinema.common.redis.utils.CacheUtils;
import vn.udn.dut.cinema.common.redis.utils.RedisUtils;
import vn.udn.dut.cinema.common.tenant.core.TenantEntity;
import vn.udn.dut.cinema.common.tenant.helper.TenantHelper;
import vn.udn.dut.cinema.system.domain.SysOssConfig;
import vn.udn.dut.cinema.system.domain.bo.SysOssConfigBo;
import vn.udn.dut.cinema.system.domain.vo.SysOssConfigVo;
import vn.udn.dut.cinema.system.mapper.SysOssConfigMapper;
import vn.udn.dut.cinema.system.service.ISysOssConfigService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Object storage configuration Service business layer processing
 *
 * @author HoaLD
 * @date 2021-08-13
 */
@RequiredArgsConstructor
@Service
public class SysOssConfigServiceImpl implements ISysOssConfigService {

    private final SysOssConfigMapper baseMapper;

    /**
     * When the project starts, initialize the parameters to the cache and load the configuration class
     */
    @Override
    public void init() {
        List<SysOssConfig> list = TenantHelper.ignore(() ->
            baseMapper.selectList(
                new LambdaQueryWrapper<SysOssConfig>().orderByAsc(TenantEntity::getTenantId))
        );
        Map<String, List<SysOssConfig>> map = StreamUtils.groupByKey(list, SysOssConfig::getTenantId);
        try {
            for (String tenantId : map.keySet()) {
                TenantHelper.setDynamic(tenantId);
                // Load OSS initialization configuration
                for (SysOssConfig config : map.get(tenantId)) {
                    String configKey = config.getConfigKey();
                    if ("0".equals(config.getStatus())) {
                        RedisUtils.setCacheObject(OssConstant.DEFAULT_CONFIG_KEY, configKey);
                    }
                    CacheUtils.put(CacheNames.SYS_OSS_CONFIG, config.getConfigKey(), JsonUtils.toJsonString(config));
                }
            }
        } finally {
            TenantHelper.clearDynamic();
        }
    }

    @Override
    public SysOssConfigVo queryById(Long ossConfigId) {
        return baseMapper.selectVoById(ossConfigId);
    }

    @Override
    public TableDataInfo<SysOssConfigVo> queryPageList(SysOssConfigBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<SysOssConfig> lqw = buildQueryWrapper(bo);
        Page<SysOssConfigVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }


    private LambdaQueryWrapper<SysOssConfig> buildQueryWrapper(SysOssConfigBo bo) {
        LambdaQueryWrapper<SysOssConfig> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getConfigKey()), SysOssConfig::getConfigKey, bo.getConfigKey());
        lqw.like(StringUtils.isNotBlank(bo.getBucketName()), SysOssConfig::getBucketName, bo.getBucketName());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), SysOssConfig::getStatus, bo.getStatus());
        return lqw;
    }

    @Override
    public Boolean insertByBo(SysOssConfigBo bo) {
        SysOssConfig config = MapstructUtils.convert(bo, SysOssConfig.class);
        validEntityBeforeSave(config);
        boolean flag = baseMapper.insert(config) > 0;
        if (flag) {
            CacheUtils.put(CacheNames.SYS_OSS_CONFIG, config.getConfigKey(), JsonUtils.toJsonString(config));
        }
        return flag;
    }

    @Override
    public Boolean updateByBo(SysOssConfigBo bo) {
        SysOssConfig config = MapstructUtils.convert(bo, SysOssConfig.class);
        validEntityBeforeSave(config);
        LambdaUpdateWrapper<SysOssConfig> luw = new LambdaUpdateWrapper<>();
        luw.set(ObjectUtil.isNull(config.getPrefix()), SysOssConfig::getPrefix, "");
        luw.set(ObjectUtil.isNull(config.getRegion()), SysOssConfig::getRegion, "");
        luw.set(ObjectUtil.isNull(config.getExt1()), SysOssConfig::getExt1, "");
        luw.set(ObjectUtil.isNull(config.getRemark()), SysOssConfig::getRemark, "");
        luw.eq(SysOssConfig::getOssConfigId, config.getOssConfigId());
        boolean flag = baseMapper.update(config, luw) > 0;
        if (flag) {
            CacheUtils.put(CacheNames.SYS_OSS_CONFIG, config.getConfigKey(), JsonUtils.toJsonString(config));
        }
        return flag;
    }

    /**
     * Data verification before saving
     */
    private void validEntityBeforeSave(SysOssConfig entity) {
        if (StringUtils.isNotEmpty(entity.getConfigKey())
            && !checkConfigKeyUnique(entity)) {
            throw new ServiceException("Operation configuration '" + entity.getConfigKey() + "' failed, configuration key already exists!");
        }
    }

    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            if (CollUtil.containsAny(ids, OssConstant.SYSTEM_DATA_IDS)) {
                throw new ServiceException("System built-in, cannot be deleted!");
            }
        }
        List<SysOssConfig> list = CollUtil.newArrayList();
        for (Long configId : ids) {
            SysOssConfig config = baseMapper.selectById(configId);
            list.add(config);
        }
        boolean flag = baseMapper.deleteBatchIds(ids) > 0;
        if (flag) {
            list.forEach(sysOssConfig ->
                CacheUtils.evict(CacheNames.SYS_OSS_CONFIG, sysOssConfig.getConfigKey()));
        }
        return flag;
    }

    /**
     * Determine whether the configKey is unique
     */
    private boolean checkConfigKeyUnique(SysOssConfig sysOssConfig) {
        long ossConfigId = ObjectUtil.isNull(sysOssConfig.getOssConfigId()) ? -1L : sysOssConfig.getOssConfigId();
        SysOssConfig info = baseMapper.selectOne(new LambdaQueryWrapper<SysOssConfig>()
            .select(SysOssConfig::getOssConfigId, SysOssConfig::getConfigKey)
            .eq(SysOssConfig::getConfigKey, sysOssConfig.getConfigKey()));
        if (ObjectUtil.isNotNull(info) && info.getOssConfigId() != ossConfigId) {
            return false;
        }
        return true;
    }

    /**
     * Enable disabled state
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateOssConfigStatus(SysOssConfigBo bo) {
        SysOssConfig sysOssConfig = MapstructUtils.convert(bo, SysOssConfig.class);
        int row = baseMapper.update(null, new LambdaUpdateWrapper<SysOssConfig>()
            .set(SysOssConfig::getStatus, "1"));
        row += baseMapper.updateById(sysOssConfig);
        if (row > 0) {
            RedisUtils.setCacheObject(OssConstant.DEFAULT_CONFIG_KEY, sysOssConfig.getConfigKey());
        }
        return row;
    }

}
