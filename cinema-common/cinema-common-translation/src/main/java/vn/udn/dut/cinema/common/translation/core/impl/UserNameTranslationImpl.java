package vn.udn.dut.cinema.common.translation.core.impl;

import lombok.AllArgsConstructor;
import vn.udn.dut.cinema.common.core.service.UserService;
import vn.udn.dut.cinema.common.translation.annotation.TranslationType;
import vn.udn.dut.cinema.common.translation.constant.TransConstant;
import vn.udn.dut.cinema.common.translation.core.TranslationInterface;

/**
 * Username translation implementation
 *
 * @author HoaLD
 */
@AllArgsConstructor
@TranslationType(type = TransConstant.USER_ID_TO_NAME)
public class UserNameTranslationImpl implements TranslationInterface<String> {

    private final UserService userService;

    @Override
    public String translation(Object key, String other) {
        if (key instanceof Long id) {
            return userService.selectUserNameById(id);
        }
        return null;
    }
}
