package vn.udn.dut.cinema.common.websocket.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * message dto
 *
 * @author HoaLD
 */
@Data
public class WebSocketMessageDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * A list of session keys to push to
     */
    private List<Long> sessionKeys;

    /**
     * message to send
     */
    private String message;
}
