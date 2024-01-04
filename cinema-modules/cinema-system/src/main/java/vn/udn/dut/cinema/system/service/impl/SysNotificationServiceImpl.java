package vn.udn.dut.cinema.system.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vn.udn.dut.cinema.common.core.utils.MapstructUtils;
import vn.udn.dut.cinema.common.core.utils.StringUtils;
import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.common.websocket.utils.WebSocketUtils;
import vn.udn.dut.cinema.system.domain.SysNotification;
import vn.udn.dut.cinema.system.domain.bo.SysMobileDeviceBo;
import vn.udn.dut.cinema.system.domain.bo.SysNotificationBo;
import vn.udn.dut.cinema.system.domain.bo.SysNotificationSocketBo;
import vn.udn.dut.cinema.system.domain.vo.SysMobileDeviceVo;
import vn.udn.dut.cinema.system.domain.vo.SysNotificationVo;
import vn.udn.dut.cinema.system.mapper.SysNotificationMapper;
import vn.udn.dut.cinema.system.runner.FirebaseService;
import vn.udn.dut.cinema.system.service.ISysMobileDeviceService;
import vn.udn.dut.cinema.system.service.ISysNotificationService;

/**
 * Notification tableService business layer processing
 *
 * @author HoaLD
 * @date 2023-11-10
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SysNotificationServiceImpl implements ISysNotificationService {

    private final SysNotificationMapper baseMapper;
    private final FirebaseService firebaseService;
    private final ISysMobileDeviceService mobileDeviceService;

    /**
     * Query Notification table
     */
    @Override
    public SysNotificationVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * Query Notification table list
     */
    @Override
    public TableDataInfo<SysNotificationVo> queryPageList(SysNotificationBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<SysNotification> lqw = buildQueryWrapper(bo);
        Page<SysNotificationVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * Query Notification table list
     */
    @Override
    public List<SysNotificationVo> queryList(SysNotificationBo bo) {
        LambdaQueryWrapper<SysNotification> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<SysNotification> buildQueryWrapper(SysNotificationBo bo) {
//        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<SysNotification> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getUserId() != null, SysNotification::getUserId, bo.getUserId());
        lqw.like(StringUtils.isNotBlank(bo.getTitle()), SysNotification::getTitle, bo.getTitle());
        lqw.eq(StringUtils.isNotBlank(bo.getContent()), SysNotification::getContent, bo.getContent());
        lqw.eq(StringUtils.isNotBlank(bo.getComponentPath()), SysNotification::getComponentPath, bo.getComponentPath());
        lqw.eq(StringUtils.isNotBlank(bo.getSeenFlag()), SysNotification::getSeenFlag, bo.getSeenFlag());
        return lqw;
    }

    /**
     * Add Notification table
     */
    @Override
    public Boolean insertByBo(SysNotificationBo bo) {
        SysNotification add = MapstructUtils.convert(bo, SysNotification.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        
        // Send websocket
        new Thread() {
			public void run() {
				try {
					SysNotificationSocketBo notiSocket = new SysNotificationSocketBo();
					notiSocket.setId(add.getId());
			        notiSocket.setTitle(bo.getTitle());
			        notiSocket.setRoutePath(bo.getComponentPath());
			        notiSocket.setMessage(bo.getContent());
			        WebSocketUtils.sendMessage(bo.getUserId(), new Gson().toJson(notiSocket));
				} catch (Exception e) {
					log.error("[WEBSOCKET SEND NOTIFICATION] " + e);
				}
			}
		}.start();
        
        // Send firebase
        new Thread() {
			public void run() {
				try {
					SysMobileDeviceBo query = new SysMobileDeviceBo();
					query.setUserId(bo.getUserId());
					query.setStatus("0");
					List<SysMobileDeviceVo> vos = mobileDeviceService.queryList(query);
					for (SysMobileDeviceVo vo : vos) {
						firebaseService.sendNotification(bo.getTitle(), bo.getContent(), bo.getComponentPath(), vo.getFirebaseToken());
					}
				} catch (Exception e) {
					log.error("[FIREBASE SEND NOTIFICATION] " + e);
				}
			}
		}.start();
        
        return flag;
    }

    /**
     * Edit Notification table
     */
    @Override
    public Boolean updateByBo(SysNotificationBo bo) {
        SysNotification update = MapstructUtils.convert(bo, SysNotification.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * Data verification before saving
     */
    private void validEntityBeforeSave(SysNotification entity){
        //TODO Do some data validation, such as unique constraints
    }

    /**
     * Batch delete Notification table
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO Do some business verification to determine whether verification is required
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
    
    /**
     * 
     * @param userId
     * @param pageQuery
     * @return
     */
    @Override
    public TableDataInfo<SysNotificationVo> queryPageListByUserId(Long userId, PageQuery pageQuery) {
    	SysNotificationBo bo = new SysNotificationBo();
    	bo.setUserId(userId);
    	return queryPageList(bo, pageQuery);
    }
    
    /**
     * 
     * @param userId
     * @return
     */
    @Override
    public Long queryUnseenCountByUserId(Long userId) {
    	SysNotificationBo bo = new SysNotificationBo();
    	bo.setUserId(userId);
    	bo.setSeenFlag("0");
    	LambdaQueryWrapper<SysNotification> lqw = buildQueryWrapper(bo);
    	return baseMapper.selectCount(lqw);
    }
    
    /**
     * 
     * @param userId
     */
    @Override
    public void seenAllNotification(Long userId) {
    	SysNotificationBo bo = new SysNotificationBo();
    	bo.setUserId(userId);
    	bo.setSeenFlag("0");
    	LambdaQueryWrapper<SysNotification> lqw = buildQueryWrapper(bo);
    	SysNotification update = new SysNotification();
    	update.setSeenFlag("1");
    	baseMapper.update(update, lqw);
    }
    
    /**
     * 
     * @param userId
     * @param id
     */
    public void seenById(Long userId, Long id) {
    	SysNotificationBo bo = new SysNotificationBo();
    	bo.setUserId(userId);
    	bo.setId(id);
    	bo.setSeenFlag("1");
    	updateByBo(bo);
    }
}
