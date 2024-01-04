package vn.udn.dut.cinema.common.core.constant;

/**
 * User constant information
 *
 * @author HoaLD
 */
public interface UserConstants {

    /**
     * The unique identifier of system users within the platform
     */
    String SYS_USER = "SYS_USER";

    /**
     * normal status
     */
    String NORMAL = "0";

    /**
     * Abnormal state
     */
    String EXCEPTION = "1";

    /**
     * User normal state
     */
    String USER_NORMAL = "0";

    /**
     * User ban status
     */
    String USER_DISABLE = "1";

    /**
     * Character normal state
     */
    String ROLE_NORMAL = "0";

    /**
     * Character ban status
     */
    String ROLE_DISABLE = "1";

    /**
     * department normal status
     */
    String DEPT_NORMAL = "0";

    /**
     * Department inactive status
     */
    String DEPT_DISABLE = "1";

    /**
     * Dictionary normal state
     */
    String DICT_NORMAL = "0";

    /**
     * Is it the system default (yes)
     */
    String YES = "Y";

    /**
     * Is the menu external link (yes)
     */
    String YES_FRAME = "0";

    /**
     * Is the menu external link (No)
     */
    String NO_FRAME = "1";

    /**
     * menu normal state
     */
    String MENU_NORMAL = "0";

    /**
     * Menu disabled state
     */
    String MENU_DISABLE = "1";

    /**
     * Menu Type (Category)
     */
    String TYPE_DIR = "M";

    /**
     * menu type (menu)
     */
    String TYPE_MENU = "C";

    /**
     * Menu type (button)
     */
    String TYPE_BUTTON = "F";

    /**
     * Layout component identification
     */
    String LAYOUT = "Layout";

    /**
     * ParentView component identification
     */
    String PARENT_VIEW = "ParentView";

    /**
     * InnerLink component identification
     */
    String INNER_LINK = "InnerLink";

    /**
     * Username length limit
     */
    int USERNAME_MIN_LENGTH = 2;
    int USERNAME_MAX_LENGTH = 20;

    /**
     * Password length limit
     */
    int PASSWORD_MIN_LENGTH = 5;
    int PASSWORD_MAX_LENGTH = 20;

    /**
     * super administrator ID
     */
    Long SUPER_ADMIN_ID = 1L;

}
