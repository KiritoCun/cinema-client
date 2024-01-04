package vn.udn.dut.cinema.system.service;

import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.system.domain.bo.SysNoticeBo;
import vn.udn.dut.cinema.system.domain.vo.SysNoticeVo;

import java.util.List;

/**
 * Announcement Service Layer
 *
 * @author HoaLD
 */
public interface ISysNoticeService {


    TableDataInfo<SysNoticeVo> selectPageNoticeList(SysNoticeBo notice, PageQuery pageQuery);

    /**
     * Query announcement information
     *
     * @param noticeId notice ID
     * @return official news
     */
    SysNoticeVo selectNoticeById(Long noticeId);

    /**
     * Query Announcement List
     *
     * @param notice official news
     * @return announcement collection
     */
    List<SysNoticeVo> selectNoticeList(SysNoticeBo notice);

    /**
     * New announcement
     *
     * @param bo official news
     * @return result
     */
    int insertNotice(SysNoticeBo bo);

    /**
     * Modification notice
     *
     * @param bo official news
     * @return result
     */
    int updateNotice(SysNoticeBo bo);

    /**
     * Delete announcement information
     *
     * @param noticeId notice ID
     * @return result
     */
    int deleteNoticeById(Long noticeId);

    /**
     * Batch delete announcement information
     *
     * @param noticeIds Announcement ID to be deleted
     * @return result
     */
    int deleteNoticeByIds(Long[] noticeIds);
    
    /**
     * 
     * @param bo
     * @param noticeType
     */
    void addFilterNoticeType(SysNoticeBo bo, String noticeType);
}
