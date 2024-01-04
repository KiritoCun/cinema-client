package vn.udn.dut.cinema.port.service.impl;

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
import vn.udn.dut.cinema.common.core.utils.StringUtils;
import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.port.domain.Hall;
import vn.udn.dut.cinema.port.domain.bo.HallBo;
import vn.udn.dut.cinema.port.domain.vo.HallVo;
import vn.udn.dut.cinema.port.mapper.HallMapper;
import vn.udn.dut.cinema.port.service.IHallService;

/**
 * HallService business layer processing
 *
 * @author HoaLD
 * @date 2023-08-23
 */
@RequiredArgsConstructor
@Service
public class HallServiceImpl implements IHallService {

	private final HallMapper baseMapper;

	/**
	 * Query Hall
	 */
	@Override
	public HallVo queryById(Long id) {
		return baseMapper.selectVoById(id);
	}

	/**
	 * Query Hall list
	 */
	@Override
	public TableDataInfo<HallVo> queryPageList(HallBo bo, PageQuery pageQuery) {
		Page<HallVo> page = baseMapper.selectPageHallList(pageQuery.build(), this.buildQueryWrapper(bo));
		return TableDataInfo.build(page);
	}

	/**
	 * Query Hall list
	 */
	@Override
	public List<HallVo> queryList(HallBo bo) {
		return baseMapper.selectHallList(this.buildQueryWrapper(bo));
	}

	private Wrapper<Hall> buildQueryWrapper(HallBo bo) {
		QueryWrapper<Hall> wrapper = Wrappers.query();
		wrapper.eq(bo.getCinemaId() != null, "h.cinema_id", bo.getCinemaId())
				.eq(bo.getId() != null, "h.id", bo.getId())
				.like(StringUtils.isNotBlank(bo.getHallName()), "h.hall_name", bo.getHallName());
		return wrapper;
	}

	/**
	 * Add Hall
	 */
	@Override
	public Boolean insertByBo(HallBo bo) {
		Hall add = MapstructUtils.convert(bo, Hall.class);
		validEntityBeforeSave(add);
		add.setCreateTime(new Date());
		boolean flag = baseMapper.insert(add) > 0;
		if (flag) {
			bo.setId(add.getId());
		}
		return flag;
	}

	/**
	 * Edit Hall
	 */
	@Override
	public Boolean updateByBo(HallBo bo) {
		Hall update = MapstructUtils.convert(bo, Hall.class);
		validEntityBeforeSave(update);
		return baseMapper.updateById(update) > 0;
	}

	/**
	 * Data verification before saving
	 */
	private void validEntityBeforeSave(Hall entity) {
		// TODO Do some data validation, such as unique constraints
	}

	/**
	 * Batch delete Hall
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
	public Map<Long, List<HallVo>> getHallMap() {
		List<HallVo> hallList = queryList(new HallBo());
		Map<Long, List<HallVo>> hallMap = hallList.stream().collect(Collectors.groupingBy(HallVo::getCinemaId));
		return hallMap;
	}
}
