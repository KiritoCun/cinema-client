package vn.udn.dut.cinema.system.domain.vo;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.cinema.system.domain.SysDocument;



/**
 * System document view object sys_document
 *
 * @author HoaLD
 * @date 2023-08-23
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysDocument.class)
public class SysDocumentVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * System document id
     */
    private Long id;

    /**
     * 
     */
    private String docType;

    /**
     * 
     */
    private String docTitle;

    /**
     * 
     */
    private String docUrl;

    /**
     * 
     */
    private String docDescription;

    /**
     * 
     */
    private String docTarget;

    /**
     * 
     */
    private String imageType;

    /**
     * 
     */
    private String isExternalLink;

    /**
     * 
     */
    private String status;
    
    /**
     * 
     */
    private Long ossId;
    
    /**
     * 
     */
    private Date createTime;

    /**
     * Remark
     */
    private String remark;
}
