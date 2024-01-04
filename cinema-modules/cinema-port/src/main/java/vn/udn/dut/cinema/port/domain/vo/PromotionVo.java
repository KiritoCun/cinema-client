package vn.udn.dut.cinema.port.domain.vo;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.cinema.port.domain.Promotion;

/**
 * Promotion view object
 *
 * @author HoaLD
 * @date 2023-11-07
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = Promotion.class)
public class PromotionVo implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Promotion id
	 */
	private Long id;

	/**
	 * 
	 */
	private String title;

	/**
	 * 
	 */
	private String imageUrl;

	/**
	 * 
	 */
	private String promotionDescription;

	/**
	 * 
	 */
	private Long discount;

	/**
	 * 
	 */
	private Date fromDate;

	/**
	 * 
	 */
	private Date toDate;

	/**
	 * 
	 */
	private String remark;
	private Date createTime;
}
