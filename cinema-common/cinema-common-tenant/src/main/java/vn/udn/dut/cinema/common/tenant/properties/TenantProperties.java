package vn.udn.dut.cinema.common.tenant.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * Tenant Configuration Properties
 *
 * @author HoaLD
 */
@Data
@ConfigurationProperties(prefix = "tenant")
public class TenantProperties {

    /**
     * Whether to enable
     */
    private Boolean enable;

    /**
     * exclusion list
     */
    private List<String> excludes;

}
