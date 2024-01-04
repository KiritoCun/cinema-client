package vn.udn.dut.cinema.common.oss.constant;

import java.util.Arrays;
import java.util.List;

/**
 * object storage constant
 *
 * @author HoaLD
 */
public interface OssConstant {

    /**
     * Default configuration KEY
     */
    String DEFAULT_CONFIG_KEY = "sys_oss:default_config";

    /**
     * Preview list resource switch Key
     */
    String PEREVIEW_LIST_RESOURCE_KEY = "sys.oss.previewListResource";

    /**
     * System data ids
     */
    List<Long> SYSTEM_DATA_IDS = Arrays.asList(1L, 2L, 3L, 4L);

    /**
     * cloud service provider
     */
    String[] CLOUD_SERVICE = new String[] {"aliyun", "qcloud", "qiniu", "obs"};

    /**
     * https status
     */
    String IS_HTTPS = "Y";

}
