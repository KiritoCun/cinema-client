package vn.udn.dut.cinema.common.tenant.core;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.udn.dut.cinema.common.mybatis.core.domain.BaseEntity;

/**
 * Tenant base class
 *
 * @author HoaLD
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TenantEntity extends BaseEntity {

    private static final long serialVersionUID = 6313899463829257882L;
	/**
     * tenant number
     */
    private String tenantId;

}
