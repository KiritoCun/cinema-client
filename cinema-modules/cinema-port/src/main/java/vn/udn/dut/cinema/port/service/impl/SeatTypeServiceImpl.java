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
import vn.udn.dut.cinema.port.domain.SeatType;
import vn.udn.dut.cinema.port.domain.bo.SeatTypeBo;
import vn.udn.dut.cinema.port.domain.vo.SeatTypeVo;
import vn.udn.dut.cinema.port.mapper.SeatTypeMapper;
import vn.udn.dut.cinema.port.service.ISeatTypeService;

/**
 * SeatType Service business layer processing
 *
 * @author HoaLD
 * @date 2023-12-10
 */
@RequiredArgsConstructor
@Service
public class SeatTypeServiceImpl implements ISeatTypeService {

	private final SeatTypeMapper baseMapper;

	/**
	 * Query SeatType
	 */
	@Override
	public SeatTypeVo queryById(Long id) {
		return baseMapper.selectVoById(id);
	}

	/**
	 * Query SeatType list
	 */
	@Override
	public TableDataInfo<SeatTypeVo> queryPageList(SeatTypeBo bo, PageQuery pageQuery) {
		LambdaQueryWrapper<SeatType> lqw = buildQueryWrapper(bo);
		Page<SeatTypeVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
		return TableDataInfo.build(result);
	}

	/**
	 * Query SeatType list
	 */
	@Override
	public List<SeatTypeVo> queryList(SeatTypeBo bo) {
		LambdaQueryWrapper<SeatType> lqw = buildQueryWrapper(bo);
		return baseMapper.selectVoList(lqw);
	}

	private LambdaQueryWrapper<SeatType> buildQueryWrapper(SeatTypeBo bo) {
//        Map<String, Object> params = bo.getParams();
		LambdaQueryWrapper<SeatType> lqw = Wrappers.lambdaQuery();
		lqw.like(StringUtils.isNotBlank(bo.getSeatTypeName()), SeatType::getSeatTypeName, bo.getSeatTypeName());
		return lqw;
	}

	/**
	 * Add SeatType
	 */
	@Override
	public Boolean insertByBo(SeatTypeBo bo) {
		SeatType add = MapstructUtils.convert(bo, SeatType.class);
		validEntityBeforeSave(add);
		boolean flag = baseMapper.insert(add) > 0;
		if (flag) {
			bo.setId(add.getId());
		}
		return flag;
	}

	/**
	 * Edit SeatType
	 */
	@Override
	public Boolean updateByBo(SeatTypeBo bo) {
		SeatType update = MapstructUtils.convert(bo, SeatType.class);
		validEntityBeforeSave(update);
		return baseMapper.updateById(update) > 0;
	}

	/**
	 * Data verification before saving
	 */
	private void validEntityBeforeSave(SeatType entity) {
		// TODO Do some data validation, such as unique constraints
	}

	/**
	 * Batch delete SeatType
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
