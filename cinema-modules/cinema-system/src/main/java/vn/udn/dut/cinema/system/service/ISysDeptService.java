package vn.udn.dut.cinema.system.service;

import cn.hutool.core.lang.tree.Tree;
import vn.udn.dut.cinema.system.domain.SysDept;
import vn.udn.dut.cinema.system.domain.bo.SysDeptBo;
import vn.udn.dut.cinema.system.domain.vo.SysDeptVo;

import java.util.List;

/**
 * Department Management Service Layer
 *
 * @author HoaLD
 */
public interface ISysDeptService {
    /**
     * Query department management data
     *
     * @param dept department information
     * @return Collection of departmental information
     */
    List<SysDeptVo> selectDeptList(SysDeptBo dept);

    /**
     * Query department tree structure information
     *
     * @param dept department information
     * @return Department Tree Information Collection
     */
    List<Tree<Long>> selectDeptTreeList(SysDeptBo dept);

    /**
     * Build the drop-down tree structure required by the front end
     *
     * @param depts department list
     * @return drop down tree structure list
     */
    List<Tree<Long>> buildDeptTreeSelect(List<SysDept> depts);

    /**
     * Query department tree information based on role ID
     *
     * @param roleId Role ID
     * @return Selected department list
     */
    List<Long> selectDeptListByRoleId(Long roleId);

    /**
     * Query information by department ID
     *
     * @param deptId Department ID
     * @return department information
     */
    SysDeptVo selectDeptById(Long deptId);

    /**
     * Query the number of all sub-departments according to ID (normal state)
     *
     * @param deptId Department ID
     * @return Number of sub-departments
     */
    long selectNormalChildrenDeptById(Long deptId);

    /**
     * Whether there is a department child node
     *
     * @param deptId Department ID
     * @return result
     */
    boolean hasChildByDeptId(Long deptId);

    /**
     * Query whether there are users in the department
     *
     * @param deptId Department ID
     * @return result true exists false does not exist
     */
    boolean checkDeptExistUser(Long deptId);

    /**
     * Check if the department name is unique
     *
     * @param dept department information
     * @return result
     */
    boolean checkDeptNameUnique(SysDeptBo dept);

    /**
     * Verify whether the department has data permissions
     *
     * @param deptId 部门id
     */
    void checkDeptDataScope(Long deptId);

    /**
     * Add save department information
     *
     * @param bo department information
     * @return result
     */
    int insertDept(SysDeptBo bo);

    /**
     * Modify and save department information
     *
     * @param bo department information
     * @return result
     */
    int updateDept(SysDeptBo bo);

    /**
     * Delete department management information
     *
     * @param deptId Department ID
     * @return result
     */
    int deleteDeptById(Long deptId);
}
