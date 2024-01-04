package vn.udn.dut.cinema.admin.domain.vo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import vn.udn.dut.cinema.system.domain.vo.SysTenantVo;

/**
 * tenant list
 *
 * @author HoaLD
 */
@Data
@AutoMapper(target = SysTenantVo.class)
public class TenantListVo {

    private String tenantId;

    private String companyName;

    private String domain;

}
