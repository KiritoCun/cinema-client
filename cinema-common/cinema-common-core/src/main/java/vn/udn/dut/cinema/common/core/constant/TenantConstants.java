package vn.udn.dut.cinema.common.core.constant;

/**
 * Tenant constant information
 *
 * @author HoaLD
 */
public interface TenantConstants {

    /**
     * Tenant normal status
     */
    String NORMAL = "0";

    /**
     * Tenant Ban Status
     */
    String DISABLE = "1";

    /**
     * super administrator ID
     */
    Long SUPER_ADMIN_ID = 1L;

    /**
     * super administrator role roleKey
     */
    String SUPER_ADMIN_ROLE_KEY = "superadmin";

    /**
     * Tenant administrator role roleKey
     */
    String TENANT_ADMIN_ROLE_KEY = "admin";

    /**
     * Tenant administrator role name
     */
    String TENANT_ADMIN_ROLE_NAME = "administrator";

    /**
     * Default tenant ID
     */
    String DEFAULT_TENANT_ID = "000000";

}
