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
import vn.udn.dut.cinema.port.domain.Slide;
import vn.udn.dut.cinema.port.domain.bo.SlideBo;
import vn.udn.dut.cinema.port.domain.vo.SlideVo;
import vn.udn.dut.cinema.port.mapper.SlideMapper;
import vn.udn.dut.cinema.port.service.ISlideService;

@RequiredArgsConstructor
@Service
public class SlideServiceImpl implements ISlideService {
	private final SlideMapper baseMapper;

	public SlideVo queryById(Long id) {
		return baseMapper.selectVoById(id);
	}

	/**
	 * Query Slide list
	 */
	@Override
	public TableDataInfo<SlideVo> queryPageList(SlideBo bo, PageQuery pageQuery) {
		LambdaQueryWrapper<Slide> lqw = buildQueryWrapper(bo);
		Page<SlideVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
		return TableDataInfo.build(result);
	}

	/**
	 * Query Slide list
	 */
	@Override
	public List<SlideVo> queryList(SlideBo bo) {
		LambdaQueryWrapper<Slide> lqw = buildQueryWrapper(bo);
		return baseMapper.selectVoList(lqw);
	}

	private LambdaQueryWrapper<Slide> buildQueryWrapper(SlideBo bo) {
//        Map<String, Object> params = bo.getParams();
		LambdaQueryWrapper<Slide> lqw = Wrappers.lambdaQuery();
		lqw.eq(StringUtils.isNotBlank(bo.getSlideUrl()), Slide::getSlideUrl, bo.getSlideUrl());

		return lqw;
	}

	/**
	 * Add Slide
	 */
	@Override
	public Boolean insertByBo(SlideBo bo) {
		Slide add = MapstructUtils.convert(bo, Slide.class);
		validEntityBeforeSave(add);
		boolean flag = baseMapper.insert(add) > 0;
		if (flag) {
			bo.setId(add.getId());
		}
		return flag;
	}

	/**
	 * Edit Slide
	 */
	@Override
	public Boolean updateByBo(SlideBo bo) {
		Slide update = MapstructUtils.convert(bo, Slide.class);
		validEntityBeforeSave(update);
		return baseMapper.updateById(update) > 0;
	}

	/**
	 * Data verification before saving
	 */
	private void validEntityBeforeSave(Slide entity) {
		// TODO Do some data validation, such as unique constraints
	}

	/**
	 * Batch delete Slide
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
