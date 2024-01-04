package vn.udn.dut.cinema.common.translation.core.impl;

import lombok.AllArgsConstructor;
import vn.udn.dut.cinema.common.core.service.DeptService;
import vn.udn.dut.cinema.common.translation.annotation.TranslationType;
import vn.udn.dut.cinema.common.translation.constant.TransConstant;
import vn.udn.dut.cinema.common.translation.core.TranslationInterface;

/**
 * Department Translation Implementation
 *
 * @author HoaLD
 */
@AllArgsConstructor
@TranslationType(type = TransConstant.DEPT_ID_TO_NAME)
public class DeptNameTranslationImpl implements TranslationInterface<String> {

    private final DeptService deptService;

    @Override
    public String translation(Object key, String other) {
        if (key instanceof String ids) {
            return deptService.selectDeptNameByIds(ids);
        } else if (key instanceof Long id) {
            return deptService.selectDeptNameByIds(id.toString());
        }
        return null;
    }
}
