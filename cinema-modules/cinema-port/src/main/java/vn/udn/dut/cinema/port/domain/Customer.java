package vn.udn.dut.cinema.port.domain;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import vn.udn.dut.cinema.common.tenant.core.TenantEntity;

/**
 * Customer user object
 *
 * @author HoaLD
 */

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("customer")
public class Customer extends TenantEntity {

	private static final long serialVersionUID = 1445341316497924617L;

	/**
	 * User ID
	 */
	@TableId(value = "user_id")
	private Long userId;

	/**
	 * user account
	 */
	private String userName;

	/**
	 * User name
	 */
	private String nickName;

	/**
	 * User type (sys_user system user)
	 */
	private String customerType;

	/**
	 * Email
	 */
	private String email;

	/**
	 * phone number
	 */
	private String phonenumber;

	/**
	 * user gender
	 */
	private String sex;

	/**
	 * profile picture
	 */
	private Long avatar;

	/**
	 * password
	 */
	@TableField(insertStrategy = FieldStrategy.NOT_EMPTY, updateStrategy = FieldStrategy.NOT_EMPTY, whereStrategy = FieldStrategy.NOT_EMPTY)
	private String password;

	/**
	 * Account status (0 normal 1 disabled)
	 */
	private String status;

	/**
	 * Remark
	 */
	private String remark;

	public Customer(Long userId) {
		this.userId = userId;
	}

}
