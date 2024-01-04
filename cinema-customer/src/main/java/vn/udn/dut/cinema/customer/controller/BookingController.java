package vn.udn.dut.cinema.customer.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vn.udn.dut.cinema.common.core.domain.R;
import vn.udn.dut.cinema.common.core.utils.StringUtils;
import vn.udn.dut.cinema.common.satoken.utils.LoginHelper;
import vn.udn.dut.cinema.customer.service.VnpayPaymentService;
import vn.udn.dut.cinema.port.domain.bo.BookingBo;
import vn.udn.dut.cinema.port.domain.bo.BookingDetailBo;
import vn.udn.dut.cinema.port.domain.bo.SeatBo;
import vn.udn.dut.cinema.port.domain.bo.VnpHistoryBo;
import vn.udn.dut.cinema.port.domain.vo.BookingDetailVo;
import vn.udn.dut.cinema.port.domain.vo.BookingInfoVo;
import vn.udn.dut.cinema.port.domain.vo.BookingVo;
import vn.udn.dut.cinema.port.domain.vo.CinemaVo;
import vn.udn.dut.cinema.port.domain.vo.HallVo;
import vn.udn.dut.cinema.port.domain.vo.InvoiceInfoVo;
import vn.udn.dut.cinema.port.domain.vo.MovieVo;
import vn.udn.dut.cinema.port.domain.vo.PromotionVo;
import vn.udn.dut.cinema.port.domain.vo.SeatTypeVo;
import vn.udn.dut.cinema.port.domain.vo.SeatVo;
import vn.udn.dut.cinema.port.domain.vo.ShowtimeVo;
import vn.udn.dut.cinema.port.domain.vo.VnpHistoryVo;
import vn.udn.dut.cinema.port.service.IBookingDetailService;
import vn.udn.dut.cinema.port.service.IBookingService;
import vn.udn.dut.cinema.port.service.ICinemaService;
import vn.udn.dut.cinema.port.service.IHallService;
import vn.udn.dut.cinema.port.service.IMovieService;
import vn.udn.dut.cinema.port.service.IPromotionService;
import vn.udn.dut.cinema.port.service.ISeatService;
import vn.udn.dut.cinema.port.service.ISeatTypeService;
import vn.udn.dut.cinema.port.service.IShowtimeService;
import vn.udn.dut.cinema.port.service.IVnpHistoryService;

/**
 * Booking controller
 *
 * @author HoaLD
 */
@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/booking")
public class BookingController {

	private final VnpayPaymentService vnpayService;
	private final ISeatService seatService;
	private final ISeatTypeService seatTypeService;
	private final IPromotionService promotionService;
	private final IVnpHistoryService vnpHistoryService;
	private final IBookingService bookingService;
	private final IBookingDetailService bookingDetailService;
	private final IShowtimeService showtimeService;
	private final ICinemaService cinemaService;
	private final IHallService hallService;
	private final IMovieService movieService;

	/**
	 * Booking info
	 */
	@GetMapping("/info")
	public R<List<BookingInfoVo>> getBookings() {
		List<BookingInfoVo> bookingInfoList = new ArrayList<>();
		Long userId = LoginHelper.getUserId();
		BookingBo bookingBo = new BookingBo();
		bookingBo.setCustomerId(userId);
		List<BookingVo> bookings = bookingService.queryList(bookingBo);
		Collections.sort(bookings, Comparator.comparingLong(BookingVo::getId).reversed());
		for (BookingVo booking : bookings) {
			BookingInfoVo bookingInfo = new BookingInfoVo();
			bookingInfo.setBookingId((booking.getId() + "").substring(13));
			CinemaVo cinema = cinemaService.queryById(booking.getCinemaId());
			ShowtimeVo showtime = showtimeService.queryById(booking.getShowtimeId());
			MovieVo movie = movieService.queryById(showtime.getMovieId());
			HallVo hall = hallService.queryById(showtime.getHallId());
			bookingInfo.setCinemaName(cinema.getCinemaName());
			bookingInfo.setMovieName(movie.getTitle());
			bookingInfo.setGenre(movie.getGenre());
			bookingInfo.setPosterUrl(movie.getPosterUrl());
			bookingInfo.setHallName(hall.getHallName());
			bookingInfo.setPromotionId(booking.getPromotionId());
			bookingInfo.setStartTime(showtime.getStartTime());
			BookingDetailBo bookingDetailBo = new BookingDetailBo();
			bookingDetailBo.setBookingId(booking.getId());
			List<BookingDetailVo> bookingDetailList = bookingDetailService.queryList(bookingDetailBo);
			List<String> seatIds = new ArrayList<>();
			for (BookingDetailVo bookingDetail : bookingDetailList) {
				SeatVo seat = seatService.queryById(bookingDetail.getSeatId());
				seatIds.add(seat.getRowCode() + seat.getColumnCode());
			}
			bookingInfo.setSeatIds(seatIds);
			bookingInfo.setPrice(booking.getTotalPrice());
			bookingInfoList.add(bookingInfo);
		}

		return R.ok(bookingInfoList);
	}

	/**
	 * Payment info
	 */
	@GetMapping("/vnpay/url/{seatIds}/{promotionId}")
	public R<String> getVnpayUrl(@PathVariable Long[] seatIds, @PathVariable Long promotionId) {
		String seatIdsStr = StringUtils.join(seatIds, ',');
		List<SeatVo> seats = seatService.queryByIds(seatIds);
		PromotionVo promotion = null;
		if (promotionId != null && promotionId != 0) {
			promotion = promotionService.queryById(promotionId);
		}
		String url = "";
		try {
			vnpayService.validBeforePayment(seats);
		} catch (Exception e) {
			return R.warn(e.getMessage());
		}
		try {
			for (SeatVo seat : seats) {
				SeatBo seatUpdate = new SeatBo();
				seatUpdate.setId(seat.getId());
				seatUpdate.setStatus("P");
				Date currentDate = new Date();
				// Tạo đối tượng Calendar và thiết lập thời gian hiện tại
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(currentDate);
				// Thêm 30 giây vào thời gian hiện tại
				calendar.add(Calendar.SECOND, 30);
				// Lấy thời gian sau khi thêm 30 giây
				Date delayedDate = calendar.getTime();
				seatUpdate.setUpdateTime(delayedDate);
				seatService.updateByBo(seatUpdate);
			}
			url = vnpayService.getPaymentUrl(seats, seatIdsStr, promotion);
		} catch (Exception e) {
			log.error("EXCEPTION WHEN GET VNPAY URL: {}", e);
			return R.warn("Có lỗi xảy ra khi kết nối đến VNPAY!", url);
		}
		return R.ok("", url);
	}

	/**
	 * Handle payment successfully
	 */
	@GetMapping("/{transactionId}")
	public R<InvoiceInfoVo> handleBookingTicket(@PathVariable String transactionId) {
		InvoiceInfoVo invoiceInfo = new InvoiceInfoVo();
		VnpHistoryBo vnpHistoryParam = new VnpHistoryBo();
		vnpHistoryParam.setVnpTransactionId(transactionId);
		VnpHistoryVo vnpHistory = vnpHistoryService.queryList(vnpHistoryParam).get(0);
		String seatIdsStr = vnpHistory.getTransactionId();
		String[] seatIds = seatIdsStr.split(",");
		SeatVo seat = seatService.queryById(Long.parseLong(seatIds[0]));
		ShowtimeVo showtime = showtimeService.queryById(seat.getShowtimeId());
		BookingBo booking = new BookingBo();
		booking.setCinemaId(showtime.getCinemaId());
		booking.setCustomerId(vnpHistory.getCreateBy());
		booking.setPaymentFlag("Y");
		booking.setNumTicket((long) seatIds.length);
		if (!"00".equals(transactionId.split("_")[1])) {
			Long promotionId = Long.parseLong(transactionId.split("_")[1]);
			booking.setPromotionId(promotionId);
			PromotionVo promotion = promotionService.queryById(promotionId);
			invoiceInfo.setPromotionName(promotion.getPromotionDescription());
		}
		booking.setShowtimeId(showtime.getId());
		booking.setTotalPrice(vnpHistory.getAmount());
		bookingService.insertByBo(booking);
		invoiceInfo.setTicketId(booking.getId());
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm - dd/MM/yyyy");
		// Chuyển đối tượng Date thành chuỗi
		String formattedDate = sdf.format(showtime.getStartTime());
		invoiceInfo.setStartTime(formattedDate);
		CinemaVo cinema = cinemaService.queryById(showtime.getCinemaId());
		invoiceInfo.setCinemaName(cinema.getCinemaName());
		HallVo hall = hallService.queryById(showtime.getHallId());
		invoiceInfo.setHallName(hall.getHallName());
		Long totalAmount = 0L;
		String seatName = "";
		List<SeatVo> seatList = new ArrayList<>();
		for (String seatIdStr : seatIds) {
			Long seatId = Long.parseLong(seatIdStr);
			SeatVo seatObj = seatService.queryById(seatId);
			seatList.add(seatObj);
			SeatTypeVo seatType = seatTypeService.queryById(seatObj.getSeatTypeId());
			totalAmount += seatType.getPrice();
			seatName += seatObj.getRowCode() + seatObj.getColumnCode() + ", ";
			BookingDetailBo bookingDetail = new BookingDetailBo();
			bookingDetail.setBookingId(booking.getId());
			bookingDetail.setSeatId(seatId);
			bookingDetail.setCinemaId(showtime.getCinemaId());
			bookingDetailService.insertByBo(bookingDetail);
			SeatBo seatUpdate = new SeatBo();
			seatUpdate.setStatus("Y");
			seatUpdate.setId(seatId);
			seatService.updateByBo(seatUpdate);
		}
		for (SeatVo seatVo : seatList) {
			SeatTypeVo seatType = seatTypeService.queryById(seatVo.getSeatTypeId());
			seatVo.setSeatTypeName(seatType.getSeatTypeName());
		}
		Map<String, Long> summary = seatList.stream()
				.collect(Collectors.groupingBy(SeatVo::getSeatTypeName, Collectors.counting()));

		String seatDescription = summary.entrySet().stream().map(entry -> entry.getValue() + " x " + entry.getKey())
				.collect(Collectors.joining(", "));
		invoiceInfo.setSeatName(seatName.substring(0, seatName.length() - 2));
		invoiceInfo.setSeatDescription(seatDescription);
		invoiceInfo.setTotalAmount(totalAmount);
		invoiceInfo.setActualAmount(vnpHistory.getAmount());
		invoiceInfo.setDiscountAmount(totalAmount - vnpHistory.getAmount());
		return R.ok(invoiceInfo);
	}

}
