package vn.udn.dut.cinema.common.web.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * xss filtering configuration properties
 *
 * @author HoaLD
 */
@Data
@ConfigurationProperties(prefix = "xss")
public class XssProperties {

    /**
     * filter switch
     */
    private String enabled;

    /**
     * Exclude links (multiple comma-separated)
     */
    private String excludes;

    /**
     * match link
     */
    private String urlPatterns;

}
