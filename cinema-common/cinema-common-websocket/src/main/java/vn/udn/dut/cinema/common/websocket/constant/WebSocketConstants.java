package vn.udn.dut.cinema.common.websocket.constant;

/**
 * Constant configuration of websocket
 *
 * @author HoaLD
 */
public interface WebSocketConstants {
    /**
     * The key of the parameter in websocketSession
     */
    String LOGIN_USER_KEY = "loginUser";

    /**
     * subscribed channels
     */
    String WEB_SOCKET_TOPIC = "global:websocket";

    /**
     * Front-end heartbeat check command
     */
    String PING = "ping";

    /**
     * String for server heartbeat recovery
     */
    String PONG = "pong";
}
