package vn.udn.dut.cinema.common.core.config;

import jakarta.validation.Validator;
import org.hibernate.validator.HibernateValidator;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Properties;

/**
 * Validation framework configuration class
 *
 * @author HoaLD
 */
@AutoConfiguration
public class ValidatorConfig {

    /**
     * Configuration verification framework Quick return mode
     */
    @Bean
    public Validator validator(MessageSource messageSource) {
        @SuppressWarnings("resource")
		LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
        // globalization
        factoryBean.setValidationMessageSource(messageSource);
        // Set the validator to use HibernateValidator
        factoryBean.setProviderClass(HibernateValidator.class);
        Properties properties = new Properties();
        // set fast exception return
        properties.setProperty("hibernate.validator.fail_fast", "true");
        factoryBean.setValidationProperties(properties);
        // load configuration
        factoryBean.afterPropertiesSet();
        return factoryBean.getValidator();
    }

}
