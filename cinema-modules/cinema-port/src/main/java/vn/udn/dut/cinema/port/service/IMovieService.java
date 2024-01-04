package vn.udn.dut.cinema.port.service;

import java.util.Collection;
import java.util.List;

import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.port.domain.bo.MovieBo;
import vn.udn.dut.cinema.port.domain.vo.MovieVo;

/**
 * Movie service interface
 *
 * @author HoaLD
 * @date 2023-11-07
 */
public interface IMovieService {

	/**
	 * Query Movie
	 */
	MovieVo queryById(Long id);

	/**
	 * Query Movie list
	 */
	TableDataInfo<MovieVo> queryPageList(MovieBo bo, PageQuery pageQuery);

	/**
	 * Query Movie list
	 */
	List<MovieVo> queryList(MovieBo bo);
	
	List<MovieVo> getNowPlayingMovies();
	List<MovieVo> getUpcomingMovies();
	/**
	 * Add Movie
	 */
	Boolean insertByBo(MovieBo bo);

	/**
	 * Edit Movie
	 */
	Boolean updateByBo(MovieBo bo);

	/**
	 * Verify and delete Movie information in batches
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

}
