package vn.udn.dut.cinema.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import vn.udn.dut.cinema.common.mybatis.core.mapper.BaseMapperPlus;
import vn.udn.dut.cinema.system.domain.SysDataHistory;
import vn.udn.dut.cinema.system.domain.vo.SysDataHistoryVo;

/**
 * System data history mapper interface
 *
 * @author HoaLD
 * @date 2023-08-23
 */
public interface SysDataHistoryMapper extends BaseMapperPlus<SysDataHistory, SysDataHistoryVo> {

	Page<SysDataHistoryVo> selectPageDataHistoryList(@Param("page") Page<SysDataHistory> page, @Param(Constants.WRAPPER) Wrapper<SysDataHistory> queryWrapper);

	List<SysDataHistoryVo> selectDataHistoryList(@Param(Constants.WRAPPER) Wrapper<SysDataHistory> queryWrapper);

	SysDataHistoryVo selectDataHistoryById(Long id);
}
