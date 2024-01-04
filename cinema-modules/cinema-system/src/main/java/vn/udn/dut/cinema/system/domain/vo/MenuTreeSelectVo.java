package vn.udn.dut.cinema.system.domain.vo;

import cn.hutool.core.lang.tree.Tree;
import lombok.Data;

import java.util.List;

/**
 * Character menu list tree information
 *
 * @author HoaLD
 */
@Data
public class MenuTreeSelectVo {

    /**
     * Select menu list
     */
    private List<Long> checkedKeys;

    /**
     * Menu drop-down tree structure list
     */
    private List<Tree<Long>> menus;

}
