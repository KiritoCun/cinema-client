package vn.udn.dut.cinema.common.core.service;

/**
 * General User Services
 *
 * @author HoaLD
 */
public interface UserService {

    /**
     * Query user account by user ID
     *
     * @param userId User ID
     * @return User Account
     */
    String selectUserNameById(Long userId);

}
