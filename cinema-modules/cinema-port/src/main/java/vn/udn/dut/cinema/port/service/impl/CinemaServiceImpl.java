package vn.udn.dut.cinema.port.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.RequiredArgsConstructor;
import vn.udn.dut.cinema.common.core.utils.MapstructUtils;
import vn.udn.dut.cinema.common.core.utils.StringUtils;
import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.port.domain.Cinema;
import vn.udn.dut.cinema.port.domain.bo.CinemaBo;
import vn.udn.dut.cinema.port.domain.vo.CinemaVo;
import vn.udn.dut.cinema.port.mapper.CinemaMapper;
import vn.udn.dut.cinema.port.service.ICinemaService;

@RequiredArgsConstructor
@Service
public class CinemaServiceImpl implements ICinemaService {
	private final CinemaMapper baseMapper;

	public CinemaVo queryById(Long id) {
		return baseMapper.selectVoById(id);
	}

	/**
	 * Query Cinema list
	 */
	@Override
	public TableDataInfo<CinemaVo> queryPageList(CinemaBo bo, PageQuery pageQuery) {
		LambdaQueryWrapper<Cinema> lqw = buildQueryWrapper(bo);
		Page<CinemaVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
		return TableDataInfo.build(result);
	}

	/**
	 * Query Cinema list
	 */
	@Override
	public List<CinemaVo> queryList(CinemaBo bo) {
		LambdaQueryWrapper<Cinema> lqw = buildQueryWrapper(bo);
		return baseMapper.selectVoList(lqw);
	}

	private LambdaQueryWrapper<Cinema> buildQueryWrapper(CinemaBo bo) {
//        Map<String, Object> params = bo.getParams();
		LambdaQueryWrapper<Cinema> lqw = Wrappers.lambdaQuery();
		lqw.like(StringUtils.isNotBlank(bo.getCinemaName()), Cinema::getCinemaName, bo.getCinemaName());
		lqw.like(StringUtils.isNotBlank(bo.getCinemaAddress()), Cinema::getCinemaAddress, bo.getCinemaAddress());
		return lqw;
	}

	/**
	 * Add Cinema
	 */
	@Override
	public Boolean insertByBo(CinemaBo bo) {
		Cinema add = MapstructUtils.convert(bo, Cinema.class);
		validEntityBeforeSave(add);
		boolean flag = baseMapper.insert(add) > 0;
		if (flag) {
			bo.setId(add.getId());
		}
		return flag;
	}

	/**
	 * Edit Cinema
	 */
	@Override
	public Boolean updateByBo(CinemaBo bo) {
		Cinema update = MapstructUtils.convert(bo, Cinema.class);
		validEntityBeforeSave(update);
		return baseMapper.updateById(update) > 0;
	}

	/**
	 * Data verification before saving
	 */
	private void validEntityBeforeSave(Cinema entity) {
		// TODO Do some data validation, such as unique constraints
	}

	/**
	 * Batch delete Cinema
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
