package vn.udn.dut.cinema.port.domain.bo;

import java.util.Date;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.cinema.common.core.validate.AddGroup;
import vn.udn.dut.cinema.common.core.validate.EditGroup;
import vn.udn.dut.cinema.common.tenant.core.TenantEntity;
import vn.udn.dut.cinema.port.domain.Movie;

/**
 * Movie business object
 *
 * @author HoaLD
 * @date 2023-11-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = Movie.class, reverseConvertGenerate = false)
public class MovieBo extends TenantEntity {

	private static final long serialVersionUID = -2323039814896662171L;

	/**
	 * Movie id
	 */
	@NotNull(message = "Id cannot be empty", groups = { EditGroup.class })
	private Long id;

	@NotBlank(message = "Title cannot be empty", groups = { AddGroup.class, EditGroup.class })
	private String title;

	@NotBlank(message = "Movie description cannot be empty", groups = { AddGroup.class, EditGroup.class })
	private String movieDescription;

	private Date releaseDate;

	private Date endDate;

	@NotNull(message = "Duration cannot be empty", groups = { AddGroup.class, EditGroup.class })
	private Long duration;

	private String genre;
	
	private String language;
	
	private String rated;

	private String director;
	
	private String actor;

	private Long rating;

	@NotBlank(message = "Poster url cannot be empty", groups = { AddGroup.class, EditGroup.class })
	private String posterUrl;
	
	@NotBlank(message = "Trailer url cannot be empty", groups = { AddGroup.class, EditGroup.class })
	private String trailerUrl;

	private String remark;
}
