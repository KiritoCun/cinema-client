package vn.udn.dut.cinema.port.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.RequiredArgsConstructor;
import vn.udn.dut.cinema.common.core.utils.MapstructUtils;
import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.port.domain.BookingDetail;
import vn.udn.dut.cinema.port.domain.bo.BookingDetailBo;
import vn.udn.dut.cinema.port.domain.vo.BookingDetailVo;
import vn.udn.dut.cinema.port.mapper.BookingDetailMapper;
import vn.udn.dut.cinema.port.service.IBookingDetailService;

/**
 * BookingDetailService business layer processing
 *
 * @author HoaLD
 * @date 2023-08-23
 */
@RequiredArgsConstructor
@Service
public class BookingDetailServiceImpl implements IBookingDetailService {

	private final BookingDetailMapper baseMapper;

	/**
	 * Query BookingDetail
	 */
	@Override
	public BookingDetailVo queryById(Long id) {
		return baseMapper.selectVoById(id);
	}

	/**
	 * Query BookingDetail list
	 */
	@Override
	public TableDataInfo<BookingDetailVo> queryPageList(BookingDetailBo bo, PageQuery pageQuery) {
		Page<BookingDetailVo> page = baseMapper.selectPageBookingDetailList(pageQuery.build(), this.buildQueryWrapper(bo));
		return TableDataInfo.build(page);
	}

	/**
	 * Query BookingDetail list
	 */
	@Override
	public List<BookingDetailVo> queryList(BookingDetailBo bo) {
		return baseMapper.selectBookingDetailList(this.buildQueryWrapper(bo));
	}

	private Wrapper<BookingDetail> buildQueryWrapper(BookingDetailBo bo) {
//      Map<String, Object> params = bo.getParams();
		QueryWrapper<BookingDetail> wrapper = Wrappers.query();
		wrapper.eq(bo.getCinemaId() != null, "b.cinema_id", bo.getCinemaId())
				.eq(bo.getBookingId() != null, "b.booking_id", bo.getBookingId())
				.eq(bo.getSeatId() != null, "b.seat_id", bo.getSeatId());
		return wrapper;
	}

	/**
	 * Add BookingDetail
	 */
	@Override
	public Boolean insertByBo(BookingDetailBo bo) {
		BookingDetail add = MapstructUtils.convert(bo, BookingDetail.class);
		validEntityBeforeSave(add);
		boolean flag = baseMapper.insert(add) > 0;
		if (flag) {
			bo.setId(add.getId());
		}
		return flag;
	}

	/**
	 * Edit BookingDetail
	 */
	@Override
	public Boolean updateByBo(BookingDetailBo bo) {
		BookingDetail update = MapstructUtils.convert(bo, BookingDetail.class);
		validEntityBeforeSave(update);
		return baseMapper.updateById(update) > 0;
	}

	/**
	 * Data verification before saving
	 */
	private void validEntityBeforeSave(BookingDetail entity) {
		// TODO Do some data validation, such as unique constraints
	}

	/**
	 * Batch delete BookingDetail
	 */
	@Override
	public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
		if (isValid) {
			// TODO Do some business verification to determine whether verification is
			// required
		}
		return baseMapper.deleteBatchIds(ids) > 0;
	}
}
