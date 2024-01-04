package vn.udn.dut.cinema.port.service;

import java.util.Collection;
import java.util.List;

import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.port.domain.bo.BookingBo;
import vn.udn.dut.cinema.port.domain.vo.BookingVo;

/**
 * Booking service interface
 *
 * @author HoaLD
 * @date 2023-12-13
 */
public interface IBookingService {

	/**
	 * Query Booking
	 */
	BookingVo queryById(Long id);

	/**
	 * Query Booking list
	 */
	TableDataInfo<BookingVo> queryPageList(BookingBo bo, PageQuery pageQuery);

	/**
	 * Query Booking list
	 */
	List<BookingVo> queryList(BookingBo bo);

	/**
	 * Add Booking
	 */
	Boolean insertByBo(BookingBo bo);

	/**
	 * Edit Booking
	 */
	Boolean updateByBo(BookingBo bo);

	/**
	 * Verify and delete Booking information in batches
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

}
