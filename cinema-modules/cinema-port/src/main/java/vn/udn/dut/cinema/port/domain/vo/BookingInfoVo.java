package vn.udn.dut.cinema.port.domain.vo;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;

import lombok.Data;

/**
 * Booking info view object
 *
 * @author HoaLD
 * @date 2023-12-30
 */
@Data
@ExcelIgnoreUnannotated
public class BookingInfoVo implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	private String movieName;

	private String genre;

	private String cinemaName;

	private String hallName;
	
	private String posterUrl;

	private List<String> seatIds;
	
	private String bookingQr = "https://www.galaxycine.vn/_next/image/?url=https%3A%2F%2Fcdn.galaxycine.vn%2Fmedia%2Fqrcode%2F2023%2F11%2F23%2Fqrcode_WSJ7KFP.png&w=256&q=75";

	private String bookingId;
	
	private Long promotionId;

	private Long price;

	private Date startTime;
}
