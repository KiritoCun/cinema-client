package vn.udn.dut.cinema.common.core.service;

/**
 * General parameter configuration service
 *
 * @author HoaLD
 */
public interface ConfigService {

    /**
     * Get the parameter value according to the parameter key
     *
     * @param configKey parameter key
     * @return parameter value
     */
    String getConfigValue(String configKey);

}
