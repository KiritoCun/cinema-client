package vn.udn.dut.cinema.common.websocket.utils;

import cn.hutool.core.collection.CollUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vn.udn.dut.cinema.common.core.domain.model.LoginUser;
import vn.udn.dut.cinema.common.redis.utils.RedisUtils;
import vn.udn.dut.cinema.common.websocket.dto.WebSocketMessageDto;
import vn.udn.dut.cinema.common.websocket.holder.WebSocketSessionHolder;

import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import static vn.udn.dut.cinema.common.websocket.constant.WebSocketConstants.LOGIN_USER_KEY;
import static vn.udn.dut.cinema.common.websocket.constant.WebSocketConstants.WEB_SOCKET_TOPIC;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Tools
 *
 * @author HoaLD
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WebSocketUtils {

    /**
     * Send a message
     *
     * @param sessionKey The session primary key is generally the user id
     * @param message    message text
     */
    public static void sendMessage(Long sessionKey, String message) {
        WebSocketSession session = WebSocketSessionHolder.getSessions(sessionKey);
        sendMessage(session, message);
    }

    /**
     * subscribe news
     *
     * @param consumer custom processing
     */
    public static void subscribeMessage(Consumer<WebSocketMessageDto> consumer) {
        RedisUtils.subscribe(WEB_SOCKET_TOPIC, WebSocketMessageDto.class, consumer);
    }

    /**
     * publish subscribe message
     *
     * @param webSocketMessage message object
     */
    public static void publishMessage(WebSocketMessageDto webSocketMessage) {
        List<Long> unsentSessionKeys = new ArrayList<>();
        // In the current service session, send messages directly
        for (Long sessionKey : webSocketMessage.getSessionKeys()) {
            if (WebSocketSessionHolder.existSession(sessionKey)) {
                WebSocketUtils.sendMessage(sessionKey, webSocketMessage.getMessage());
                continue;
            }
            unsentSessionKeys.add(sessionKey);
        }
        // Not in the session of the current service, publish and subscribe messages
        if (CollUtil.isNotEmpty(unsentSessionKeys)) {
            WebSocketMessageDto broadcastMessage = new WebSocketMessageDto();
            broadcastMessage.setMessage(webSocketMessage.getMessage());
            broadcastMessage.setSessionKeys(unsentSessionKeys);
            RedisUtils.publish(WEB_SOCKET_TOPIC, broadcastMessage, consumer -> {
                log.info("WebSocket sends topic subscription message topic:{} session keys:{} message:{}",
                    WEB_SOCKET_TOPIC, unsentSessionKeys, webSocketMessage.getMessage());
            });
        }
    }

    public static void sendPongMessage(WebSocketSession session) {
        sendMessage(session, new PongMessage());
    }

    public static void sendMessage(WebSocketSession session, String message) {
        sendMessage(session, new TextMessage(message));
    }

    private static void sendMessage(WebSocketSession session, WebSocketMessage<?> message) {
        if (session == null || !session.isOpen()) {
            log.error("[send] session session has been closed");
        } else {
            try {
                // Get the users in the current session
                LoginUser loginUser = (LoginUser) session.getAttributes().get(LOGIN_USER_KEY);
                session.sendMessage(message);
                log.info("[send] sessionId: {},userId:{},userType:{},message:{}", session.getId(), loginUser.getUserId(), loginUser.getUserType(), message);
            } catch (IOException e) {
                log.error("[send] session({}) send message({}) exception", session, message, e);
            }
        }
    }
}
