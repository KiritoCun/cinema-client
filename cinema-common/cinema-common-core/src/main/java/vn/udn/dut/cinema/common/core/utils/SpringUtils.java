package vn.udn.dut.cinema.common.core.utils;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * spring tool class
 *
 * @author HoaLD
 */
@Component
public final class SpringUtils extends SpringUtil {

    /**
     * Returns true if the BeanFactory contains a bean definition matching the given name
     */
    public static boolean containsBean(String name) {
        return getBeanFactory().containsBean(name);
    }

    /**
     * Determines whether the bean definition registered with the given name is a singleton or a prototype.
     * If no bean definition corresponding to the given name is found, an exception (NoSuchBeanDefinitionException) will be thrown
     */
    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return getBeanFactory().isSingleton(name);
    }

    /**
     * @return Class The type of registered object
     */
    public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        return getBeanFactory().getType(name);
    }

    /**
     * If the given bean name has aliases in the bean definition, return those aliases
     */
    public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
        return getBeanFactory().getAliases(name);
    }

    /**
     * Get the aop proxy object
     */
    @SuppressWarnings("unchecked")
    public static <T> T getAopProxy(T invoker) {
        return (T) AopContext.currentProxy();
    }


    /**
     * Get the spring context
     */
    public static ApplicationContext context() {
        return getApplicationContext();
    }

}
