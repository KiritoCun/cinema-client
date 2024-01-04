package vn.udn.dut.cinema.port.service.impl;

import java.time.LocalDate;
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
import vn.udn.dut.cinema.port.domain.Promotion;
import vn.udn.dut.cinema.port.domain.bo.PromotionBo;
import vn.udn.dut.cinema.port.domain.vo.PromotionVo;
import vn.udn.dut.cinema.port.mapper.PromotionMapper;
import vn.udn.dut.cinema.port.service.IPromotionService;

/**
 * PromotionService business layer processing
 *
 * @author HoaLD
 * @date 2023-08-23
 */
@RequiredArgsConstructor
@Service
public class PromotionServiceImpl implements IPromotionService {

	private final PromotionMapper baseMapper;

	/**
	 * Query Promotion
	 */
	@Override
	public PromotionVo queryById(Long id) {
		return baseMapper.selectVoById(id);
	}

	/**
	 * Query Promotion list
	 */
	@Override
	public TableDataInfo<PromotionVo> queryPageList(PromotionBo bo, PageQuery pageQuery) {
		LambdaQueryWrapper<Promotion> lqw = buildQueryWrapper(bo);
		Page<PromotionVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
		return TableDataInfo.build(result);
	}

	/**
	 * Query Promotion list
	 */
	@Override
	public List<PromotionVo> queryList(PromotionBo bo) {
		LambdaQueryWrapper<Promotion> lqw = buildQueryWrapper(bo);
		return baseMapper.selectVoList(lqw);
	}
	public List<PromotionVo> getNowPromotions() {
		LocalDate currentDate = LocalDate.now();
	    LambdaQueryWrapper<Promotion> nowPromotionsWrapper = Wrappers.lambdaQuery();
	    
	    nowPromotionsWrapper.le(Promotion::getFromDate, currentDate).ge(Promotion::getToDate, currentDate);
	    
	    List<PromotionVo> nowPromotions = baseMapper.selectVoList(nowPromotionsWrapper);

	    return nowPromotions;
	}

	private LambdaQueryWrapper<Promotion> buildQueryWrapper(PromotionBo bo) {
//        Map<String, Object> params = bo.getParams();
		LambdaQueryWrapper<Promotion> lqw = Wrappers.lambdaQuery();
		lqw.eq(StringUtils.isNotBlank(bo.getTitle()), Promotion::getTitle, bo.getTitle());
		lqw.like(StringUtils.isNotBlank(bo.getPromotionDescription()), Promotion::getPromotionDescription,
				bo.getPromotionDescription());
		return lqw;
	}

	/**
	 * Add Promotion
	 */
	@Override
	public Boolean insertByBo(PromotionBo bo) {
		Promotion add = MapstructUtils.convert(bo, Promotion.class);
		validEntityBeforeSave(add);
		boolean flag = baseMapper.insert(add) > 0;
		if (flag) {
			bo.setId(add.getId());
		}
		return flag;
	}

	/**
	 * Edit Promotion
	 */
	@Override
	public Boolean updateByBo(PromotionBo bo) {
		Promotion update = MapstructUtils.convert(bo, Promotion.class);
		validEntityBeforeSave(update);
		return baseMapper.updateById(update) > 0;
	}

	/**
	 * Data verification before saving
	 */
	private void validEntityBeforeSave(Promotion entity) {
		// TODO Do some data validation, such as unique constraints
	}

	/**
	 * Batch delete Promotion
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
