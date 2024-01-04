package vn.udn.dut.cinema.common.core.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Program Annotation Configuration
 *
 * @author HoaLD
 */
@AutoConfiguration
// Indicates that the proxy object is exposed through the aop framework, and AopContext can access it
@EnableAspectJAutoProxy(exposeProxy = true)
public class ApplicationConfig {

}
