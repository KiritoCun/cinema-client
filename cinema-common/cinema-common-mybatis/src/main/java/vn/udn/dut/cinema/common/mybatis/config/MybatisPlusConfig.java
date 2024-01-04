package vn.udn.dut.cinema.common.mybatis.config;

import cn.hutool.core.net.NetUtil;
import vn.udn.dut.cinema.common.mybatis.handler.InjectionMetaObjectHandler;
import vn.udn.dut.cinema.common.mybatis.interceptor.PlusDataPermissionInterceptor;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * mybatis-plus configuration class (the comment below has a plug-in introduction)
 *
 * @author HoaLD
 */
@EnableTransactionManagement(proxyTargetClass = true)
@AutoConfiguration
@MapperScan("${mybatis-plus.mapperPackage}")
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // Data rights processing
        interceptor.addInnerInterceptor(dataPermissionInterceptor());
        // paging plugin
        interceptor.addInnerInterceptor(paginationInnerInterceptor());
        // Optimistic Lock Plugin
        interceptor.addInnerInterceptor(optimisticLockerInnerInterceptor());
        return interceptor;
    }

    /**
     * Data Permission Interceptor
     */
    public PlusDataPermissionInterceptor dataPermissionInterceptor() {
        return new PlusDataPermissionInterceptor();
    }

    /**
     * Pagination plug-in, automatically identify the database type
     */
    public PaginationInnerInterceptor paginationInnerInterceptor() {
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        // Set the maximum number of single page limit, the default is 500, -1 is unlimited
        paginationInnerInterceptor.setMaxLimit(-1L);
        // paging rationalization
        paginationInnerInterceptor.setOverflow(true);
        return paginationInnerInterceptor;
    }

    /**
     * Optimistic Lock Plugin
     */
    public OptimisticLockerInnerInterceptor optimisticLockerInnerInterceptor() {
        return new OptimisticLockerInnerInterceptor();
    }

    /**
     * Meta Object Field Population Controller
     */
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new InjectionMetaObjectHandler();
    }

    /**
     * Use the network card information to bind the snowflake generator
     * Prevent cluster snowflake ID duplication
     */
    @Bean
    public IdentifierGenerator idGenerator() {
        return new DefaultIdentifierGenerator(NetUtil.getLocalhost());
    }

    /**
     * PaginationInnerInterceptor Pagination plug-in, automatically identify the database type
     * https://baomidou.com/pages/97710a/
     * OptimisticLockerInnerInterceptor Optimistic Lock Plugin
     * https://baomidou.com/pages/0d93c0/
     * MetaObjectHandler Meta Object Field Population Controller
     * https://baomidou.com/pages/4c6bcf/
     * ISqlInjector sql injector
     * https://baomidou.com/pages/42ea4a/
     * BlockAttackInnerInterceptor If it is a delete or update operation on the entire table, the operation will be terminated
     * https://baomidou.com/pages/f9a237/
     * IllegalSQLInnerInterceptor sql performance specification plug-in (junk SQL interception)
     * IdentifierGenerator custom primary key strategy
     * https://baomidou.com/pages/568eb2/
     * TenantLineInnerInterceptor Multi-tenancy plugin
     * https://baomidou.com/pages/aef2f2/
     * DynamicTableNameInnerInterceptor Dynamic table name plugin
     * https://baomidou.com/pages/2a45ff/
     */

}
