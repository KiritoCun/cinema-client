package vn.udn.dut.cinema.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import lombok.RequiredArgsConstructor;
import vn.udn.dut.cinema.common.core.utils.StreamUtils;
import vn.udn.dut.cinema.common.mybatis.helper.DataBaseHelper;
import vn.udn.dut.cinema.system.domain.SysDept;
import vn.udn.dut.cinema.system.domain.SysRoleDept;
import vn.udn.dut.cinema.system.mapper.SysDeptMapper;
import vn.udn.dut.cinema.system.mapper.SysRoleDeptMapper;
import vn.udn.dut.cinema.system.service.ISysDataScopeService;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Data permission implementation
 * <p>
 * Note: This Service is not allowed to call the method marked `data permission` annotation
 * For example: deptMapper.selectList This selectList method is annotated with `data permission` annotation, and there will be a problem of circular parsing
 *
 * @author HoaLD
 */
@RequiredArgsConstructor
@Service("sdss")
public class SysDataScopeServiceImpl implements ISysDataScopeService {

    private final SysRoleDeptMapper roleDeptMapper;
    private final SysDeptMapper deptMapper;

    @Override
    public String getRoleCustom(Long roleId) {
        List<SysRoleDept> list = roleDeptMapper.selectList(
            new LambdaQueryWrapper<SysRoleDept>()
                .select(SysRoleDept::getDeptId)
                .eq(SysRoleDept::getRoleId, roleId));
        if (CollUtil.isNotEmpty(list)) {
            return StreamUtils.join(list, rd -> Convert.toStr(rd.getDeptId()));
        }
        return null;
    }

    @Override
    public String getDeptAndChild(Long deptId) {
        List<SysDept> deptList = deptMapper.selectList(new LambdaQueryWrapper<SysDept>()
            .select(SysDept::getDeptId)
            .apply(DataBaseHelper.findInSet(deptId, "ancestors")));
        List<Long> ids = StreamUtils.toList(deptList, SysDept::getDeptId);
        ids.add(deptId);
        List<SysDept> list = deptMapper.selectList(new LambdaQueryWrapper<SysDept>()
            .select(SysDept::getDeptId)
            .in(SysDept::getDeptId, ids));
        if (CollUtil.isNotEmpty(list)) {
            return StreamUtils.join(list, d -> Convert.toStr(d.getDeptId()));
        }
        return null;
    }

}
