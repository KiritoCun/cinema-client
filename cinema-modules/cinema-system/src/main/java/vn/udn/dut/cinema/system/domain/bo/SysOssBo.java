package vn.udn.dut.cinema.system.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.cinema.common.mybatis.core.domain.BaseEntity;
import vn.udn.dut.cinema.system.domain.SysOss;

/**
 * OSS object storage paging query object sys_oss
 *
 * @author HoaLD
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysOss.class, reverseConvertGenerate = false)
public class SysOssBo extends BaseEntity {

    private static final long serialVersionUID = 7025678082414496490L;

	/**
     * ossId
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
     * service provider
     */
    private String service;

}
