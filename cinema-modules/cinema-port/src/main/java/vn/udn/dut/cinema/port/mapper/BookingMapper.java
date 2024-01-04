package vn.udn.dut.cinema.port.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import vn.udn.dut.cinema.common.mybatis.core.mapper.BaseMapperPlus;
import vn.udn.dut.cinema.port.domain.Booking;
import vn.udn.dut.cinema.port.domain.vo.BookingVo;

/**
 * Booking mapper interface
 *
 * @author HoaLD
 * @date 2023-12-13
 */
public interface BookingMapper extends BaseMapperPlus<Booking, BookingVo> {

	/**
	 * 
	 * @param page
	 * @param queryWrapper
	 * @return
	 */
	Page<BookingVo> selectPageBookingList(@Param("page") Page<Booking> page,
			@Param(Constants.WRAPPER) Wrapper<Booking> queryWrapper);

	/**
	 * 
	 * @param wrapper
	 * @return
	 */
	List<BookingVo> selectBookingList(@Param(Constants.WRAPPER) Wrapper<Booking> wrapper);

}
