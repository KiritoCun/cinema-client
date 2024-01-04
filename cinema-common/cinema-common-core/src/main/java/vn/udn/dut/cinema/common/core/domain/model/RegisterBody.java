package vn.udn.dut.cinema.common.core.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * User registration object
 *
 * @author HoaLD
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RegisterBody extends LoginBody {

    private String userType;

}
