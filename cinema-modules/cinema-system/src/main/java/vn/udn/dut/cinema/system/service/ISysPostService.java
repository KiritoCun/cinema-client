package vn.udn.dut.cinema.system.service;

import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.system.domain.bo.SysPostBo;
import vn.udn.dut.cinema.system.domain.vo.SysPostVo;

import java.util.List;

/**
 * Job Information Service Layer
 *
 * @author HoaLD
 */
public interface ISysPostService {


    TableDataInfo<SysPostVo> selectPagePostList(SysPostBo post, PageQuery pageQuery);

    /**
     * Query job information collection
     *
     * @param post job information
     * @return job list
     */
    List<SysPostVo> selectPostList(SysPostBo post);

    /**
     * Search all positions
     *
     * @return job list
     */
    List<SysPostVo> selectPostAll();

    /**
     * Query job information by job ID
     *
     * @param postId Job ID
     * @return role object information
     */
    SysPostVo selectPostById(Long postId);

    /**
     * Obtain a list of job selection boxes based on user ID
     *
     * @param userId User ID
     * @return Selected post ID list
     */
    List<Long> selectPostListByUserId(Long userId);

    /**
     * Check job title
     *
     * @param post job information
     * @return result
     */
    boolean checkPostNameUnique(SysPostBo post);

    /**
     * Verify job code
     *
     * @param post job information
     * @return result
     */
    boolean checkPostCodeUnique(SysPostBo post);

    /**
     * Query the number of positions used by the position ID
     *
     * @param postId Job ID
     * @return result
     */
    long countUserPostById(Long postId);

    /**
     * Delete job information
     *
     * @param postId Job ID
     * @return result
     */
    int deletePostById(Long postId);

    /**
     * Batch delete job information
     *
     * @param postIds Job ID to be deleted
     * @return result
     */
    int deletePostByIds(Long[] postIds);

    /**
     * Add save position information
     *
     * @param bo job information
     * @return result
     */
    int insertPost(SysPostBo bo);

    /**
     * Modify and save job information
     *
     * @param bo job information
     * @return result
     */
    int updatePost(SysPostBo bo);
}
