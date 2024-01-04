package vn.udn.dut.cinema.system.domain;

import java.io.Serial;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.cinema.common.tenant.core.TenantEntity;

/**
 * Mobile device table object sys_mobile_device
 *
 * @author HoaLD
 * @date 2023-11-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_mobile_device")
public class SysMobileDevice extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Mobile device id
     */
    @TableId(value = "id")
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
