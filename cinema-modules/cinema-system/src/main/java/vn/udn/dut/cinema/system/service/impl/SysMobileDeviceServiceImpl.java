package vn.udn.dut.cinema.system.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.RequiredArgsConstructor;
import vn.udn.dut.cinema.common.core.utils.MapstructUtils;
import vn.udn.dut.cinema.common.core.utils.StringUtils;
import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.system.domain.SysMobileDevice;
import vn.udn.dut.cinema.system.domain.bo.SysMobileDeviceBo;
import vn.udn.dut.cinema.system.domain.vo.SysMobileDeviceVo;
import vn.udn.dut.cinema.system.mapper.SysMobileDeviceMapper;
import vn.udn.dut.cinema.system.service.ISysMobileDeviceService;

/**
 * Mobile device tableService business layer processing
 *
 * @author HoaLD
 * @date 2023-11-10
 */
@RequiredArgsConstructor
@Service
public class SysMobileDeviceServiceImpl implements ISysMobileDeviceService {

    private final SysMobileDeviceMapper baseMapper;

    /**
     * Query Mobile device table
     */
    @Override
    public SysMobileDeviceVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * Query Mobile device table list
     */
    @Override
    public TableDataInfo<SysMobileDeviceVo> queryPageList(SysMobileDeviceBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<SysMobileDevice> lqw = buildQueryWrapper(bo);
        Page<SysMobileDeviceVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * Query Mobile device table list
     */
    @Override
    public List<SysMobileDeviceVo> queryList(SysMobileDeviceBo bo) {
        LambdaQueryWrapper<SysMobileDevice> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<SysMobileDevice> buildQueryWrapper(SysMobileDeviceBo bo) {
//        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<SysMobileDevice> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getUserId() != null, SysMobileDevice::getUserId, bo.getUserId());
        lqw.eq(StringUtils.isNotBlank(bo.getJwt()), SysMobileDevice::getJwt, bo.getJwt());
        lqw.eq(StringUtils.isNotBlank(bo.getFirebaseToken()), SysMobileDevice::getFirebaseToken, bo.getFirebaseToken());
        lqw.eq(StringUtils.isNotBlank(bo.getDeviceToken()), SysMobileDevice::getDeviceToken, bo.getDeviceToken());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), SysMobileDevice::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * Add Mobile device table
     */
    @Override
    public Boolean insertByBo(SysMobileDeviceBo bo) {
        SysMobileDevice add = MapstructUtils.convert(bo, SysMobileDevice.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * Edit Mobile device table
     */
    @Override
    public Boolean updateByBo(SysMobileDeviceBo bo) {
        SysMobileDevice update = MapstructUtils.convert(bo, SysMobileDevice.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * Data verification before saving
     */
    private void validEntityBeforeSave(SysMobileDevice entity){
        //TODO Do some data validation, such as unique constraints
    }

    /**
     * Batch delete Mobile device table
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO Do some business verification to determine whether verification is required
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
