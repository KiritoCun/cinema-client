package vn.udn.dut.cinema.system.domain.vo;

import java.io.Serial;
import java.io.Serializable;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.cinema.system.domain.SysMobileDevice;



/**
 * Mobile device table view object sys_mobile_device
 *
 * @author HoaLD
 * @date 2023-11-10
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysMobileDevice.class)
public class SysMobileDeviceVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Mobile device id
     */
    private Long id;

    /**
     *
     */
    private Long userId;

    /**
     *
     */
    private String jwt;

    /**
     *
     */
    private String firebaseToken;

    /**
     *
     */
    private String deviceToken;

    /**
     *
     */
    private String status;
}
