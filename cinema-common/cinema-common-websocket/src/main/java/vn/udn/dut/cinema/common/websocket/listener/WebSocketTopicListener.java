package vn.udn.dut.cinema.common.websocket.listener;

import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;
import vn.udn.dut.cinema.common.websocket.holder.WebSocketSessionHolder;
import vn.udn.dut.cinema.common.websocket.utils.WebSocketUtils;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;

/**
 * WebSocket topic subscription listener
 *
 * @author HoaLD
 */
@Slf4j
public class WebSocketTopicListener implements ApplicationRunner, Ordered {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        WebSocketUtils.subscribeMessage((message) -> {
            log.info("WebSocket topic subscription received message session keys={} message={}!", message.getSessionKeys(), message.getMessage());
            if (CollUtil.isNotEmpty(message.getSessionKeys())) {
                message.getSessionKeys().forEach(key -> {
                    if (WebSocketSessionHolder.existSession(key)) {
                        WebSocketUtils.sendMessage(key, message.getMessage());
                    }
                });
            }
        });
        log.info("Initialize the WebSocket topic subscription listener successfully");
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
