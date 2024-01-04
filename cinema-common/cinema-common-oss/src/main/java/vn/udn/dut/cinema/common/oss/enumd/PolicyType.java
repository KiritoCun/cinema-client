package vn.udn.dut.cinema.common.oss.enumd;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Minio policy configuration
 *
 * @author HoaLD
 */
@Getter
@AllArgsConstructor
public enum PolicyType {

    /**
     * read only
     */
    READ("read-only"),

    /**
     * just write
     */
    WRITE("write-only"),

    /**
     * read and write
     */
    READ_WRITE("read-write");

    /**
     * type
     */
    private final String type;

}
