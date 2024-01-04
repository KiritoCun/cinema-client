package vn.udn.dut.cinema.port.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
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
import vn.udn.dut.cinema.port.domain.Seat;
import vn.udn.dut.cinema.port.domain.bo.SeatBo;
import vn.udn.dut.cinema.port.domain.vo.SeatOrderVo;
import vn.udn.dut.cinema.port.domain.vo.SeatVo;
import vn.udn.dut.cinema.port.mapper.SeatMapper;
import vn.udn.dut.cinema.port.service.ISeatService;

/**
 * SeatService business layer processing
 *
 * @author HoaLD
 * @date 2023-08-23
 */
@RequiredArgsConstructor
@Service
public class SeatServiceImpl implements ISeatService {

	private final SeatMapper baseMapper;

	/**
	 * Query Seat
	 */
	@Override
	public SeatVo queryById(Long id) {
		return baseMapper.selectVoById(id);
	}

	/**
	 * Query Seat list
	 */
	@Override
	public TableDataInfo<SeatVo> queryPageList(SeatBo bo, PageQuery pageQuery) {
		Page<SeatVo> page = baseMapper.selectPageSeatList(pageQuery.build(), this.buildQueryWrapper(bo));
		return TableDataInfo.build(page);
	}

	/**
	 * Query Seat list
	 */
	@Override
	public List<SeatVo> queryList(SeatBo bo) {
		return baseMapper.selectSeatList(this.buildQueryWrapper(bo));
	}

	@SuppressWarnings("unchecked")
	private Wrapper<Seat> buildQueryWrapper(SeatBo bo) {
		Map<String, Object> params = bo.getParams();
		QueryWrapper<Seat> wrapper = Wrappers.query();
		wrapper.eq(bo.getShowtimeId() != null, "s.showtime_id", bo.getShowtimeId())
		.eq(bo.getId() != null, "s.id", bo.getId())
		.in(params.get("ids") != null, "s.id", (List<Long>) params.get("ids"));;
		return wrapper;
	}

	/**
	 * Add Seat
	 */
	@Override
	public Boolean insertByBo(SeatBo bo) {
		Seat add = MapstructUtils.convert(bo, Seat.class);
		validEntityBeforeSave(add);
		add.setCreateTime(new Date());
		boolean flag = baseMapper.insert(add) > 0;
		if (flag) {
			bo.setId(add.getId());
		}
		return flag;
	}

	/**
	 * Edit Seat
	 */
	@Override
	public Boolean updateByBo(SeatBo bo) {
		Seat update = MapstructUtils.convert(bo, Seat.class);
		validEntityBeforeSave(update);
		return baseMapper.updateById(update) > 0;
	}

	/**
	 * Data verification before saving
	 */
	private void validEntityBeforeSave(Seat entity) {
		// TODO Do some data validation, such as unique constraints
	}

	/**
	 * Batch delete Seat
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
	public List<SeatOrderVo> fetchSeatOrderList(Long showtimeId) {
		SeatBo seatParam = new SeatBo();
		seatParam.setShowtimeId(showtimeId);
		List<SeatVo> seatList = queryList(seatParam);
		List<SeatOrderVo> seatOrderList = new ArrayList<>();
		Map<String, List<SeatVo>> seatMap = seatList.stream().collect(Collectors.groupingBy(SeatVo::getRowCode));
		int index = 0;
		for (var entry : seatMap.entrySet()) {
			SeatOrderVo seatOrder = new SeatOrderVo();
			seatOrder.setId((long) index++);
			seatOrder.setRowCode(entry.getKey());
			seatOrder.setPrice(entry.getValue().get(0).getPrice());
			seatOrder.setSeatList(entry.getValue());
			seatOrderList.add(seatOrder);
		}
		return seatOrderList;
	}

	@Override
	public List<SeatVo> queryByIds(Long[] seatIds) {
		SeatBo seatBo = new SeatBo();
		Map<String, Object> params = new HashMap<>();
		params.put("ids", Arrays.asList(seatIds));
		seatBo.setParams(params);
		return baseMapper.selectSeatList(this.buildQueryWrapper(seatBo));
	}

}
