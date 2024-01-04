package vn.udn.dut.cinema.system.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import vn.udn.dut.cinema.common.core.constant.UserConstants;
import vn.udn.dut.cinema.common.core.domain.model.LoginUser;
import vn.udn.dut.cinema.common.core.exception.ServiceException;
import vn.udn.dut.cinema.common.core.utils.MapstructUtils;
import vn.udn.dut.cinema.common.core.utils.StreamUtils;
import vn.udn.dut.cinema.common.core.utils.StringUtils;
import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.common.satoken.utils.LoginHelper;
import vn.udn.dut.cinema.system.domain.SysRole;
import vn.udn.dut.cinema.system.domain.SysRoleDept;
import vn.udn.dut.cinema.system.domain.SysRoleMenu;
import vn.udn.dut.cinema.system.domain.SysUserRole;
import vn.udn.dut.cinema.system.domain.bo.SysRoleBo;
import vn.udn.dut.cinema.system.domain.vo.SysRoleVo;
import vn.udn.dut.cinema.system.mapper.SysRoleDeptMapper;
import vn.udn.dut.cinema.system.mapper.SysRoleMapper;
import vn.udn.dut.cinema.system.mapper.SysRoleMenuMapper;
import vn.udn.dut.cinema.system.mapper.SysUserRoleMapper;
import vn.udn.dut.cinema.system.service.ISysRoleService;

/**
 * Role Business layer processing
 *
 * @author HoaLD
 */
@RequiredArgsConstructor
@Service
public class SysRoleServiceImpl implements ISysRoleService {

    private final SysRoleMapper baseMapper;
    private final SysRoleMenuMapper roleMenuMapper;
    private final SysUserRoleMapper userRoleMapper;
    private final SysRoleDeptMapper roleDeptMapper;

    @Override
    public TableDataInfo<SysRoleVo> selectPageRoleList(SysRoleBo role, PageQuery pageQuery) {
        Page<SysRoleVo> page = baseMapper.selectPageRoleList(pageQuery.build(), this.buildQueryWrapper(role));
        return TableDataInfo.build(page);
    }

    /**
     * Query role data by pagination according to conditions
     *
     * @param role role information
     * @return Character data collection information
     */
    @Override
    public List<SysRoleVo> selectRoleList(SysRoleBo role) {
        return baseMapper.selectRoleList(this.buildQueryWrapper(role));
    }

    private Wrapper<SysRole> buildQueryWrapper(SysRoleBo bo) {
        Map<String, Object> params = bo.getParams();
        QueryWrapper<SysRole> wrapper = Wrappers.query();
        wrapper.eq("r.del_flag", UserConstants.ROLE_NORMAL)
            .eq(ObjectUtil.isNotNull(bo.getRoleId()), "r.role_id", bo.getRoleId())
            .like(StringUtils.isNotBlank(bo.getRoleName()), "r.role_name", bo.getRoleName())
            .eq(StringUtils.isNotBlank(bo.getStatus()), "r.status", bo.getStatus())
            .eq(StringUtils.isNotBlank(bo.getSystemType()), "r.system_type", bo.getSystemType())
            .like(StringUtils.isNotBlank(bo.getRoleKey()), "r.role_key", bo.getRoleKey())
            .between(params.get("beginTime") != null && params.get("endTime") != null,
                "r.create_time", params.get("beginTime"), params.get("endTime"));
        return wrapper;
    }

    /**
     * Query roles based on user ID
     *
     * @param userId User ID
     * @return role list
     */
    @Override
    public List<SysRoleVo> selectRolesByUserId(Long userId, String systemType) {
        List<SysRoleVo> userRoles = baseMapper.selectRolePermissionByUserId(userId);
        List<SysRoleVo> roles = selectRoleAll(systemType);
        for (SysRoleVo role : roles) {
            for (SysRoleVo userRole : userRoles) {
                if (role.getRoleId().longValue() == userRole.getRoleId().longValue()) {
                    role.setFlag(true);
                    break;
                }
            }
        }
        return roles;
    }

    /**
     * Query permissions based on user ID
     *
     * @param userId User ID
     * @return menu information
     */
    @Override
    public Set<String> selectRolePermissionByUserId(Long userId) {
        List<SysRoleVo> perms = baseMapper.selectRolePermissionByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (SysRoleVo perm : perms) {
            if (ObjectUtil.isNotNull(perm)) {
                permsSet.addAll(StringUtils.splitList(perm.getRoleKey().trim()));
            }
        }
        return permsSet;
    }

    /**
     * query all roles
     *
     * @return role list
     */
    @Override
    public List<SysRoleVo> selectRoleAll(String systemType) {
    	SysRoleBo bo = new SysRoleBo();
    	bo.setSystemType(systemType);
        return this.selectRoleList(bo);
    }

    /**
     * Get the role selection box list according to the user ID
     *
     * @param userId User ID
     * @return Selected role ID list
     */
    @Override
    public List<Long> selectRoleListByUserId(Long userId) {
        return baseMapper.selectRoleListByUserId(userId);
    }

    /**
     * Query roles by role ID
     *
     * @param roleId Role ID
     * @return role object information
     */
    @Override
    public SysRoleVo selectRoleById(Long roleId) {
        return baseMapper.selectRoleById(roleId);
    }

    /**
     * Check if the role name is unique
     *
     * @param role role information
     * @return result
     */
    @Override
    public boolean checkRoleNameUnique(SysRoleBo role) {
        boolean exist = baseMapper.exists(new LambdaQueryWrapper<SysRole>()
            .eq(SysRole::getRoleName, role.getRoleName())
            .eq(SysRole::getSystemType, role.getSystemType())
            .ne(ObjectUtil.isNotNull(role.getRoleId()), SysRole::getRoleId, role.getRoleId()));
        return !exist;
    }

    /**
     * Check if the role permissions are unique
     *
     * @param role role information
     * @return result
     */
    @Override
    public boolean checkRoleKeyUnique(SysRoleBo role) {
        boolean exist = baseMapper.exists(new LambdaQueryWrapper<SysRole>()
            .eq(SysRole::getRoleKey, role.getRoleKey())
            .eq(SysRole::getSystemType, role.getSystemType())
            .ne(ObjectUtil.isNotNull(role.getRoleId()), SysRole::getRoleId, role.getRoleId()));
        return !exist;
    }

    /**
     * Check if the role allows the operation
     *
     * @param roleId Role ID
     */
    @Override
    public void checkRoleAllowed(Long roleId) {
        if (ObjectUtil.isNotNull(roleId) && LoginHelper.isSuperAdmin(roleId)) {
            throw new ServiceException("Super administrator role is not allowed");
        }
    }

    /**
     * Check if the role has data permissions
     *
     * @param roleId role id
     */
    @Override
    public void checkRoleDataScope(Long roleId) {
        if (ObjectUtil.isNull(roleId)) {
            return;
        }
        if (LoginHelper.isSuperAdmin()) {
            return;
        }
        List<SysRoleVo> roles = this.selectRoleList(new SysRoleBo(roleId));
        if (CollUtil.isEmpty(roles)) {
            throw new ServiceException("No access to permission data!");
        }

    }

    /**
     * Query the number of roles used by the role ID
     *
     * @param roleId Role ID
     * @return result
     */
    @Override
    public long countUserRoleByRoleId(Long roleId) {
        return userRoleMapper.selectCount(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getRoleId, roleId));
    }

    /**
     * Added save role information
     *
     * @param bo role information
     * @return result
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertRole(SysRoleBo bo) {
        SysRole role = MapstructUtils.convert(bo, SysRole.class);
        // Add role information
        baseMapper.insert(role);
        bo.setRoleId(role.getRoleId());
        return insertRoleMenu(bo);
    }

    /**
     * Modify and save role information
     *
     * @param bo role information
     * @return result
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateRole(SysRoleBo bo) {
        SysRole role = MapstructUtils.convert(bo, SysRole.class);
        // Modify role information
        baseMapper.updateById(role);
        // Delete role associated with menu
        roleMenuMapper.delete(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, role.getRoleId()));
        return insertRoleMenu(bo);
    }

    /**
     * Modify character status
     *
     * @param roleId Role ID
     * @param status character status
     * @return result
     */
    @Override
    public int updateRoleStatus(Long roleId, String status) {
        return baseMapper.update(null,
            new LambdaUpdateWrapper<SysRole>()
                .set(SysRole::getStatus, status)
                .eq(SysRole::getRoleId, roleId));
    }

    /**
     * Modify data permission information
     *
     * @param bo role information
     * @return result
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int authDataScope(SysRoleBo bo) {
        SysRole role = MapstructUtils.convert(bo, SysRole.class);
        // Modify role information
        baseMapper.updateById(role);
        // Delete the role associated with the department
        roleDeptMapper.delete(new LambdaQueryWrapper<SysRoleDept>().eq(SysRoleDept::getRoleId, role.getRoleId()));
        // Add role and department information (data permissions)
        return insertRoleDept(bo);
    }

    /**
     * Added role menu information
     *
     * @param role role object
     */
    private int insertRoleMenu(SysRoleBo role) {
        int rows = 1;
        // Added user and role management
        List<SysRoleMenu> list = new ArrayList<SysRoleMenu>();
        for (Long menuId : role.getMenuIds()) {
            SysRoleMenu rm = new SysRoleMenu();
            rm.setRoleId(role.getRoleId());
            rm.setMenuId(menuId);
            list.add(rm);
        }
        if (list.size() > 0) {
            rows = roleMenuMapper.insertBatch(list) ? list.size() : 0;
        }
        return rows;
    }

    /**
     * Add role department information (data permission)
     *
     * @param role role object
     */
    private int insertRoleDept(SysRoleBo role) {
        int rows = 1;
        // Added role and department (data authority) management
        List<SysRoleDept> list = new ArrayList<SysRoleDept>();
        for (Long deptId : role.getDeptIds()) {
            SysRoleDept rd = new SysRoleDept();
            rd.setRoleId(role.getRoleId());
            rd.setDeptId(deptId);
            list.add(rd);
        }
        if (list.size() > 0) {
            rows = roleDeptMapper.insertBatch(list) ? list.size() : 0;
        }
        return rows;
    }

    /**
     * Delete a role by role ID
     *
     * @param roleId Role ID
     * @return result
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteRoleById(Long roleId) {
        // Delete role associated with menu
        roleMenuMapper.delete(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, roleId));
        // Delete the role associated with the department
        roleDeptMapper.delete(new LambdaQueryWrapper<SysRoleDept>().eq(SysRoleDept::getRoleId, roleId));
        return baseMapper.deleteById(roleId);
    }

    /**
     * Batch delete role information
     *
     * @param roleIds ID of the role to delete
     * @return result
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteRoleByIds(Long[] roleIds) {
        for (Long roleId : roleIds) {
            checkRoleAllowed(roleId);
            checkRoleDataScope(roleId);
            SysRole role = baseMapper.selectById(roleId);
            if (countUserRoleByRoleId(roleId) > 0) {
                throw new ServiceException(String.format("%1$s has been allocated and cannot be deleted", role.getRoleName()));
            }
        }
        List<Long> ids = Arrays.asList(roleIds);
        // Delete role associated with menu
        roleMenuMapper.delete(new LambdaQueryWrapper<SysRoleMenu>().in(SysRoleMenu::getRoleId, ids));
        // Delete the role associated with the department
        roleDeptMapper.delete(new LambdaQueryWrapper<SysRoleDept>().in(SysRoleDept::getRoleId, ids));
        return baseMapper.deleteBatchIds(ids);
    }

    /**
     * Deauthorize user roles
     *
     * @param userRole User and role association information
     * @return result
     */
    @Override
    public int deleteAuthUser(SysUserRole userRole) {
        int rows = userRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>()
            .eq(SysUserRole::getRoleId, userRole.getRoleId())
            .eq(SysUserRole::getUserId, userRole.getUserId()));
        if (rows > 0) {
            cleanOnlineUserByRole(userRole.getRoleId());
        }
        return rows;
    }

    /**
     * Batch deauthorize user roles
     *
     * @param roleId  Role ID
     * @param userIds ID of the user data that needs to be deauthorized
     * @return result
     */
    @Override
    public int deleteAuthUsers(Long roleId, Long[] userIds) {
        int rows = userRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>()
            .eq(SysUserRole::getRoleId, roleId)
            .in(SysUserRole::getUserId, Arrays.asList(userIds)));
        if (rows > 0) {
            cleanOnlineUserByRole(roleId);
        }
        return rows;
    }

    /**
     * Batch selection of authorized user roles
     *
     * @param roleId  Role ID
     * @param userIds The user data ID that requires authorization
     * @return result
     */
    @Override
    public int insertAuthUsers(Long roleId, Long[] userIds) {
        // Added user and role management
        int rows = 1;
        List<SysUserRole> list = StreamUtils.toList(List.of(userIds), userId -> {
            SysUserRole ur = new SysUserRole();
            ur.setUserId(userId);
            ur.setRoleId(roleId);
            return ur;
        });
        if (CollUtil.isNotEmpty(list)) {
            rows = userRoleMapper.insertBatch(list) ? list.size() : 0;
        }
        if (rows > 0) {
            cleanOnlineUserByRole(roleId);
        }
        return rows;
    }

    @Override
    public void cleanOnlineUserByRole(Long roleId) {
        List<String> keys = StpUtil.searchTokenValue("", 0, -1, false);
        if (CollUtil.isEmpty(keys)) {
            return;
        }
        // The large number of online users associated with the role will cause redis to block and freeze. Operate with caution
        keys.parallelStream().forEach(key -> {
            String token = StringUtils.substringAfterLast(key, ":");
            // Skip if already expired
            if (StpUtil.stpLogic.getTokenActivityTimeoutByToken(token) < -1) {
                return;
            }
            LoginUser loginUser = LoginHelper.getLoginUser(token);
            if (loginUser.getRoles().stream().anyMatch(r -> r.getRoleId().equals(roleId))) {
                try {
                    StpUtil.logoutByTokenValue(token);
                } catch (NotLoginException ignored) {
                }
            }
        });
    }
}
