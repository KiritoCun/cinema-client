package vn.udn.dut.cinema.port.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import vn.udn.dut.cinema.common.mybatis.core.mapper.BaseMapperPlus;
import vn.udn.dut.cinema.port.domain.BookingDetail;
import vn.udn.dut.cinema.port.domain.vo.BookingDetailVo;

/**
 * BookingDetail mapper interface
 *
 * @author HoaLD
 * @date 2023-12-13
 */
public interface BookingDetailMapper extends BaseMapperPlus<BookingDetail, BookingDetailVo> {

	/**
	 * 
	 * @param page
	 * @param queryWrapper
	 * @return
	 */
	Page<BookingDetailVo> selectPageBookingDetailList(@Param("page") Page<BookingDetail> page,
			@Param(Constants.WRAPPER) Wrapper<BookingDetail> queryWrapper);

	/**
	 * 
	 * @param queryWrapper
	 * @return
	 */
	List<BookingDetailVo> selectBookingDetailList(@Param(Constants.WRAPPER) Wrapper<BookingDetail> queryWrapper);

}
