package vn.udn.dut.cinema.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Equipment type
 * For a set of user systems
 *
 * @author HoaLD
 */
@Getter
@AllArgsConstructor
public enum DeviceType {

    /**
     * pc side
     */
    PC("pc"),

    /**
     * app side
     */
    APP("app"),

    /**
     * applet
     */
    XCX("xcx");

    private final String device;
}
