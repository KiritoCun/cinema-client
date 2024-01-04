package vn.udn.dut.cinema.common.web.core;

import vn.udn.dut.cinema.common.core.domain.R;
import vn.udn.dut.cinema.common.core.utils.StringUtils;

/**
 * General data processing at the web layer
 *
 * @author HoaLD
 */
public class BaseController {

    /**
     * response return result
     *
     * @param rows Affected rows
     * @return operation result
     */
    protected R<Void> toAjax(int rows) {
        return rows > 0 ? R.ok() : R.fail();
    }

    /**
     * response return result
     *
     * @param result result
     * @return operation result
     */
    protected R<Void> toAjax(boolean result) {
        return result ? R.ok() : R.fail();
    }

    /**
     * page jump
     */
    public String redirect(String url) {
        return StringUtils.format("redirect:{}", url);
    }

}
