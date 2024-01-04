package vn.udn.dut.cinema.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.RequiredArgsConstructor;
import vn.udn.dut.cinema.common.core.utils.MapstructUtils;
import vn.udn.dut.cinema.common.core.utils.StringUtils;
import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.system.constant.SystemConstants;
import vn.udn.dut.cinema.system.domain.SysNotice;
import vn.udn.dut.cinema.system.domain.bo.SysNoticeBo;
import vn.udn.dut.cinema.system.domain.vo.SysNoticeVo;
import vn.udn.dut.cinema.system.domain.vo.SysUserVo;
import vn.udn.dut.cinema.system.mapper.SysNoticeMapper;
import vn.udn.dut.cinema.system.mapper.SysUserMapper;
import vn.udn.dut.cinema.system.service.ISysNoticeService;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Announcement Service layer implementation
 *
 * @author HoaLD
 */
@RequiredArgsConstructor
@Service
public class SysNoticeServiceImpl implements ISysNoticeService {

    private final SysNoticeMapper baseMapper;
    private final SysUserMapper userMapper;

    @Override
    public TableDataInfo<SysNoticeVo> selectPageNoticeList(SysNoticeBo notice, PageQuery pageQuery) {
        LambdaQueryWrapper<SysNotice> lqw = buildQueryWrapper(notice);
        Page<SysNoticeVo> page = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(page);
    }

    /**
     * Query announcement information
     *
     * @param noticeId Notice id
     * @return official news
     */
    @Override
    public SysNoticeVo selectNoticeById(Long noticeId) {
        return baseMapper.selectVoById(noticeId);
    }

    /**
     * Query Announcement List
     *
     * @param notice official news
     * @return announcement collection
     */
    @Override
    public List<SysNoticeVo> selectNoticeList(SysNoticeBo notice) {
        LambdaQueryWrapper<SysNotice> lqw = buildQueryWrapper(notice);
        return baseMapper.selectVoList(lqw);
    }

    @SuppressWarnings("unchecked")
	private LambdaQueryWrapper<SysNotice> buildQueryWrapper(SysNoticeBo bo) {
    	Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<SysNotice> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getNoticeTitle()), SysNotice::getNoticeTitle, bo.getNoticeTitle());
        lqw.eq(StringUtils.isNotBlank(bo.getNoticeType()), SysNotice::getNoticeType, bo.getNoticeType());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), SysNotice::getStatus, bo.getStatus());
        if (StringUtils.isNotBlank(bo.getCreateByName())) {
            SysUserVo sysUser = userMapper.selectUserByUserName(bo.getCreateByName());
            lqw.eq(SysNotice::getCreateBy, ObjectUtil.isNotNull(sysUser) ? sysUser.getUserId() : null);
        }
        lqw.in(params.get("noticeTypes") != null, SysNotice::getNoticeType, (List<String>)params.get("noticeTypes"));
        lqw.between(params.get("beginTime") != null && params.get("endTime") != null,
                SysNotice::getCreateTime, params.get("beginTime"), params.get("endTime"));
        return lqw;
    }

    /**
     * New announcement
     *
     * @param bo official news
     * @return result
     */
    @Override
    public int insertNotice(SysNoticeBo bo) {
        SysNotice notice = MapstructUtils.convert(bo, SysNotice.class);
        return baseMapper.insert(notice);
    }

    /**
     * Modification notice
     *
     * @param bo official news
     * @return result
     */
    @Override
    public int updateNotice(SysNoticeBo bo) {
        SysNotice notice = MapstructUtils.convert(bo, SysNotice.class);
        return baseMapper.updateById(notice);
    }

    /**
     * Delete announcement object
     *
     * @param noticeId notice ID
     * @return result
     */
    @Override
    public int deleteNoticeById(Long noticeId) {
        return baseMapper.deleteById(noticeId);
    }

    /**
     * Batch delete announcement information
     *
     * @param noticeIds Announcement ID to be deleted
     * @return result
     */
    @Override
    public int deleteNoticeByIds(Long[] noticeIds) {
        return baseMapper.deleteBatchIds(Arrays.asList(noticeIds));
    }
    
    /**
     * 
     * @param bo
     * @param noticeType
     */
    @Override
    public void addFilterNoticeType(SysNoticeBo bo, String noticeType) {
    	Map<String, Object> params = bo.getParams();
    	switch (noticeType) {
			case SystemConstants.NOTICE_TYPE_LOGISTICS: {
				params.put("noticeTypes", new ArrayList<>() {
					private static final long serialVersionUID = 1L; {
	                add(SystemConstants.NOTICE_TYPE_ALL);
	                add(SystemConstants.NOTICE_TYPE_LOGISTICS);
	            }});
				return;
			}
			case SystemConstants.NOTICE_TYPE_SHIPPINGLINE: {
				params.put("noticeTypes", new ArrayList<>() {
					private static final long serialVersionUID = 1L; {
	                add(SystemConstants.NOTICE_TYPE_ALL);
	                add(SystemConstants.NOTICE_TYPE_SHIPPINGLINE);
	            }});
				return;
			}
			case SystemConstants.NOTICE_TYPE_VICT: {
				params.put("noticeTypes", new ArrayList<>() {
					private static final long serialVersionUID = 1L; {
	                add(SystemConstants.NOTICE_TYPE_ALL);
	                add(SystemConstants.NOTICE_TYPE_VICT);
	            }});
				return;
			}
			default: return;
    	}
    }
}
