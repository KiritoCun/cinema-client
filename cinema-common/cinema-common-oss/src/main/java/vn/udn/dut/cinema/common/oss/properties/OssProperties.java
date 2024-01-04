package vn.udn.dut.cinema.common.oss.properties;

import lombok.Data;

/**
 * OSS object storage configuration properties
 *
 * @author HoaLD
 */
@Data
public class OssProperties {

    /**
     * Tenant id
     */
    private String tenantId;

    /**
     * Endpoint
     */
    private String endpoint;

    /**
     * Domain
     */
    private String domain;

    /**
     * Prefix
     */
    private String prefix;

    /**
     * ACCESS_KEY
     */
    private String accessKey;

    /**
     * SECRET_KEY
     */
    private String secretKey;

    /**
     * Bucket name
     */
    private String bucketName;

    /**
     * Region
     */
    private String region;

    /**
     * Is https? (Y=yes, N=no)
     */
    private String isHttps;

    /**
     * Bucket permission type (0private 1public 2custom)
     */
    private String accessPolicy;

}
