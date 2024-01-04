package vn.udn.dut.cinema.common.security.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Security configuration properties
 *
 * @author HoaLD
 */
@Data
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {

    /**
     * exclude path
     */
    private String[] excludes;


}
