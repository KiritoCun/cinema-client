package vn.udn.dut.cinema.system.service;

import java.util.Collection;
import java.util.List;

import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.system.domain.bo.SysNotificationBo;
import vn.udn.dut.cinema.system.domain.vo.SysNotificationVo;

/**
 * Notification table service interface
 *
 * @author HoaLD
 * @date 2023-11-10
 */
public interface ISysNotificationService {

    /**
     * Query Notification table
     */
    SysNotificationVo queryById(Long id);

    /**
     * Query Notification table list
     */
    TableDataInfo<SysNotificationVo> queryPageList(SysNotificationBo bo, PageQuery pageQuery);

    /**
     * Query Notification table list
     */
    List<SysNotificationVo> queryList(SysNotificationBo bo);

    /**
     * Add Notification table
     */
    Boolean insertByBo(SysNotificationBo bo);

    /**
     * Edit Notification table
     */
    Boolean updateByBo(SysNotificationBo bo);

    /**
     * Verify and delete Notification table information in batches
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
    
    /**
     * 
     * @param userId
     * @param pageQuery
     * @return
     */
    TableDataInfo<SysNotificationVo> queryPageListByUserId(Long userId, PageQuery pageQuery);
    
    /**
     * 
     * @param userId
     * @return
     */
    Long queryUnseenCountByUserId(Long userId);
    
    /**
     * 
     * @param userId
     */
    void seenAllNotification(Long userId);
    
    /**
     * 
     * @param userId
     * @param id
     */
    void seenById(Long userId, Long id);
}
