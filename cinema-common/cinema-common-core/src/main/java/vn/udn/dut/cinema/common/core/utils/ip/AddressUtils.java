package vn.udn.dut.cinema.common.core.utils.ip;

import cn.hutool.core.net.NetUtil;
import cn.hutool.http.HtmlUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import vn.udn.dut.cinema.common.core.utils.StringUtils;

/**
 * get address class
 *
 * @author HoaLD
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressUtils {

    // unknown address
    public static final String UNKNOWN = "XX XX";

    public static String getRealAddressByIP(String ip) {
        if (StringUtils.isBlank(ip)) {
            return UNKNOWN;
        }
        // Intranet does not query
        ip = "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : HtmlUtil.cleanHtmlTag(ip);
        if (NetUtil.isInnerIP(ip)) {
            return "Intranet IP";
        }
        return RegionUtils.getCityInfo(ip);
    }
}
