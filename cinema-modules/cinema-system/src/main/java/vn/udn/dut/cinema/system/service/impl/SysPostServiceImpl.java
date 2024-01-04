package vn.udn.dut.cinema.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.RequiredArgsConstructor;
import vn.udn.dut.cinema.common.core.exception.ServiceException;
import vn.udn.dut.cinema.common.core.utils.MapstructUtils;
import vn.udn.dut.cinema.common.core.utils.StringUtils;
import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.system.domain.SysPost;
import vn.udn.dut.cinema.system.domain.SysUserPost;
import vn.udn.dut.cinema.system.domain.bo.SysPostBo;
import vn.udn.dut.cinema.system.domain.vo.SysPostVo;
import vn.udn.dut.cinema.system.mapper.SysPostMapper;
import vn.udn.dut.cinema.system.mapper.SysUserPostMapper;
import vn.udn.dut.cinema.system.service.ISysPostService;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Job Information Service Layer Processing
 *
 * @author HoaLD
 */
@RequiredArgsConstructor
@Service
public class SysPostServiceImpl implements ISysPostService {

    private final SysPostMapper baseMapper;
    private final SysUserPostMapper userPostMapper;

    @Override
    public TableDataInfo<SysPostVo> selectPagePostList(SysPostBo post, PageQuery pageQuery) {
        LambdaQueryWrapper<SysPost> lqw = buildQueryWrapper(post);
        Page<SysPostVo> page = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(page);
    }

    /**
     * Query job information collection
     *
     * @param post job information
     * @return job information collection
     */
    @Override
    public List<SysPostVo> selectPostList(SysPostBo post) {
        LambdaQueryWrapper<SysPost> lqw = buildQueryWrapper(post);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<SysPost> buildQueryWrapper(SysPostBo bo) {
        LambdaQueryWrapper<SysPost> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getPostCode()), SysPost::getPostCode, bo.getPostCode());
        lqw.like(StringUtils.isNotBlank(bo.getPostName()), SysPost::getPostName, bo.getPostName());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), SysPost::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * Search all positions
     *
     * @return job list
     */
    @Override
    public List<SysPostVo> selectPostAll() {
        return baseMapper.selectVoList(new QueryWrapper<>());
    }

    /**
     * Query job information by job ID
     *
     * @param postId Job ID
     * @return role object information
     */
    @Override
    public SysPostVo selectPostById(Long postId) {
        return baseMapper.selectVoById(postId);
    }

    /**
     * Obtain a list of job selection boxes based on user ID
     *
     * @param userId User ID
     * @return Selected post ID list
     */
    @Override
    public List<Long> selectPostListByUserId(Long userId) {
        return baseMapper.selectPostListByUserId(userId);
    }

    /**
     * Check if the job title is unique
     *
     * @param post job information
     * @return result
     */
    @Override
    public boolean checkPostNameUnique(SysPostBo post) {
        boolean exist = baseMapper.exists(new LambdaQueryWrapper<SysPost>()
            .eq(SysPost::getPostName, post.getPostName())
            .ne(ObjectUtil.isNotNull(post.getPostId()), SysPost::getPostId, post.getPostId()));
        return !exist;
    }

    /**
     * Verify that the job code is unique
     *
     * @param post job information
     * @return result
     */
    @Override
    public boolean checkPostCodeUnique(SysPostBo post) {
        boolean exist = baseMapper.exists(new LambdaQueryWrapper<SysPost>()
            .eq(SysPost::getPostCode, post.getPostCode())
            .ne(ObjectUtil.isNotNull(post.getPostId()), SysPost::getPostId, post.getPostId()));
        return !exist;
    }

    /**
     * Query the number of positions used by the position ID
     *
     * @param postId Job ID
     * @return result
     */
    @Override
    public long countUserPostById(Long postId) {
        return userPostMapper.selectCount(new LambdaQueryWrapper<SysUserPost>().eq(SysUserPost::getPostId, postId));
    }

    /**
     * Delete job information
     *
     * @param postId Job ID
     * @return result
     */
    @Override
    public int deletePostById(Long postId) {
        return baseMapper.deleteById(postId);
    }

    /**
     * Batch delete job information
     *
     * @param postIds Job ID to be deleted
     * @return result
     */
    @Override
    public int deletePostByIds(Long[] postIds) {
        for (Long postId : postIds) {
            SysPost post = baseMapper.selectById(postId);
            if (countUserPostById(postId) > 0) {
                throw new ServiceException(String.format("%1$s has been allocated and cannot be deleted", post.getPostName()));
            }
        }
        return baseMapper.deleteBatchIds(Arrays.asList(postIds));
    }

    /**
     * Add save position information
     *
     * @param bo job information
     * @return result
     */
    @Override
    public int insertPost(SysPostBo bo) {
        SysPost post = MapstructUtils.convert(bo, SysPost.class);
        return baseMapper.insert(post);
    }

    /**
     * Modify and save job information
     *
     * @param bo job information
     * @return result
     */
    @Override
    public int updatePost(SysPostBo bo) {
        SysPost post = MapstructUtils.convert(bo, SysPost.class);
        return baseMapper.updateById(post);
    }
}
