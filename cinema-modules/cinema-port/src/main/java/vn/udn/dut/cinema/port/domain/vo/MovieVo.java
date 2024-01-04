package vn.udn.dut.cinema.port.domain.vo;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.cinema.port.domain.Movie;

/**
 * Movie view object
 *
 * @author HoaLD
 * @date 2023-11-07
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = Movie.class)
public class MovieVo implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Movie ID
	 */
	private Long id;

	private String title;

	private String movieDescription;

	private Date releaseDate;

	private Date endDate;

	private Long duration;

	private String genre;
	
	private String language;
	
	private String rated;

	private String director;
	
	private String actor;

	private Long rating;

	private String posterUrl;
	
	private String trailerUrl;

	private String remark;
	private Date createTime;
}
