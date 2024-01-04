package vn.udn.dut.cinema.system.domain.vo;

import cn.hutool.core.lang.tree.Tree;
import lombok.Data;

import java.util.List;

/**
 * Role department list tree information
 *
 * @author HoaLD
 */
@Data
public class DeptTreeSelectVo {

    /**
     * Selected department list
     */
    private List<Long> checkedKeys;

    /**
     * drop down tree structure list
     */
    private List<Tree<Long>> depts;

}
