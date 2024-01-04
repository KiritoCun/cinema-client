package vn.udn.dut.cinema.common.translation.core.impl;

import lombok.AllArgsConstructor;
import vn.udn.dut.cinema.common.core.service.OssService;
import vn.udn.dut.cinema.common.translation.annotation.TranslationType;
import vn.udn.dut.cinema.common.translation.constant.TransConstant;
import vn.udn.dut.cinema.common.translation.core.TranslationInterface;

/**
 * OSS translation implementation
 *
 * @author HoaLD
 */
@AllArgsConstructor
@TranslationType(type = TransConstant.OSS_ID_TO_URL)
public class OssUrlTranslationImpl implements TranslationInterface<String> {

    private final OssService ossService;

    @Override
    public String translation(Object key, String other) {
        if (key instanceof String ids) {
            return ossService.selectUrlByIds(ids);
        } else if (key instanceof Long id) {
            return ossService.selectUrlByIds(id.toString());
        }
        return null;
    }
}
