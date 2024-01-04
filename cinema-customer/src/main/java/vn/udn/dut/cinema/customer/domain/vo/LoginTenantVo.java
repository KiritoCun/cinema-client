package vn.udn.dut.cinema.customer.domain.vo;

import java.util.List;

import lombok.Data;

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
