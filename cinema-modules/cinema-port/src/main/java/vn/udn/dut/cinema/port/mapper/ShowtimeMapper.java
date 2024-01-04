package vn.udn.dut.cinema.port.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import vn.udn.dut.cinema.common.mybatis.core.mapper.BaseMapperPlus;
import vn.udn.dut.cinema.port.domain.Showtime;
import vn.udn.dut.cinema.port.domain.vo.ShowtimeVo;

/**
 * Showtime mapper interface
 *
 * @author HoaLD
 * @date 2023-12-21
 */
public interface ShowtimeMapper extends BaseMapperPlus<Showtime, ShowtimeVo> {

	/**
	 * 
	 * @param page
	 * @param queryWrapper
	 * @return
	 */
	Page<ShowtimeVo> selectPageShowtimeList(@Param("page") Page<Showtime> page,
			@Param(Constants.WRAPPER) Wrapper<Showtime> queryWrapper);

	/**
	 * 
	 * @param queryWrapper
	 * @return
	 */
	List<ShowtimeVo> selectShowtimeList(@Param(Constants.WRAPPER) Wrapper<Showtime> queryWrapper);

}
