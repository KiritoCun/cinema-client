package vn.udn.dut.cinema.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;

import vn.udn.dut.cinema.common.mybatis.annotation.DataColumn;
import vn.udn.dut.cinema.common.mybatis.annotation.DataPermission;
import vn.udn.dut.cinema.common.mybatis.core.mapper.BaseMapperPlus;
import vn.udn.dut.cinema.system.domain.SysDept;
import vn.udn.dut.cinema.system.domain.vo.SysDeptVo;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Department Management Data Layer
 *
 * @author HoaLD
 */
public interface SysDeptMapper extends BaseMapperPlus<SysDept, SysDeptVo> {

    /**
     * Query department management data
     *
     * @param queryWrapper Query conditions
     * @return Collection of departmental information
     */
    @DataPermission({
        @DataColumn(key = "deptName", value = "dept_id")
    })
    List<SysDeptVo> selectDeptList(@Param(Constants.WRAPPER) Wrapper<SysDept> queryWrapper);

    @DataPermission({
        @DataColumn(key = "deptName", value = "dept_id")
    })
    SysDeptVo selectDeptById(Long deptId);

    /**
     * Query department tree information based on role ID
     *
     * @param roleId            character ID
     * @param deptCheckStrictly Whether the selection items of the department tree are displayed in association
     * @return Selected department list
     */
    List<Long> selectDeptListByRoleId(@Param("roleId") Long roleId, @Param("deptCheckStrictly") boolean deptCheckStrictly);

}
