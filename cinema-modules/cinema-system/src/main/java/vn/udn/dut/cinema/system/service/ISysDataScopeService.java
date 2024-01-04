package vn.udn.dut.cinema.system.service;

/**
 * General Data Rights Service
 *
 * @author HoaLD
 */
public interface ISysDataScopeService {

    /**
     * Get role customization permissions
     *
     * @param roleId role id
     * @return department id group
     */
    String getRoleCustom(Long roleId);

    /**
     * Get the department and the following permissions
     *
     * @param deptId department id
     * @return department id group
     */
    String getDeptAndChild(Long deptId);

}
