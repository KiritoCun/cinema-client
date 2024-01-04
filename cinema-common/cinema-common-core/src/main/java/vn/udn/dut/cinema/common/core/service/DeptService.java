package vn.udn.dut.cinema.common.core.service;

/**
 * General Departmental Services
 *
 * @author HoaLD
 */
public interface DeptService {

    /**
     * Query department name by department ID
     *
     * @param deptIds Department ID strings separated by commas
     * @return Department names separated by commas
     */
    String selectDeptNameByIds(String deptIds);

}
