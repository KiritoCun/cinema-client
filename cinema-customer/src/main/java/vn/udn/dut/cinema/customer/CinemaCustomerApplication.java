package vn.udn.dut.cinema.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * Starting program
 *
 * @author HoaLD
 */
@EnableFeignClients
@SpringBootApplication
@ComponentScan("vn.udn.dut.cinema")
public class CinemaCustomerApplication {

    public static void main(String[] args) {
//    	System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication application = new SpringApplication(CinemaCustomerApplication.class);
        application.setApplicationStartup(new BufferingApplicationStartup(2048));
        application.run(args);
        System.out.println("Cinema Customer started successfully");
    }
}
