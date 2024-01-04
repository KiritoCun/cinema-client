package vn.udn.dut.cinema.common.satoken.core.service;

import cn.dev33.satoken.stp.StpInterface;
import vn.udn.dut.cinema.common.core.domain.model.LoginUser;
import vn.udn.dut.cinema.common.core.enums.UserType;
import vn.udn.dut.cinema.common.satoken.utils.LoginHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * sa-token permission management implementation class
 *
 * @author HoaLD
 */
public class SaPermissionImpl implements StpInterface {

    /**
     * Get list of menu permissions
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        LoginUser loginUser = LoginHelper.getLoginUser();
        UserType userType = UserType.getUserType(loginUser.getUserType());
        if (userType == UserType.SYS_USER) {
            return new ArrayList<>(loginUser.getMenuPermission());
        } else if (userType == UserType.APP_USER) {
            // Other terminals write their own according to the business
        }
        return new ArrayList<>();
    }

    /**
     * Get a list of role permissions
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        LoginUser loginUser = LoginHelper.getLoginUser();
        UserType userType = UserType.getUserType(loginUser.getUserType());
        if (userType == UserType.SYS_USER) {
            return new ArrayList<>(loginUser.getRolePermission());
        } else if (userType == UserType.APP_USER) {
            // Other terminals write their own according to the business
        }
        return new ArrayList<>();
    }
}
