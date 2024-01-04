package vn.udn.dut.cinema.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import vn.udn.dut.cinema.common.mybatis.annotation.DataColumn;
import vn.udn.dut.cinema.common.mybatis.annotation.DataPermission;
import vn.udn.dut.cinema.common.mybatis.core.mapper.BaseMapperPlus;
import vn.udn.dut.cinema.system.domain.SysRole;
import vn.udn.dut.cinema.system.domain.vo.SysRoleVo;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * role table data layer
 *
 * @author HoaLD
 */
public interface SysRoleMapper extends BaseMapperPlus<SysRole, SysRoleVo> {

    Page<SysRoleVo> selectPageRoleList(@Param("page") Page<SysRole> page, @Param(Constants.WRAPPER) Wrapper<SysRole> queryWrapper);

    /**
     * Query role data by pagination according to conditions
     *
     * @param queryWrapper Query conditions
     * @return Character data collection information
     */
    @DataPermission({
        @DataColumn(key = "deptName", value = "d.dept_id")
    })
    List<SysRoleVo> selectRoleList(@Param(Constants.WRAPPER) Wrapper<SysRole> queryWrapper);

    @DataPermission({
        @DataColumn(key = "deptName", value = "d.dept_id")
    })
    SysRoleVo selectRoleById(Long roleId);

    /**
     * Query roles based on user ID
     *
     * @param userId User ID
     * @return role list
     */
    List<SysRoleVo> selectRolePermissionByUserId(Long userId);


    /**
     * Get the role selection box list according to the user ID
     *
     * @param userId User ID
     * @return Selected role ID list
     */
    List<Long> selectRoleListByUserId(Long userId);

    /**
     * Query roles based on user ID
     *
     * @param userName username
     * @return role list
     */
    List<SysRoleVo> selectRolesByUserName(String userName);

}
