package vn.udn.dut.cinema.system.service;

import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.system.domain.bo.SysDictTypeBo;
import vn.udn.dut.cinema.system.domain.vo.SysDictDataVo;
import vn.udn.dut.cinema.system.domain.vo.SysDictTypeVo;

import java.util.List;

/**
 * Dictionary Business Layer
 *
 * @author HoaLD
 */
public interface ISysDictTypeService {


    TableDataInfo<SysDictTypeVo> selectPageDictTypeList(SysDictTypeBo dictType, PageQuery pageQuery);

    /**
     * Pagination query dictionary type according to conditions
     *
     * @param dictType Dictionary type information
     * @return Dictionary type collection information
     */
    List<SysDictTypeVo> selectDictTypeList(SysDictTypeBo dictType);

    /**
     * According to all dictionary types
     *
     * @return Dictionary type collection information
     */
    List<SysDictTypeVo> selectDictTypeAll();

    /**
     * Query dictionary data according to dictionary type
     *
     * @param dictType dictionary type
     * @return Dictionary data set information
     */
    List<SysDictDataVo> selectDictDataByType(String dictType);

    /**
     * Query information based on dictionary type ID
     *
     * @param dictId dictionary type ID
     * @return dictionary type
     */
    SysDictTypeVo selectDictTypeById(Long dictId);

    /**
     * Query information based on dictionary type
     *
     * @param dictType dictionary type
     * @return dictionary type
     */
    SysDictTypeVo selectDictTypeByType(String dictType);

    /**
     * Batch delete dictionary information
     *
     * @param dictIds The dictionary ID to delete
     */
    void deleteDictTypeByIds(Long[] dictIds);

    /**
     * Reset dictionary cache data
     */
    void resetDictCache();

    /**
     * Add save dictionary type information
     *
     * @param bo Dictionary type information
     * @return result
     */
    List<SysDictTypeVo> insertDictType(SysDictTypeBo bo);

    /**
     * Modify and save dictionary type information
     *
     * @param bo Dictionary type information
     * @return result
     */
    List<SysDictDataVo> updateDictType(SysDictTypeBo bo);

    /**
     * Check whether the dictionary type name is unique
     *
     * @param dictType dictionary type
     * @return result
     */
    boolean checkDictTypeUnique(SysDictTypeBo dictType);
}
