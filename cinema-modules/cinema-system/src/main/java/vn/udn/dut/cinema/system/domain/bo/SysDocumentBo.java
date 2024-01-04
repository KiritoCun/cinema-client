package vn.udn.dut.cinema.system.domain.bo;

import vn.udn.dut.cinema.system.domain.SysDocument;
import vn.udn.dut.cinema.common.mybatis.core.domain.BaseEntity;
import vn.udn.dut.cinema.common.core.validate.AddGroup;
import vn.udn.dut.cinema.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * System document business object sys_document
 *
 * @author HoaLD
 * @date 2023-08-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysDocument.class, reverseConvertGenerate = false)
public class SysDocumentBo extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
     * System document id
     */
    @NotNull(message = "Id cannot be empty", groups = { EditGroup.class })
    private Long id;

    /**
     * 
     */
    @NotBlank(message = "Document type cannot be empty", groups = { AddGroup.class, EditGroup.class })
    private String docType;

    /**
     * 
     */
    @NotBlank(message = "Title cannot be empty", groups = { AddGroup.class, EditGroup.class })
    private String docTitle;

    /**
     * 
     */
    @NotBlank(message = "URL cannot be empty", groups = { AddGroup.class, EditGroup.class })
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
     * Remark
     */
    private String remark;


}
