package vn.udn.dut.cinema.port.domain.vo;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.cinema.port.domain.Slide;

@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = Slide.class)
public class SlideVo implements Serializable {
	
	@Serial
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String slideUrl;
	
	private String remark;
	
	private Date createTime;
}
