package vn.udn.dut.cinema.common.mybatis.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import vn.udn.dut.cinema.common.core.utils.StringUtils;
import vn.udn.dut.cinema.common.mybatis.helper.DataPermissionHelper;

/**
 * Data permission type
 * <p>
 * Syntax support for spel template expressions
 * <p>
 * Built-in data user current user content reference LoginUser
 * If you need to extend the data, you can use the {@link DataPermissionHelper} operation
 * Built-in service sdss system data authority service content reference SysDataScopeService
 * If you need to expand more custom services, you can refer to sdss and write it yourself
 *
 * @author HoaLD
 * @version 3.5.0
 */
@Getter
@AllArgsConstructor
public enum DataScopeType {

    /**
     * Full Data Access
     */
    ALL("1", "", ""),

    /**
     * Custom Data Permissions
     */
    CUSTOM("2", " #{#deptName} IN ( #{@sdss.getRoleCustom( #user.roleId )} ) ", ""),

    /**
     * Department Data Permissions
     */
    DEPT("3", " #{#deptName} = #{#user.deptId} ", ""),

    /**
     * Department and below data permissions
     */
    DEPT_AND_CHILD("4", " #{#deptName} IN ( #{@sdss.getDeptAndChild( #user.deptId )} )", ""),

    /**
     * Only personal data permission
     */
    SELF("5", " #{#userName} = #{#user.userId} ", " 1 = 0 ");

    private final String code;

    /**
     * Syntax takes a spel template expression
     */
    private final String sqlTemplate;

    /**
     * Fill if sqlTemplate is not satisfied
     */
    private final String elseSql;

    public static DataScopeType findCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (DataScopeType type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
}
