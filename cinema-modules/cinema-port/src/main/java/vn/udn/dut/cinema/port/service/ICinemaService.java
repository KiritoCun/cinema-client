package vn.udn.dut.cinema.port.service;

import java.util.Collection;
import java.util.List;

import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.port.domain.bo.CinemaBo;
import vn.udn.dut.cinema.port.domain.vo.CinemaVo;

public interface ICinemaService {
	CinemaVo queryById(Long id);

	TableDataInfo<CinemaVo> queryPageList(CinemaBo bo, PageQuery pageQuery);

	List<CinemaVo> queryList(CinemaBo bo);

	Boolean insertByBo(CinemaBo bo);

	Boolean updateByBo(CinemaBo bo);

	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
