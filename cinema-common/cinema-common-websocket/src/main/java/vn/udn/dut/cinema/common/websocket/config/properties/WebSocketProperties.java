package vn.udn.dut.cinema.common.websocket.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * WebSocket configuration items
 *
 * @author HoaLD
 */
@ConfigurationProperties("websocket")
@Data
public class WebSocketProperties {

    private Boolean enabled;

    /**
     * path
     */
    private String path;

    /**
     *  Set access source address
     */
    private String allowedOrigins;
}
