package vn.udn.dut.cinema.system.service;

import java.util.Set;

/**
 * User permission handling
 *
 * @author HoaLD
 */
public interface ISysPermissionService {

    /**
     * Get role data permission
     *
     * @param userId  user id
     * @return Role permission information
     */
    Set<String> getRolePermission(Long userId);

    /**
     * Get menu data permissions
     *
     * @param userId  user id
     * @return Menu permission information
     */
    Set<String> getMenuPermission(Long userId);

}
