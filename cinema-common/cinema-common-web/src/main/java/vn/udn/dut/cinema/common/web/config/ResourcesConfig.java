package vn.udn.dut.cinema.common.web.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import vn.udn.dut.cinema.common.web.interceptor.PlusWebInvokeTimeInterceptor;

/**
 * General configuration
 *
 * @author HoaLD
 */
@AutoConfiguration
public class ResourcesConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Global access performance interception
        registry.addInterceptor(new PlusWebInvokeTimeInterceptor());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    }

    /**
     * cross-domain configuration
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // Set access source address
        config.addAllowedOriginPattern("*");
        // Set access source request header
        config.addAllowedHeader("*");
        // Set access source request method
        config.addAllowedMethod("*");
        // Valid for 1800 seconds
        config.setMaxAge(1800L);
        // Add a mapping path to intercept all requests
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        // return the new CorsFilter
        return new CorsFilter(source);
    }
}
