package vn.udn.dut.cinema.port.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import vn.udn.dut.cinema.common.mybatis.core.mapper.BaseMapperPlus;
import vn.udn.dut.cinema.port.domain.Seat;
import vn.udn.dut.cinema.port.domain.vo.SeatVo;

/**
 * Seat mapper interface
 *
 * @author HoaLD
 * @date 2023-12-13
 */
public interface SeatMapper extends BaseMapperPlus<Seat, SeatVo> {

	/**
	 * 
	 * @param page
	 * @param queryWrapper
	 * @return
	 */
	Page<SeatVo> selectPageSeatList(@Param("page") Page<Seat> page,
			@Param(Constants.WRAPPER) Wrapper<Seat> queryWrapper);

	/**
	 * 
	 * @param queryWrapper
	 * @return
	 */
	List<SeatVo> selectSeatList(@Param(Constants.WRAPPER) Wrapper<Seat> queryWrapper);

}
