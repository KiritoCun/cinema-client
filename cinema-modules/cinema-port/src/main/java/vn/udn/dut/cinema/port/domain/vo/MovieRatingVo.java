package vn.udn.dut.cinema.port.domain.vo;

import java.io.Serial;
import java.io.Serializable;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.cinema.port.domain.MovieRating;

/**
 * Movie rating view object
 *
 * @author HoaLD
 * @date 2023-11-07
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = MovieRating.class)
public class MovieRatingVo implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Movie rating ID
	 */
	private Long id;

	private String tenantId;

	private Long movie_id;

	private Long starNumber;

	private Long customerId;
}
