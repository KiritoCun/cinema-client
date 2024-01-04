package vn.udn.dut.cinema.port.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import vn.udn.dut.cinema.common.core.constant.UserConstants;
import vn.udn.dut.cinema.common.core.utils.MapstructUtils;
import vn.udn.dut.cinema.common.core.utils.StringUtils;
import vn.udn.dut.cinema.common.mybatis.core.page.PageQuery;
import vn.udn.dut.cinema.common.mybatis.core.page.TableDataInfo;
import vn.udn.dut.cinema.port.domain.Customer;
import vn.udn.dut.cinema.port.domain.bo.CustomerBo;
import vn.udn.dut.cinema.port.domain.vo.CustomerVo;
import vn.udn.dut.cinema.port.mapper.CustomerMapper;
import vn.udn.dut.cinema.port.service.ICustomerService;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements ICustomerService {
	private final CustomerMapper baseMapper;

	public CustomerVo queryById(Long id) {
		return baseMapper.selectVoById(id);
	}

	/**
	 * Query Customer list
	 */
	@Override
	public TableDataInfo<CustomerVo> queryPageList(CustomerBo bo, PageQuery pageQuery) {
		LambdaQueryWrapper<Customer> lqw = buildQueryWrapper(bo);
		Page<CustomerVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
		return TableDataInfo.build(result);
	}

	/**
	 * Query Customer list
	 */
	@Override
	public List<CustomerVo> queryList(CustomerBo bo) {
		LambdaQueryWrapper<Customer> lqw = buildQueryWrapper(bo);
		return baseMapper.selectVoList(lqw);
	}

	private LambdaQueryWrapper<Customer> buildQueryWrapper(CustomerBo bo) {
//        Map<String, Object> params = bo.getParams();
		LambdaQueryWrapper<Customer> lqw = Wrappers.lambdaQuery();
		lqw.like(StringUtils.isNotBlank(bo.getUserName()), Customer::getUserName, bo.getUserName());
		lqw.like(StringUtils.isNotBlank(bo.getNickName()), Customer::getNickName, bo.getNickName());
		lqw.like(StringUtils.isNotBlank(bo.getEmail()), Customer::getEmail, bo.getEmail());
		return lqw;
	}

	/**
	 * Add Customer
	 */
	@Override
	public Boolean insertByBo(CustomerBo bo) {
		Customer add = MapstructUtils.convert(bo, Customer.class);
		validEntityBeforeSave(add);
		boolean flag = baseMapper.insert(add) > 0;
		if (flag) {
			//bo.setId(add.getId());
		}
		return flag;
	}

	/**
	 * Edit Customer
	 */
	@Override
	public Boolean updateByBo(CustomerBo bo) {
		Customer update = MapstructUtils.convert(bo, Customer.class);
		validEntityBeforeSave(update);
		return baseMapper.updateById(update) > 0;
	}

	/**
	 * Data verification before saving
	 */
	private void validEntityBeforeSave(Customer entity) {
		// TODO Do some data validation, such as unique constraints
	}

	/**
	 * Batch delete Customer
	 */
	@Override
	public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
		if (isValid) {
			// TODO Do some business verification to determine whether verification is
			// required
		}
		return baseMapper.deleteBatchIds(ids) > 0;
	}

	@Override
	public boolean checkUserNameUnique(CustomerBo customerUser) {
		boolean exist = baseMapper.exists(new LambdaQueryWrapper<Customer>()
				.eq(Customer::getStatus, UserConstants.USER_NORMAL)
				.eq(Customer::getUserName, customerUser.getUserName())
				.ne(ObjectUtil.isNotNull(customerUser.getUserId()), Customer::getUserId, customerUser.getUserId()));
		return !exist;
	}

	@Override
	public boolean registerUser(CustomerBo customerUser) {
		Customer user = MapstructUtils.convert(customerUser, Customer.class);
		return baseMapper.insert(user) > 0;
	}

	@Override
	public CustomerVo selectByUserId(Long userId) {
		return baseMapper.selectUserById(userId);
	}

	@Override
	public boolean updateUserAvatar(Long userId, Long avatar) {
		return baseMapper.update(null,
				new LambdaUpdateWrapper<Customer>().set(Customer::getAvatar, avatar).eq(Customer::getUserId, userId)) > 0;
	}
}
