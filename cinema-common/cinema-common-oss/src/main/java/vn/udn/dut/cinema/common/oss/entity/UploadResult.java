package vn.udn.dut.cinema.common.oss.entity;

import lombok.Builder;
import lombok.Data;

/**
 * upload return body
 *
 * @author HoaLD
 */
@Data
@Builder
public class UploadResult {

    /**
     * file path
     */
    private String url;

    /**
     * file name
     */
    private String filename;
}
