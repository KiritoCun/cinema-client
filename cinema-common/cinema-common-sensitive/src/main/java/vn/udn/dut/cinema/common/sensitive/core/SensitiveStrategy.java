package vn.udn.dut.cinema.common.sensitive.core;

import cn.hutool.core.util.DesensitizedUtil;
import lombok.AllArgsConstructor;

import java.util.function.Function;

/**
 * desensitization strategy
 *
 * @author HoaLD
 * @version 3.6.0
 */
@AllArgsConstructor
public enum SensitiveStrategy {

    /**
     * ID desensitization
     */
    ID_CARD(s -> DesensitizedUtil.idCardNum(s, 3, 4)),

    /**
     * Mobile phone number desensitization
     */
    PHONE(DesensitizedUtil::mobilePhone),

    /**
     * Address desensitization
     */
    ADDRESS(s -> DesensitizedUtil.address(s, 8)),

    /**
     * email desensitization
     */
    EMAIL(DesensitizedUtil::email),

    /**
     * Bank card
     */
    BANK_CARD(DesensitizedUtil::bankCard);

    // You can add other desensitization strategies by yourself

    private final Function<String, String> desensitizer;

    public Function<String, String> desensitizer() {
        return desensitizer;
    }
}
