package vn.udn.dut.cinema.customer;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Deploy in web container
 *
 * @author HoaLD
 */
public class CinemaCustomerServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CinemaCustomerApplication.class);
    }

}
