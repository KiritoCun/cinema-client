package vn.udn.dut.cinema.system.domain.vo;

import lombok.Data;

/**
 * Upload object information
 *
 * @author HoaLD
 */
@Data
public class SysOssUploadVo {

    /**
     * URL address
     */
    private String url;

    /**
     * file name
     */
    private String fileName;

    /**
     * Object storage primary key
     */
    private String ossId;

}
