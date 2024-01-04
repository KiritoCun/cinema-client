package vn.udn.dut.cinema.system.mapper;

import vn.udn.dut.cinema.common.mybatis.core.mapper.BaseMapperPlus;
import vn.udn.dut.cinema.system.domain.SysPost;
import vn.udn.dut.cinema.system.domain.vo.SysPostVo;

import java.util.List;

/**
 * job information data layer
 *
 * @author HoaLD
 */
public interface SysPostMapper extends BaseMapperPlus<SysPost, SysPostVo> {

    /**
     * Obtain a list of job selection boxes based on user ID
     *
     * @param userId User ID
     * @return Selected post ID list
     */
    List<Long> selectPostListByUserId(Long userId);

    /**
     * Query the post group to which the user belongs
     *
     * @param userName username
     * @return result
     */
    List<SysPostVo> selectPostsByUserName(String userName);

}
