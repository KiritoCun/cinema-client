package vn.udn.dut.cinema.system.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import vn.udn.dut.cinema.common.core.utils.StringUtils;

/**
 * Cache information
 *
 * @author HoaLD
 */
@Data
@NoArgsConstructor
public class SysCache {

    /**
     * cache name
     */
    private String cacheName = "";

    /**
     * cache key name
     */
    private String cacheKey = "";

    /**
     * cache content
     */
    private String cacheValue = "";

    /**
     * Remark
     */
    private String remark = "";

    public SysCache(String cacheName, String remark) {
        this.cacheName = cacheName;
        this.remark = remark;
    }

    public SysCache(String cacheName, String cacheKey, String cacheValue) {
        this.cacheName = StringUtils.replace(cacheName, ":", "");
        this.cacheKey = StringUtils.replace(cacheKey, cacheName, "");
        this.cacheValue = cacheValue;
    }

}
