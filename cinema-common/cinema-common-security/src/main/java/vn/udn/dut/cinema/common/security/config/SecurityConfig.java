package vn.udn.dut.cinema.common.security.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import lombok.RequiredArgsConstructor;
import vn.udn.dut.cinema.common.core.utils.SpringUtils;
import vn.udn.dut.cinema.common.security.config.properties.SecurityProperties;
import vn.udn.dut.cinema.common.security.handler.AllUrlHandler;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Permission security configuration
 *
 * @author HoaLD
 */

@AutoConfiguration
@EnableConfigurationProperties(SecurityProperties.class)
@RequiredArgsConstructor
public class SecurityConfig implements WebMvcConfigurer {

    private final SecurityProperties securityProperties;

    /**
     * Register sa-token interceptor
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Register route interceptor, customize validation rules
        registry.addInterceptor(new SaInterceptor(handler -> {
            AllUrlHandler allUrlHandler = SpringUtils.getBean(AllUrlHandler.class);
            // Login Validation -- Exclude Multiple Paths
            SaRouter
                // get all
                .match(allUrlHandler.getUrls())
                // Check for non-excluded paths
                .check(() -> {
                    // Check if you are logged in and have a token
                    StpUtil.checkLogin();

                    // Efficiency impact for temporary testing
                    // if (log.isDebugEnabled()) {
                    //     log.debug("Remaining valid time: {}", StpUtil.getTokenTimeout());
                    //     log.debug("Temporary valid time: {}", StpUtil.getTokenActivityTimeout());
                    // }

                });
        })).addPathPatterns("/**")
            // Exclude paths that do not need to be intercepted
            .excludePathPatterns(securityProperties.getExcludes());
    }

}
