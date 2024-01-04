package vn.udn.dut.cinema.common.idempotent.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConfiguration;

import vn.udn.dut.cinema.common.idempotent.aspectj.RepeatSubmitAspect;

/**
 * Idempotent function configuration
 *
 * @author HoaLD
 */
@AutoConfiguration(after = RedisConfiguration.class)
public class IdempotentConfig {

    @Bean
    public RepeatSubmitAspect repeatSubmitAspect() {
        return new RepeatSubmitAspect();
    }

}
