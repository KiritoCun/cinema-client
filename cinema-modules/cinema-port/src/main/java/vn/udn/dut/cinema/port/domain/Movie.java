package vn.udn.dut.cinema.port.domain;

import java.io.Serial;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.cinema.common.tenant.core.TenantEntity;

/**
 * Movie object
 *
 * @author HoaLD
 * @date 2023-11-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("movie")
public class Movie extends TenantEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Movie id
	 */
	@TableId(value = "id")
	private Long id;

	private String title;

	private String movieDescription;

	private Date releaseDate;

	private Date endDate;

	private Long duration;
	
	private String language;
	
	private String rated;

	private String genre;

	private String director;
	
	private String actor;

	private Long rating;

	private String posterUrl;
	
	private String trailerUrl;

	private String remark;
}
