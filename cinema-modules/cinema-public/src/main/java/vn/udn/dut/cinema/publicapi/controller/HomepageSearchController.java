package vn.udn.dut.cinema.publicapi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import vn.udn.dut.cinema.common.core.domain.R;
import vn.udn.dut.cinema.common.web.core.BaseController;
import vn.udn.dut.cinema.port.domain.bo.CinemaBo;
import vn.udn.dut.cinema.port.domain.bo.MovieBo;
import vn.udn.dut.cinema.port.domain.bo.PromotionBo;
import vn.udn.dut.cinema.port.domain.bo.SlideBo;
import vn.udn.dut.cinema.port.domain.vo.CinemaVo;
import vn.udn.dut.cinema.port.domain.vo.MovieVo;
import vn.udn.dut.cinema.port.domain.vo.PromotionVo;
import vn.udn.dut.cinema.port.service.ICinemaService;
import vn.udn.dut.cinema.port.domain.vo.SeatOrderVo;
import vn.udn.dut.cinema.port.domain.vo.ShowtimeInfoVo;
import vn.udn.dut.cinema.port.service.IMovieService;
import vn.udn.dut.cinema.port.service.IPromotionService;
import vn.udn.dut.cinema.port.service.ISeatService;
import vn.udn.dut.cinema.port.service.IShowtimeService;
import vn.udn.dut.cinema.port.service.ISlideService;

/**
 * Homepage api
 *
 * @author HOALD
 * @date 2023-11-11
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/homepage/search")
public class HomepageSearchController extends BaseController {

	private final IPromotionService promotionService;
	private final IMovieService movieService;
	private final ISlideService slideService;
	private final ICinemaService cinemaService;
	private final IShowtimeService showtimeService;
	private final ISeatService seatService;

	@GetMapping("/documents")
	public R<Map<String, Object>> getDocuments() {
		Map<String, Object> result = new HashMap<String, Object>();
		PromotionBo bo1 = new PromotionBo();
		result.put("promotions", promotionService.queryList(bo1));
		MovieBo bo2 = new MovieBo();
		result.put("movies", movieService.queryList(bo2));
		SlideBo bo3 = new SlideBo();
		result.put("slides", slideService.queryList(bo3));
		return R.ok(result);
	}
	
	@GetMapping("/nowplayingmovies")
	public List<MovieVo> getNowPlayingMovies() {
		return movieService.getNowPlayingMovies();
	}
	
	@GetMapping("/upcommingmovies")
	public List<MovieVo> getUpcommingMovies() {
		return movieService.getUpcomingMovies();
	}

	@GetMapping("/promotions")
	public List<PromotionVo> getPromotions() {
		return promotionService.getNowPromotions();
	}
	@GetMapping("/cinemas")
	public List<CinemaVo> getCinemaList(CinemaBo bo) {
		return cinemaService.queryList(bo);
	}
	
	@GetMapping("/showtimeInfoList/{movieId}")
	public List<ShowtimeInfoVo> fetchShowtimeInfoList(@PathVariable Long movieId) {
		return showtimeService.fetchShowtimeInfoList(movieId);
	}
	
	@GetMapping("/showtime/seatOrderList/{showtimeId}")
	public List<SeatOrderVo> fetchSeatOrderList(@PathVariable Long showtimeId) {
		return seatService.fetchSeatOrderList(showtimeId);
	}
}
