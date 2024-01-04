package vn.udn.dut.cinema.common.sms.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * SMS text message configuration properties
 *
 * @author HoaLD
 * @version 4.2.0
 */
@Data
@ConfigurationProperties(prefix = "sms")
public class SmsProperties {

    private Boolean enabled;

    /**
     * configure node
     * Ali Cloud dysmsapi.aliyuncs.com
     * Tencent Cloud sms.tencentcloudapi.com
     */
    private String endpoint;

    /**
     * key
     */
    private String accessKeyId;

    /**
     * key
     */
    private String accessKeySecret;

    /*
     * SMS signature
     */
    private String signName;

    /**
     * SMS App ID (Tencent Exclusive)
     */
    private String sdkAppId;

}
