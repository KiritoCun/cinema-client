package vn.udn.dut.cinema.common.satoken.config;

import cn.dev33.satoken.dao.SaTokenDao;
import cn.dev33.satoken.jwt.StpLogicJwtForSimple;
import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpLogic;
import vn.udn.dut.cinema.common.satoken.core.dao.PlusSaTokenDao;
import vn.udn.dut.cinema.common.satoken.core.service.SaPermissionImpl;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * sa-token configuration
 *
 * @author HoaLD
 */
@AutoConfiguration
public class SaTokenConfig implements WebMvcConfigurer {

    @Bean
    public StpLogic getStpLogicJwt() {
        // Sa-Token integrate jwt (simple mode)
        return new StpLogicJwtForSimple();
    }

    /**
     * Authorization interface implementation (use bean injection to facilitate user replacement)
     */
    @Bean
    public StpInterface stpInterface() {
        return new SaPermissionImpl();
    }

    /**
     * Custom dao layer storage
     */
    @Bean
    public SaTokenDao saTokenDao() {
        return new PlusSaTokenDao();
    }

}
