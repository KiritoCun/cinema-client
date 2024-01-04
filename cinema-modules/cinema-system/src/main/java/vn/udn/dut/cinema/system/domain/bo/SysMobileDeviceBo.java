package vn.udn.dut.cinema.system.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.cinema.common.mybatis.core.domain.BaseEntity;
import vn.udn.dut.cinema.system.domain.SysMobileDevice;

/**
 * Mobile device table business object sys_mobile_device
 *
 * @author HoaLD
 * @date 2023-11-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysMobileDevice.class, reverseConvertGenerate = false)
public class SysMobileDeviceBo extends BaseEntity {
		
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