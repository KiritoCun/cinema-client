package vn.udn.dut.cinema.port.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("hall_seat")
public class HallSeat {
	
    @TableId(value = "id")
    
    private Long id;
    
    private Long hallId;
    
    private Long seatTypeId;
    
    private String rowCode;
    
    private Integer rowSeatNumber;
}
