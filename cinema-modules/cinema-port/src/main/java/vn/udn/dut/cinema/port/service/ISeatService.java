package vn.udn.dut.cinema.port.service;

import java.util.Collection;
import java.util.List;

import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.port.domain.bo.SeatBo;
import vn.udn.dut.cinema.port.domain.vo.SeatOrderVo;
import vn.udn.dut.cinema.port.domain.vo.SeatVo;

/**
 * Seat service interface
 *
 * @author HoaLD
 * @date 2023-12-13
 */
public interface ISeatService {

	/**
	 * Query Seat
	 */
	SeatVo queryById(Long id);

	/**
	 * Query Seat list
	 */
	TableDataInfo<SeatVo> queryPageList(SeatBo bo, PageQuery pageQuery);

	/**
	 * Query Seat list
	 */
	List<SeatVo> queryList(SeatBo bo);

	/**
	 * Add Seat
	 */
	Boolean insertByBo(SeatBo bo);

	/**
	 * Edit Seat
	 */
	Boolean updateByBo(SeatBo bo);

	/**
	 * Verify and delete Seat information in batches
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

	List<SeatOrderVo> fetchSeatOrderList(Long showtimeId);

	/**
	 *
	 * @param seatIds
	 * @return
	 */
	List<SeatVo> queryByIds(Long[] seatIds);

}
