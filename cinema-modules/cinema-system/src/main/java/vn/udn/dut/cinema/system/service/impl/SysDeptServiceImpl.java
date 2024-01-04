package vn.udn.dut.cinema.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import lombok.RequiredArgsConstructor;
import vn.udn.dut.cinema.common.core.constant.CacheNames;
import vn.udn.dut.cinema.common.core.constant.UserConstants;
import vn.udn.dut.cinema.common.core.exception.ServiceException;
import vn.udn.dut.cinema.common.core.service.DeptService;
import vn.udn.dut.cinema.common.core.utils.MapstructUtils;
import vn.udn.dut.cinema.common.core.utils.SpringUtils;
import vn.udn.dut.cinema.common.core.utils.StringUtils;
import vn.udn.dut.cinema.common.core.utils.TreeBuildUtils;
import vn.udn.dut.cinema.common.mybatis.helper.DataBaseHelper;
import vn.udn.dut.cinema.common.redis.utils.CacheUtils;
import vn.udn.dut.cinema.common.satoken.utils.LoginHelper;
import vn.udn.dut.cinema.system.domain.SysDept;
import vn.udn.dut.cinema.system.domain.SysRole;
import vn.udn.dut.cinema.system.domain.SysUser;
import vn.udn.dut.cinema.system.domain.bo.SysDeptBo;
import vn.udn.dut.cinema.system.domain.vo.SysDeptVo;
import vn.udn.dut.cinema.system.mapper.SysDeptMapper;
import vn.udn.dut.cinema.system.mapper.SysRoleMapper;
import vn.udn.dut.cinema.system.mapper.SysUserMapper;
import vn.udn.dut.cinema.system.service.ISysDeptService;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Department Management Service Realization
 *
 * @author HoaLD
 */
@RequiredArgsConstructor
@Service
public class SysDeptServiceImpl implements ISysDeptService, DeptService {

    private final SysDeptMapper baseMapper;
    private final SysRoleMapper roleMapper;
    private final SysUserMapper userMapper;

    /**
     * Query department management data
     *
     * @param dept department information
     * @return Collection of departmental information
     */
    @Override
    public List<SysDeptVo> selectDeptList(SysDeptBo dept) {
        LambdaQueryWrapper<SysDept> lqw = buildQueryWrapper(dept);
        return baseMapper.selectDeptList(lqw);
    }

    /**
     * Query department tree structure information
     *
     * @param bo department information
     * @return Department Tree Information Collection
     */
    @Override
    public List<Tree<Long>> selectDeptTreeList(SysDeptBo bo) {
        LambdaQueryWrapper<SysDept> lqw = buildQueryWrapper(bo);
        List<SysDept> depts = baseMapper.selectList(lqw);
        return buildDeptTreeSelect(depts);
    }

    private LambdaQueryWrapper<SysDept> buildQueryWrapper(SysDeptBo bo) {
        LambdaQueryWrapper<SysDept> lqw = Wrappers.lambdaQuery();
        lqw.eq(SysDept::getDelFlag, "0");
        lqw.eq(ObjectUtil.isNotNull(bo.getDeptId()), SysDept::getDeptId, bo.getDeptId());
        lqw.eq(ObjectUtil.isNotNull(bo.getParentId()), SysDept::getParentId, bo.getParentId());
        lqw.like(StringUtils.isNotBlank(bo.getDeptName()), SysDept::getDeptName, bo.getDeptName());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), SysDept::getStatus, bo.getStatus());
        lqw.orderByAsc(SysDept::getParentId);
        lqw.orderByAsc(SysDept::getOrderNum);
        return lqw;
    }

    /**
     * Build the drop-down tree structure required by the front end
     *
     * @param depts department list
     * @return drop down tree structure list
     */
    @Override
    public List<Tree<Long>> buildDeptTreeSelect(List<SysDept> depts) {
        if (CollUtil.isEmpty(depts)) {
            return CollUtil.newArrayList();
        }
        return TreeBuildUtils.build(depts, (dept, tree) ->
            tree.setId(dept.getDeptId())
                .setParentId(dept.getParentId())
                .setName(dept.getDeptName())
                .setWeight(dept.getOrderNum()));
    }

    /**
     * Query department tree information based on role ID
     *
     * @param roleId Role ID
     * @return Selected department list
     */
    @Override
    public List<Long> selectDeptListByRoleId(Long roleId) {
        SysRole role = roleMapper.selectById(roleId);
        return baseMapper.selectDeptListByRoleId(roleId, role.getDeptCheckStrictly());
    }

    /**
     * Query information by department ID
     *
     * @param deptId Department ID
     * @return department information
     */
    @Cacheable(cacheNames = CacheNames.SYS_DEPT, key = "#deptId")
    @Override
    public SysDeptVo selectDeptById(Long deptId) {
        SysDeptVo dept = baseMapper.selectVoById(deptId);
        if (ObjectUtil.isNull(dept)) {
            return null;
        }
        SysDeptVo parentDept = baseMapper.selectVoOne(new LambdaQueryWrapper<SysDept>()
            .select(SysDept::getDeptName).eq(SysDept::getDeptId, dept.getParentId()));
        dept.setParentName(ObjectUtil.isNotNull(parentDept) ? parentDept.getDeptName() : null);
        return dept;
    }

    /**
     * Query department name by department ID
     *
     * @param deptIds Department ID strings separated by commas
     * @return Department names separated by commas
     */
    @Override
    public String selectDeptNameByIds(String deptIds) {
        List<String> list = new ArrayList<>();
        for (Long id : StringUtils.splitTo(deptIds, Convert::toLong)) {
            SysDeptVo vo = SpringUtils.getAopProxy(this).selectDeptById(id);
            if (ObjectUtil.isNotNull(vo)) {
                list.add(vo.getDeptName());
            }
        }
        return String.join(StringUtils.SEPARATOR, list);
    }

    /**
     * Query the number of all sub-departments according to ID (normal state)
     *
     * @param deptId Department ID
     * @return Number of sub-departments
     */
    @Override
    public long selectNormalChildrenDeptById(Long deptId) {
        return baseMapper.selectCount(new LambdaQueryWrapper<SysDept>()
            .eq(SysDept::getStatus, UserConstants.DEPT_NORMAL)
            .apply(DataBaseHelper.findInSet(deptId, "ancestors")));
    }

    /**
     * Whether there is a child node
     *
     * @param deptId Department ID
     * @return result
     */
    @Override
    public boolean hasChildByDeptId(Long deptId) {
        return baseMapper.exists(new LambdaQueryWrapper<SysDept>()
            .eq(SysDept::getParentId, deptId));
    }

    /**
     * Query whether there are users in the department
     *
     * @param deptId Department ID
     * @return result true exists false does not exist
     */
    @Override
    public boolean checkDeptExistUser(Long deptId) {
        return userMapper.exists(new LambdaQueryWrapper<SysUser>()
            .eq(SysUser::getDeptId, deptId));
    }

    /**
     * Check if the department name is unique
     *
     * @param dept department information
     * @return result
     */
    @Override
    public boolean checkDeptNameUnique(SysDeptBo dept) {
        boolean exist = baseMapper.exists(new LambdaQueryWrapper<SysDept>()
            .eq(SysDept::getDeptName, dept.getDeptName())
            .eq(SysDept::getParentId, dept.getParentId())
            .ne(ObjectUtil.isNotNull(dept.getDeptId()), SysDept::getDeptId, dept.getDeptId()));
        return !exist;
    }

    /**
     * Verify whether the department has data permissions
     *
     * @param deptId department id
     */
    @Override
    public void checkDeptDataScope(Long deptId) {
        if (ObjectUtil.isNull(deptId)) {
            return;
        }
        if (LoginHelper.isSuperAdmin()) {
            return;
        }
        SysDeptVo dept = baseMapper.selectDeptById(deptId);
        if (ObjectUtil.isNull(dept)) {
            throw new ServiceException("No access to departmental data!");
        }
    }

    /**
     * Add save department information
     *
     * @param bo department information
     * @return result
     */
    @Override
    public int insertDept(SysDeptBo bo) {
        SysDept info = baseMapper.selectById(bo.getParentId());
        // If the parent node is not in a normal state, new child nodes are not allowed
        if (!UserConstants.DEPT_NORMAL.equals(info.getStatus())) {
            throw new ServiceException("Department disabled, no new additions allowed");
        }
        SysDept dept = MapstructUtils.convert(bo, SysDept.class);
        dept.setAncestors(info.getAncestors() + StringUtils.SEPARATOR + dept.getParentId());
        return baseMapper.insert(dept);
    }

    /**
     * Modify and save department information
     *
     * @param bo department information
     * @return result
     */
    @CacheEvict(cacheNames = CacheNames.SYS_DEPT, key = "#bo.deptId")
    @Override
    public int updateDept(SysDeptBo bo) {
        SysDept dept = MapstructUtils.convert(bo, SysDept.class);
        SysDept oldDept = baseMapper.selectById(dept.getDeptId());
        if (!oldDept.getParentId().equals(dept.getParentId())) {
            // If it is a new parent department, check whether it has the authority of the new parent department to avoid overreaching
            this.checkDeptDataScope(dept.getParentId());
            SysDept newParentDept = baseMapper.selectById(dept.getParentId());
            if (ObjectUtil.isNotNull(newParentDept) && ObjectUtil.isNotNull(oldDept)) {
                String newAncestors = newParentDept.getAncestors() + StringUtils.SEPARATOR + newParentDept.getDeptId();
                String oldAncestors = oldDept.getAncestors();
                dept.setAncestors(newAncestors);
                updateDeptChildren(dept.getDeptId(), newAncestors, oldAncestors);
            }
        }
        int result = baseMapper.updateById(dept);
        if (UserConstants.DEPT_NORMAL.equals(dept.getStatus()) && StringUtils.isNotEmpty(dept.getAncestors())
            && !StringUtils.equals(UserConstants.DEPT_NORMAL, dept.getAncestors())) {
            // If the department is enabled, enable all parent departments of the department
            updateParentDeptStatusNormal(dept);
        }
        return result;
    }

    /**
     * Modify the parent department status of this department
     *
     * @param dept current department
     */
    private void updateParentDeptStatusNormal(SysDept dept) {
        String ancestors = dept.getAncestors();
        Long[] deptIds = Convert.toLongArray(ancestors);
        baseMapper.update(null, new LambdaUpdateWrapper<SysDept>()
            .set(SysDept::getStatus, UserConstants.DEPT_NORMAL)
            .in(SysDept::getDeptId, Arrays.asList(deptIds)));
    }

    /**
     * Modify child element relationship
     *
     * @param deptId       Modified department ID
     * @param newAncestors new collection of parent ids
     * @param oldAncestors old collection of parent ids
     */
    private void updateDeptChildren(Long deptId, String newAncestors, String oldAncestors) {
        List<SysDept> children = baseMapper.selectList(new LambdaQueryWrapper<SysDept>()
            .apply(DataBaseHelper.findInSet(deptId, "ancestors")));
        List<SysDept> list = new ArrayList<>();
        for (SysDept child : children) {
            SysDept dept = new SysDept();
            dept.setDeptId(child.getDeptId());
            dept.setAncestors(child.getAncestors().replaceFirst(oldAncestors, newAncestors));
            list.add(dept);
        }
        if (CollUtil.isNotEmpty(list)) {
            if (baseMapper.updateBatchById(list)) {
                list.forEach(dept -> CacheUtils.evict(CacheNames.SYS_DEPT, dept.getDeptId()));
            }
        }
    }

    /**
     * Delete department management information
     *
     * @param deptId Department ID
     * @return result
     */
    @CacheEvict(cacheNames = CacheNames.SYS_DEPT, key = "#deptId")
    @Override
    public int deleteDeptById(Long deptId) {
        return baseMapper.deleteById(deptId);
    }

}
