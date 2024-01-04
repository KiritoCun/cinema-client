package vn.udn.dut.cinema.port.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * Movie rating object
 *
 * @author HoaLD
 * @date 2023-11-07
 */
@Data
@TableName("movie_rating")
public class MovieRating {

	private String tenantId;

	@TableId(value = "id")
	private Long id;

	private Long movie_id;

	private Long starNumber;

	private Long customerId;
}
