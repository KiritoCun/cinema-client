package vn.udn.dut.cinema.system.service.impl;

import cn.dev33.satoken.secure.BCrypt;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import vn.udn.dut.cinema.common.core.constant.CacheNames;
import vn.udn.dut.cinema.common.core.constant.Constants;
import vn.udn.dut.cinema.common.core.constant.TenantConstants;
import vn.udn.dut.cinema.common.core.exception.ServiceException;
import vn.udn.dut.cinema.common.core.utils.MapstructUtils;
import vn.udn.dut.cinema.common.core.utils.SpringUtils;
import vn.udn.dut.cinema.common.core.utils.StringUtils;
import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.system.domain.*;
import vn.udn.dut.cinema.system.domain.bo.SysTenantBo;
import vn.udn.dut.cinema.system.domain.vo.SysTenantVo;
import vn.udn.dut.cinema.system.mapper.*;
import vn.udn.dut.cinema.system.service.ISysTenantService;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Tenant Service business layer processing
 *
 * @author HoaLD
 */
@RequiredArgsConstructor
@Service
public class SysTenantServiceImpl implements ISysTenantService {

    private final SysTenantMapper baseMapper;
    private final SysTenantPackageMapper tenantPackageMapper;
    private final SysUserMapper userMapper;
    private final SysDeptMapper deptMapper;
    private final SysRoleMapper roleMapper;
    private final SysRoleMenuMapper roleMenuMapper;
    private final SysRoleDeptMapper roleDeptMapper;
    private final SysUserRoleMapper userRoleMapper;
    private final SysDictTypeMapper dictTypeMapper;
    private final SysDictDataMapper dictDataMapper;
    private final SysConfigMapper configMapper;

    /**
     * query tenant
     */
    @Override
    public SysTenantVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

    /**
     * Query tenants based on tenant ID
     */
    @Cacheable(cacheNames = CacheNames.SYS_TENANT, key = "#tenantId")
    @Override
    public SysTenantVo queryByTenantId(String tenantId) {
        return baseMapper.selectVoOne(new LambdaQueryWrapper<SysTenant>().eq(SysTenant::getTenantId, tenantId));
    }

    /**
     * Query the list of tenants
     */
    @Override
    public TableDataInfo<SysTenantVo> queryPageList(SysTenantBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<SysTenant> lqw = buildQueryWrapper(bo);
        Page<SysTenantVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * Query the list of tenants
     */
    @Override
    public List<SysTenantVo> queryList(SysTenantBo bo) {
        LambdaQueryWrapper<SysTenant> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<SysTenant> buildQueryWrapper(SysTenantBo bo) {
        LambdaQueryWrapper<SysTenant> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getTenantId()), SysTenant::getTenantId, bo.getTenantId());
        lqw.like(StringUtils.isNotBlank(bo.getContactUserName()), SysTenant::getContactUserName, bo.getContactUserName());
        lqw.eq(StringUtils.isNotBlank(bo.getContactPhone()), SysTenant::getContactPhone, bo.getContactPhone());
        lqw.like(StringUtils.isNotBlank(bo.getCompanyName()), SysTenant::getCompanyName, bo.getCompanyName());
        lqw.eq(StringUtils.isNotBlank(bo.getLicenseNumber()), SysTenant::getLicenseNumber, bo.getLicenseNumber());
        lqw.eq(StringUtils.isNotBlank(bo.getAddress()), SysTenant::getAddress, bo.getAddress());
        lqw.eq(StringUtils.isNotBlank(bo.getIntro()), SysTenant::getIntro, bo.getIntro());
        lqw.like(StringUtils.isNotBlank(bo.getDomain()), SysTenant::getDomain, bo.getDomain());
        lqw.eq(bo.getPackageId() != null, SysTenant::getPackageId, bo.getPackageId());
        lqw.eq(bo.getExpireTime() != null, SysTenant::getExpireTime, bo.getExpireTime());
        lqw.eq(bo.getAccountCount() != null, SysTenant::getAccountCount, bo.getAccountCount());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), SysTenant::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * New tenant
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean insertByBo(SysTenantBo bo) {
        SysTenant add = MapstructUtils.convert(bo, SysTenant.class);

        // Get all tenant numbers
        List<String> tenantIds = baseMapper.selectObjs(
            new LambdaQueryWrapper<SysTenant>().select(SysTenant::getTenantId), Convert::toStr);
        String tenantId = generateTenantId(tenantIds);
        add.setTenantId(tenantId);
        boolean flag = baseMapper.insert(add) > 0;
        if (!flag) {
            throw new ServiceException("Failed to create tenant");
        }
        bo.setId(add.getId());

        // Create roles based on packages
        Long roleId = createTenantRole(tenantId, bo.getPackageId());

        // Create department: company name is department name
        SysDept dept = new SysDept();
        dept.setTenantId(tenantId);
        dept.setDeptName(bo.getCompanyName());
        dept.setLeader(bo.getUsername());
        dept.setParentId(Constants.TOP_PARENT_ID);
        dept.setAncestors(Constants.TOP_PARENT_ID.toString());
        deptMapper.insert(dept);
        Long deptId = dept.getDeptId();

        // Role and Department Association Table
        SysRoleDept roleDept = new SysRoleDept();
        roleDept.setRoleId(roleId);
        roleDept.setDeptId(deptId);
        roleDeptMapper.insert(roleDept);

        // create system user
        SysUser user = new SysUser();
        user.setTenantId(tenantId);
        user.setUserName(bo.getUsername());
        user.setNickName(bo.getUsername());
        user.setPassword(BCrypt.hashpw(bo.getPassword()));
        user.setDeptId(deptId);
        userMapper.insert(user);

        // User and Role Association Table
        SysUserRole userRole = new SysUserRole();
        userRole.setUserId(user.getUserId());
        userRole.setRoleId(roleId);
        userRoleMapper.insert(userRole);

        String defaultTenantId = TenantConstants.DEFAULT_TENANT_ID;
        List<SysDictType> dictTypeList = dictTypeMapper.selectList(
            new LambdaQueryWrapper<SysDictType>().eq(SysDictType::getTenantId, defaultTenantId));
        List<SysDictData> dictDataList = dictDataMapper.selectList(
            new LambdaQueryWrapper<SysDictData>().eq(SysDictData::getTenantId, defaultTenantId));
        for (SysDictType dictType : dictTypeList) {
            dictType.setDictId(null);
            dictType.setTenantId(tenantId);
        }
        for (SysDictData dictData : dictDataList) {
            dictData.setDictCode(null);
            dictData.setTenantId(tenantId);
        }
        dictTypeMapper.insertBatch(dictTypeList);
        dictDataMapper.insertBatch(dictDataList);

        List<SysConfig> sysConfigList = configMapper.selectList(
            new LambdaQueryWrapper<SysConfig>().eq(SysConfig::getTenantId, defaultTenantId));
        for (SysConfig config : sysConfigList) {
            config.setConfigId(null);
            config.setTenantId(tenantId);
        }
        configMapper.insertBatch(sysConfigList);
        return true;
    }

    /**
     * Generate tenant id
     *
     * @param tenantIds Existing tenant id list
     * @return tenant id
     */
    private String generateTenantId(List<String> tenantIds) {
        // Randomly generate 6 digits
        String numbers = RandomUtil.randomNumbers(6);
        // Determine whether it exists, and regenerate if it exists
        if (tenantIds.contains(numbers)) {
            generateTenantId(tenantIds);
        }
        return numbers;
    }

    /**
     * Create a tenant role from the tenant menu
     *
     * @param tenantId  tenant number
     * @param packageId tenant package id
     * @return role id
     */
    private Long createTenantRole(String tenantId, Long packageId) {
        // Get Tenant Package
        SysTenantPackage tenantPackage = tenantPackageMapper.selectById(packageId);
        if (ObjectUtil.isNull(tenantPackage)) {
            throw new ServiceException("Package does not exist");
        }
        // Get package menu id
        List<Long> menuIds = StringUtils.splitTo(tenantPackage.getMenuIds(), Convert::toLong);

        // Creating a Role
        SysRole role = new SysRole();
        role.setTenantId(tenantId);
        role.setRoleName(TenantConstants.TENANT_ADMIN_ROLE_NAME);
        role.setRoleKey(TenantConstants.TENANT_ADMIN_ROLE_KEY);
        role.setRoleSort(1);
        role.setStatus(TenantConstants.NORMAL);
        roleMapper.insert(role);
        Long roleId = role.getRoleId();

        // Create role menu
        List<SysRoleMenu> roleMenus = new ArrayList<>(menuIds.size());
        menuIds.forEach(menuId -> {
            SysRoleMenu roleMenu = new SysRoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenus.add(roleMenu);
        });
        roleMenuMapper.insertBatch(roleMenus);

        return roleId;
    }

    /**
     * modify tenant
     */
    @CacheEvict(cacheNames = CacheNames.SYS_TENANT, key = "#bo.tenantId")
    @Override
    public Boolean updateByBo(SysTenantBo bo) {
        SysTenant tenant = MapstructUtils.convert(bo, SysTenant.class);
        tenant.setTenantId(null);
        tenant.setPackageId(null);
        return baseMapper.updateById(tenant) > 0;
    }

    /**
     * Modify tenant status
     *
     * @param bo tenant information
     * @return result
     */
    @CacheEvict(cacheNames = CacheNames.SYS_TENANT, key = "#bo.tenantId")
    @Override
    public int updateTenantStatus(SysTenantBo bo) {
        SysTenant tenant = MapstructUtils.convert(bo, SysTenant.class);
        return baseMapper.updateById(tenant);
    }

    /**
     * Check if the tenant allows the operation
     *
     * @param tenantId tenant ID
     */
    @Override
    public void checkTenantAllowed(String tenantId) {
        if (ObjectUtil.isNotNull(tenantId) && TenantConstants.DEFAULT_TENANT_ID.equals(tenantId)) {
            throw new ServiceException("Operation admin tenant not allowed");
        }
    }

    /**
     * Delete tenants in batches
     */
    @CacheEvict(cacheNames = CacheNames.SYS_TENANT, allEntries = true)
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            // Do some business verification to determine whether verification is required
            if (ids.contains(TenantConstants.SUPER_ADMIN_ID)) {
                throw new ServiceException("Overmanagement tenants cannot be deleted");
            }
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    /**
     * Check if the business name is unique
     */
    @Override
    public boolean checkCompanyNameUnique(SysTenantBo bo) {
        boolean exist = baseMapper.exists(new LambdaQueryWrapper<SysTenant>()
            .eq(SysTenant::getCompanyName, bo.getCompanyName())
            .ne(ObjectUtil.isNotNull(bo.getTenantId()), SysTenant::getTenantId, bo.getTenantId()));
        return !exist;
    }

    /**
     * Check account balance
     */
    @Override
    public boolean checkAccountBalance(String tenantId) {
        SysTenantVo tenant = SpringUtils.getAopProxy(this).queryByTenantId(tenantId);
        // If the balance is -1, it means unlimited
        if (tenant.getAccountCount() == -1) {
            return true;
        }
        Long userNumber = userMapper.selectCount(new LambdaQueryWrapper<>());
        // If the balance is greater than 0, it means that there are still available places
        return tenant.getAccountCount() - userNumber > 0;
    }

    /**
     * Check validity period
     */
    @Override
    public boolean checkExpireTime(String tenantId) {
        SysTenantVo tenant = SpringUtils.getAopProxy(this).queryByTenantId(tenantId);
        // If no expiration time is set, it means unlimited
        if (ObjectUtil.isNull(tenant.getExpireTime())) {
            return true;
        }
        // Pass if the current time is before the expiration time
        return new Date().before(tenant.getExpireTime());
    }

    /**
     * Synchronize Tenant Packages
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean syncTenantPackage(String tenantId, String packageId) {
        SysTenantPackage tenantPackage = tenantPackageMapper.selectById(packageId);
        List<SysRole> roles = roleMapper.selectList(
            new LambdaQueryWrapper<SysRole>().eq(SysRole::getTenantId, tenantId));
        List<Long> roleIds = new ArrayList<>(roles.size() - 1);
        List<Long> menuIds = StringUtils.splitTo(tenantPackage.getMenuIds(), Convert::toLong);
        roles.forEach(item -> {
            if (TenantConstants.TENANT_ADMIN_ROLE_KEY.equals(item.getRoleKey())) {
                List<SysRoleMenu> roleMenus = new ArrayList<>(menuIds.size());
                menuIds.forEach(menuId -> {
                    SysRoleMenu roleMenu = new SysRoleMenu();
                    roleMenu.setRoleId(item.getRoleId());
                    roleMenu.setMenuId(menuId);
                    roleMenus.add(roleMenu);
                });
                roleMenuMapper.delete(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, item.getRoleId()));
                roleMenuMapper.insertBatch(roleMenus);
            } else {
                roleIds.add(item.getRoleId());
            }
        });
        if (!roleIds.isEmpty()) {
            roleMenuMapper.delete(
                new LambdaQueryWrapper<SysRoleMenu>().in(SysRoleMenu::getRoleId, roleIds).notIn(!menuIds.isEmpty(), SysRoleMenu::getMenuId, menuIds));
        }
        return true;
    }
}
