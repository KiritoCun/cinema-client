package vn.udn.dut.cinema.common.core.service;

/**
 * General OSS service
 *
 * @author HoaLD
 */
public interface OssService {

    /**
     * Query the corresponding url by ossId
     *
     * @param ossIds ossId string separated by commas
     * @return url string separated by commas
     */
    String selectUrlByIds(String ossIds);

}
