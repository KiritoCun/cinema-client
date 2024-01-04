package vn.udn.dut.cinema.port.service;

import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.port.domain.bo.SlideBo;
import vn.udn.dut.cinema.port.domain.vo.SlideVo;

import java.util.Collection;
import java.util.List;

public interface ISlideService {
    SlideVo queryById(Long id);

    TableDataInfo<SlideVo> queryPageList(SlideBo bo, PageQuery pageQuery);

    List<SlideVo> queryList(SlideBo bo);

    Boolean insertByBo(SlideBo bo);

    Boolean updateByBo(SlideBo bo);

    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
