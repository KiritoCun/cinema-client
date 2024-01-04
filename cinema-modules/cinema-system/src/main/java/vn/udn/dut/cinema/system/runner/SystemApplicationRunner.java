package vn.udn.dut.cinema.system.runner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vn.udn.dut.cinema.system.service.ISysOssConfigService;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Initialize the business data corresponding to the system module
 *
 * @author HoaLD
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class SystemApplicationRunner implements ApplicationRunner {

    private final ISysOssConfigService ossConfigService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ossConfigService.init();
        log.info("The OSS configuration is initialized successfully");
    }

}
