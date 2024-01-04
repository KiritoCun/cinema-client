package vn.udn.dut.cinema.system.domain.vo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.cinema.common.translation.annotation.Translation;
import vn.udn.dut.cinema.common.translation.constant.TransConstant;
import vn.udn.dut.cinema.system.domain.SysOss;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * OSS object storage view object sys_oss
 *
 * @author HoaLD
 */
@Data
@AutoMapper(target = SysOss.class)
public class SysOssVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Object storage primary key
     */
    private Long ossId;

    /**
     * file name
     */
    private String fileName;

    /**
     * original name
     */
    private String originalName;

    /**
     * file extension
     */
    private String fileSuffix;

    /**
     * URL address
     */
    private String url;

    /**
     * Creation time
     */
    private Date createTime;

    /**
     * uploader
     */
    private Long createBy;

    /**
     * uploader name
     */
    @Translation(type = TransConstant.USER_ID_TO_NAME, mapper = "createBy")
    private String createByName;

    /**
     * service provider
     */
    private String service;


}
