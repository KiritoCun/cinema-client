package vn.udn.dut.cinema.port.mapper;

import vn.udn.dut.cinema.common.mybatis.core.mapper.BaseMapperPlus;
import vn.udn.dut.cinema.port.domain.Customer;
import vn.udn.dut.cinema.port.domain.vo.CustomerVo;

public interface CustomerMapper extends BaseMapperPlus<Customer, CustomerVo> {

	CustomerVo selectUserCustomerByUserName(String username);

	CustomerVo selectUserById(Long userId);

}
