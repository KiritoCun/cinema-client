package vn.udn.dut.cinema.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import vn.udn.dut.cinema.common.core.constant.UserConstants;
import vn.udn.dut.cinema.common.mybatis.core.mapper.BaseMapperPlus;
import vn.udn.dut.cinema.system.domain.SysDictData;
import vn.udn.dut.cinema.system.domain.vo.ContainerSztpVo;
import vn.udn.dut.cinema.system.domain.vo.SysDictDataVo;

/**
 * Dictionary Table Data Layer
 *
 * @author HoaLD
 */
public interface SysDictDataMapper extends BaseMapperPlus<SysDictData, SysDictDataVo> {

    default List<SysDictDataVo> selectDictDataByType(String dictType) {
        return selectVoList(
            new LambdaQueryWrapper<SysDictData>()
                .eq(SysDictData::getStatus, UserConstants.DICT_NORMAL)
                .eq(SysDictData::getDictType, dictType)
                .orderByAsc(SysDictData::getDictSort));
    }
    
	Page<ContainerSztpVo> selectContainerSztpsByBo(@Param("page") Page<ContainerSztpVo> page,
			@Param(Constants.WRAPPER) Wrapper<ContainerSztpVo> queryWrapper);
}
