package vn.udn.dut.cinema.common.sensitive.core;

/**
 * desensitization service
 * The default administrator does not filter
 * Need to rewrite the implementation according to the business
 *
 * @author HoaLD
 * @version 3.6.0
 */
public interface SensitiveService {

    /**
     * Whether to desensitize
     */
    boolean isSensitive();

}
