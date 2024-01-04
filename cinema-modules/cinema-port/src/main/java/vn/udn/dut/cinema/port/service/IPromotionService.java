package vn.udn.dut.cinema.port.service;

import java.util.Collection;
import java.util.List;

import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.port.domain.bo.PromotionBo;
import vn.udn.dut.cinema.port.domain.vo.PromotionVo;

/**
 * Promotion service interface
 *
 * @author HoaLD
 * @date 2023-11-07
 */
public interface IPromotionService {

	/**
	 * Query Promotion
	 */
	PromotionVo queryById(Long id);

	/**
	 * Query Promotion list
	 */
	TableDataInfo<PromotionVo> queryPageList(PromotionBo bo, PageQuery pageQuery);

	/**
	 * Query Promotion list
	 */
	List<PromotionVo> queryList(PromotionBo bo);
	List<PromotionVo> getNowPromotions();

	/**
	 * Add Promotion
	 */
	Boolean insertByBo(PromotionBo bo);

	/**
	 * Edit Promotion
	 */
	Boolean updateByBo(PromotionBo bo);

	/**
	 * Verify and delete Promotion information in batches
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

}
