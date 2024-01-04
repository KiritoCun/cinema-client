package vn.udn.dut.cinema.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * Routing configuration information
 *
 * @author HoaLD
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RouterVo {

    /**
     * route name
     */
    private String name;

    /**
     * routing address
     */
    private String path;

    /**
     * Whether to hide the route, when set to true, the route will not appear in the sidebar
     */
    private boolean hidden;

    /**
     * Redirect address, when noRedirect is set, the route cannot be clicked in the breadcrumb navigation
     */
    private String redirect;

    /**
     * component address
     */
    private String component;

    /**
     * Routing parameters: such as {"id": 1, "name": "hieu"}
     */
    private String query;

    /**
     * When you have more than one route declared by children under a route, it will automatically become a nested mode - such as a component page
     */
    private Boolean alwaysShow;

    /**
     * other elements
     */
    private MetaVo meta;

    /**
     * sub-routing
     */
    private List<RouterVo> children;

}
