package vn.udn.dut.cinema.port.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.RequiredArgsConstructor;
import vn.udn.dut.cinema.common.core.utils.MapstructUtils;
import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.port.domain.Showtime;
import vn.udn.dut.cinema.port.domain.bo.HallSeatBo;
import vn.udn.dut.cinema.port.domain.bo.SeatBo;
import vn.udn.dut.cinema.port.domain.bo.ShowtimeBo;
import vn.udn.dut.cinema.port.domain.vo.CinemaVo;
import vn.udn.dut.cinema.port.domain.vo.HallSeatVo;
import vn.udn.dut.cinema.port.domain.vo.HallVo;
import vn.udn.dut.cinema.port.domain.vo.MovieVo;
import vn.udn.dut.cinema.port.domain.vo.ShowtimeInfoVo;
import vn.udn.dut.cinema.port.domain.vo.ShowtimeVo;
import vn.udn.dut.cinema.port.mapper.ShowtimeMapper;
import vn.udn.dut.cinema.port.service.ICinemaService;
import vn.udn.dut.cinema.port.service.IHallSeatService;
import vn.udn.dut.cinema.port.service.IHallService;
import vn.udn.dut.cinema.port.service.IMovieService;
import vn.udn.dut.cinema.port.service.ISeatService;
import vn.udn.dut.cinema.port.service.IShowtimeService;

/**
 * ShowtimeService business layer processing
 *
 * @author HoaLD
 * @date 2023-08-23
 */
@RequiredArgsConstructor
@Service
public class ShowtimeServiceImpl implements IShowtimeService {

	private final ShowtimeMapper baseMapper;
	private final IHallService hallService;
	private final IHallSeatService hallSeatService;
	private final ISeatService seatService;
	private final IMovieService movieService;
	private final ICinemaService cinemaService;

	/**
	 * Query Showtime
	 */
	@Override
	public ShowtimeVo queryById(Long id) {
		return baseMapper.selectVoById(id);
	}

	/**
	 * Query Showtime list
	 */
	@Override
	public TableDataInfo<ShowtimeVo> queryPageList(ShowtimeBo bo, PageQuery pageQuery) {
		Page<ShowtimeVo> page = baseMapper.selectPageShowtimeList(pageQuery.build(), this.buildQueryWrapper(bo));
		return TableDataInfo.build(page);
	}

	/**
	 * Query Showtime list
	 */
	@Override
	public List<ShowtimeVo> queryList(ShowtimeBo bo) {
		return baseMapper.selectShowtimeList(this.buildQueryWrapper(bo));
	}

	private Wrapper<Showtime> buildQueryWrapper(ShowtimeBo bo) {
		QueryWrapper<Showtime> wrapper = Wrappers.query();
		wrapper.eq(bo.getCinemaId() != null, "s.cinema_id", bo.getCinemaId())
				.eq(bo.getHallId() != null, "s.hall_id", bo.getHallId())
				.eq(bo.getMovieId() != null, "s.movie_id", bo.getMovieId())
				.like(bo.getHallName() != null, "h.hall_name", bo.getHallName())
				.like(bo.getMovieName() != null, "m.title", bo.getMovieName())
				.eq(bo.getId() != null, "s.id", bo.getId());
		return wrapper;
	}

	/**
	 * Add Showtime
	 */
	@Override
	public Boolean insertByBo(ShowtimeBo bo) {
		Showtime add = MapstructUtils.convert(bo, Showtime.class);
		validEntityBeforeSave(add);
		add.setCreateTime(new Date());
		boolean flag = baseMapper.insert(add) > 0;
		if (flag) {
			bo.setId(add.getId());
		}
		return flag;
	}

	/**
	 * Edit Showtime
	 */
	@Override
	public Boolean updateByBo(ShowtimeBo bo) {
		Showtime update = MapstructUtils.convert(bo, Showtime.class);
		validEntityBeforeSave(update);
		return baseMapper.updateById(update) > 0;
	}

	/**
	 * Data verification before saving
	 */
	private void validEntityBeforeSave(Showtime entity) {
		// TODO Do some data validation, such as unique constraints
	}

	/**
	 * Batch delete Showtime
	 */
	@Override
	public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
		if (isValid) {
			// TODO Do some business verification to determine whether verification is
			// required
		}
		return baseMapper.deleteBatchIds(ids) > 0;
	}

	@Override
	public void releaseShowtimes(List<ShowtimeBo> showtimes) {
		for (ShowtimeBo showtime : showtimes) {
			MovieVo movie = movieService.queryById(showtime.getMovieId());
			// Tạo đối tượng Calendar từ đối tượng Date
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(showtime.getStartTime());
			calendar.add(Calendar.MINUTE, Integer.parseInt(movie.getDuration() + ""));
			// Lấy đối tượng Date mới sau khi cộng thêm số phút
			Date endTime = calendar.getTime();
			showtime.setEndTime(endTime);
			insertByBo(showtime);
			HallVo hall = hallService.queryById(showtime.getHallId());
			HallSeatBo hallSeatParam = new HallSeatBo();
			hallSeatParam.setHallId(hall.getId());
			List<HallSeatVo> hallSeatList = hallSeatService.queryList(hallSeatParam);
			for (HallSeatVo hallSeat : hallSeatList) {
				for (int i = 1; i <= hallSeat.getRowSeatNumber(); i++) {
					SeatBo seat = new SeatBo();
					seat.setSeatTypeId(hallSeat.getSeatTypeId());
					seat.setRowCode(hallSeat.getRowCode());
					seat.setColumnCode(i);
					seat.setShowtimeId(showtime.getId());
					seat.setHallId(hallSeat.getHallId());
					seat.setStatus("N");
					seatService.insertByBo(seat);
				}
			}
		}
	}

	@Override
	public List<ShowtimeInfoVo> fetchShowtimeInfoList(Long movieId) {
		ShowtimeBo showtimeParam = new ShowtimeBo();
		showtimeParam.setMovieId(movieId);
		List<ShowtimeVo> showtimeList = queryList(showtimeParam);
		Map<Long, List<ShowtimeVo>> showtimeMap = showtimeList.stream()
				.collect(Collectors.groupingBy(ShowtimeVo::getCinemaId));
		List<ShowtimeInfoVo> showtimeInfoVoList = new ArrayList<>();
		for (var entry : showtimeMap.entrySet()) {
			ShowtimeInfoVo showtimeInfoVo = new ShowtimeInfoVo();
			Long cinemaId = entry.getKey();
			CinemaVo cinema = cinemaService.queryById(cinemaId);
			showtimeInfoVo.setId(cinemaId);
			showtimeInfoVo.setCinema(cinema);
			showtimeInfoVo.setShowtimeList(entry.getValue());
			showtimeInfoVoList.add(showtimeInfoVo);
		}
		return showtimeInfoVoList;
	}

}
