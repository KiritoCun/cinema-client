package vn.udn.dut.cinema.system.service;

import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.system.domain.SysUserRole;
import vn.udn.dut.cinema.system.domain.bo.SysRoleBo;
import vn.udn.dut.cinema.system.domain.vo.SysRoleVo;

import java.util.List;
import java.util.Set;

/**
 * role business layer
 *
 * @author HoaLD
 */
public interface ISysRoleService {


    TableDataInfo<SysRoleVo> selectPageRoleList(SysRoleBo role, PageQuery pageQuery);

    /**
     * Query role data by pagination according to conditions
     *
     * @param role role information
     * @return Character data collection information
     */
    List<SysRoleVo> selectRoleList(SysRoleBo role);

    /**
     * Query role list based on user ID
     *
     * @param userId User ID
     * @return role list
     */
    List<SysRoleVo> selectRolesByUserId(Long userId, String systemType);

    /**
     * Query role permissions based on user ID
     *
     * @param userId User ID
     * @return menu information
     */
    Set<String> selectRolePermissionByUserId(Long userId);

    /**
     * query all roles
     *
     * @return role list
     */
    List<SysRoleVo> selectRoleAll(String systemType);

    /**
     * Get the role selection box list according to the user ID
     *
     * @param userId User ID
     * @return Selected role ID list
     */
    List<Long> selectRoleListByUserId(Long userId);

    /**
     * Query roles by role ID
     *
     * @param roleId Role ID
     * @return role object information
     */
    SysRoleVo selectRoleById(Long roleId);

    /**
     * Check if the role name is unique
     *
     * @param role role information
     * @return result
     */
    boolean checkRoleNameUnique(SysRoleBo role);

    /**
     * Check if the role permissions are unique
     *
     * @param role role information
     * @return result
     */
    boolean checkRoleKeyUnique(SysRoleBo role);

    /**
     * Check if the role allows the operation
     *
     * @param roleId Role ID
     */
    void checkRoleAllowed(Long roleId);

    /**
     * Check if the role has data permissions
     *
     * @param roleId role id
     */
    void checkRoleDataScope(Long roleId);

    /**
     * Query the number of roles used by the role ID
     *
     * @param roleId Role ID
     * @return result
     */
    long countUserRoleByRoleId(Long roleId);

    /**
     * Added save role information
     *
     * @param bo role information
     * @return result
     */
    int insertRole(SysRoleBo bo);

    /**
     * Modify and save role information
     *
     * @param bo role information
     * @return result
     */
    int updateRole(SysRoleBo bo);

    /**
     * Modify character status
     *
     * @param roleId Role ID
     * @param status character status
     * @return result
     */
    int updateRoleStatus(Long roleId, String status);

    /**
     * Modify data permission information
     *
     * @param bo role information
     * @return result
     */
    int authDataScope(SysRoleBo bo);

    /**
     * Delete a role by role ID
     *
     * @param roleId Role ID
     * @return result
     */
    int deleteRoleById(Long roleId);

    /**
     * Batch delete role information
     *
     * @param roleIds ID of the role to delete
     * @return result
     */
    int deleteRoleByIds(Long[] roleIds);

    /**
     * Deauthorize user roles
     *
     * @param userRole User and role association information
     * @return result
     */
    int deleteAuthUser(SysUserRole userRole);

    /**
     * Batch deauthorize user roles
     *
     * @param roleId  Role ID
     * @param userIds ID of the user data that needs to be deauthorized
     * @return result
     */
    int deleteAuthUsers(Long roleId, Long[] userIds);

    /**
     * Batch selection of authorized user roles
     *
     * @param roleId  Role ID
     * @param userIds ID of the user data to delete
     * @return result
     */
    int insertAuthUsers(Long roleId, Long[] userIds);

    void cleanOnlineUserByRole(Long roleId);
}
