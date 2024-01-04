package vn.udn.dut.cinema.common.translation.core.impl;

import lombok.AllArgsConstructor;
import vn.udn.dut.cinema.common.core.service.DictService;
import vn.udn.dut.cinema.common.core.utils.StringUtils;
import vn.udn.dut.cinema.common.translation.annotation.TranslationType;
import vn.udn.dut.cinema.common.translation.constant.TransConstant;
import vn.udn.dut.cinema.common.translation.core.TranslationInterface;

/**
 * Dictionary translation implementation
 *
 * @author HoaLD
 */
@AllArgsConstructor
@TranslationType(type = TransConstant.DICT_TYPE_TO_LABEL)
public class DictTypeTranslationImpl implements TranslationInterface<String> {

    private final DictService dictService;

    @Override
    public String translation(Object key, String other) {
        if (key instanceof String dictValue && StringUtils.isNotBlank(other)) {
            return dictService.getDictLabel(other, dictValue);
        }
        return null;
    }
}
