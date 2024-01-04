package vn.udn.dut.cinema.port.service;

import java.util.Collection;
import java.util.List;

import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.port.domain.bo.BookingDetailBo;
import vn.udn.dut.cinema.port.domain.vo.BookingDetailVo;

/**
 * BookingDetail service interface
 *
 * @author HoaLD
 * @date 2023-12-13
 */
public interface IBookingDetailService {

	/**
	 * Query BookingDetail
	 */
	BookingDetailVo queryById(Long id);

	/**
	 * Query BookingDetail list
	 */
	TableDataInfo<BookingDetailVo> queryPageList(BookingDetailBo bo, PageQuery pageQuery);

	/**
	 * Query BookingDetail list
	 */
	List<BookingDetailVo> queryList(BookingDetailBo bo);

	/**
	 * Add BookingDetail
	 */
	Boolean insertByBo(BookingDetailBo bo);

	/**
	 * Edit BookingDetail
	 */
	Boolean updateByBo(BookingDetailBo bo);

	/**
	 * Verify and delete BookingDetail information in batches
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

}
