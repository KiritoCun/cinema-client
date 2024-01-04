package vn.udn.dut.cinema.admin.domain.vo;

import lombok.Data;

import java.util.List;

/**
 * login tenant object
 *
 * @author HoaLD
 */
@Data
public class LoginTenantVo {

    /**
     * tenant switch
     */
    private Boolean tenantEnabled;

    /**
     * list of tenant objects
     */
    private List<TenantListVo> voList;

}
