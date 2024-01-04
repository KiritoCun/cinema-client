package vn.udn.dut.cinema.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * user status
 *
 * @author HoaLD
 */
@Getter
@AllArgsConstructor
public enum TenantStatus {
    /**
     * normal
     */
    OK("0", "Normal"),
    /**
     * disabled
     */
    DISABLE("1", "Disabled"),
    /**
     * delete
     */
    DELETED("2", "Delete");

    private final String code;
    private final String info;

}
