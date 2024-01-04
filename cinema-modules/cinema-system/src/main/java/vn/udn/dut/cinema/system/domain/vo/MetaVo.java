package vn.udn.dut.cinema.system.domain.vo;

import lombok.Data;
import vn.udn.dut.cinema.common.core.utils.StringUtils;

/**
 * Routing display information
 *
 * @author HoaLD
 */

@Data
public class MetaVo {

    /**
     * Set the name of this route displayed in the sidebar and breadcrumbs
     */
    private String title;

    /**
     * Set the icon of the route, corresponding to the path src/assets/icons/svg
     */
    private String icon;

    /**
     * If set to true, it will not be cached by <keep-alive>
     */
    private boolean noCache;

    /**
     * Internal link address (starting with http(s)://)
     */
    private String link;

    public MetaVo(String title, String icon) {
        this.title = title;
        this.icon = icon;
    }

    public MetaVo(String title, String icon, boolean noCache) {
        this.title = title;
        this.icon = icon;
        this.noCache = noCache;
    }

    public MetaVo(String title, String icon, String link) {
        this.title = title;
        this.icon = icon;
        this.link = link;
    }

    public MetaVo(String title, String icon, boolean noCache, String link) {
        this.title = title;
        this.icon = icon;
        this.noCache = noCache;
        if (StringUtils.ishttp(link)) {
            this.link = link;
        }
    }

}
