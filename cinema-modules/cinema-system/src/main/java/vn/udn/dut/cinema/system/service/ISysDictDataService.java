package vn.udn.dut.cinema.system.service;

import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.system.domain.bo.ContainerSztpBo;
import vn.udn.dut.cinema.system.domain.bo.SysDictDataBo;
import vn.udn.dut.cinema.system.domain.vo.ContainerSztpVo;
import vn.udn.dut.cinema.system.domain.vo.SysDictDataVo;

import java.util.List;

/**
 * Dictionary Business Layer
 *
 * @author HoaLD
 */
public interface ISysDictDataService {


    TableDataInfo<SysDictDataVo> selectPageDictDataList(SysDictDataBo dictData, PageQuery pageQuery);

    /**
     * Paging query dictionary data according to conditions
     *
     * @param dictData Dictionary data information
     * @return Dictionary data set information
     */
    List<SysDictDataVo> selectDictDataList(SysDictDataBo dictData);

    /**
     * Query dictionary data information according to dictionary type and dictionary key value
     *
     * @param dictType  dictionary type
     * @param dictValue Dictionary key value
     * @return dictionary tag
     */
    String selectDictLabel(String dictType, String dictValue);

    /**
     * Query information based on dictionary data ID
     *
     * @param dictCode Dictionary data ID
     * @return dictionary data
     */
    SysDictDataVo selectDictDataById(Long dictCode);

    /**
     * Batch delete dictionary data information
     *
     * @param dictCodes The ID of the dictionary data that needs to be deleted
     */
    void deleteDictDataByIds(Long[] dictCodes);

    /**
     * Added saving dictionary data information
     *
     * @param bo Dictionary data information
     * @return result
     */
    List<SysDictDataVo> insertDictData(SysDictDataBo bo);

    /**
     * Modify and save dictionary data information
     *
     * @param bo Dictionary data information
     * @return result
     */
    List<SysDictDataVo> updateDictData(SysDictDataBo bo);
    
    TableDataInfo<ContainerSztpVo> queryContainerSztpPageList(ContainerSztpBo bo, PageQuery pageQuery);
}
