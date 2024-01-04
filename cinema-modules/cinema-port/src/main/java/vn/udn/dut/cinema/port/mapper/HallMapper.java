package vn.udn.dut.cinema.port.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import vn.udn.dut.cinema.common.mybatis.core.mapper.BaseMapperPlus;
import vn.udn.dut.cinema.port.domain.Hall;
import vn.udn.dut.cinema.port.domain.vo.HallVo;

/**
 * Hall mapper interface
 *
 * @author HoaLD
 * @date 2023-12-13
 */
public interface HallMapper extends BaseMapperPlus<Hall, HallVo> {

	/**
	 * 
	 * @param page
	 * @param queryWrapper
	 * @return
	 */
	Page<HallVo> selectPageHallList(@Param("page") Page<Hall> page,
			@Param(Constants.WRAPPER) Wrapper<Hall> queryWrapper);

	/**
	 * 
	 * @param queryWrapper
	 * @return
	 */
	List<HallVo> selectHallList(@Param(Constants.WRAPPER) Wrapper<Hall> queryWrapper);

}
