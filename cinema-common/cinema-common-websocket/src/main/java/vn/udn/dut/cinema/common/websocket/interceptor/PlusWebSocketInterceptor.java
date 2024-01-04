package vn.udn.dut.cinema.common.websocket.interceptor;

import vn.udn.dut.cinema.common.core.domain.model.LoginUser;
import vn.udn.dut.cinema.common.satoken.utils.LoginHelper;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import static vn.udn.dut.cinema.common.websocket.constant.WebSocketConstants.LOGIN_USER_KEY;

import java.util.Map;

/**
 * Interceptor for WebSocket handshake requests
 *
 * @author zendwang
 */
public class PlusWebSocketInterceptor implements HandshakeInterceptor {

    /**
     * before shaking hands
     *
     * @param request    request
     * @param response   response
     * @param wsHandler  wsHandler
     * @param attributes attributes
     * @return Whether the handshake is successful
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        LoginUser loginUser = LoginHelper.getLoginUser();
        attributes.put(LOGIN_USER_KEY, loginUser);
        return true;
    }

    /**
     * after shaking hands
     *
     * @param request   request
     * @param response  response
     * @param wsHandler wsHandler
     * @param exception abnormal
     */
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
