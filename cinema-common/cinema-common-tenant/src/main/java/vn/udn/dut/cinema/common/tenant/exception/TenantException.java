package vn.udn.dut.cinema.common.tenant.exception;

import java.io.Serial;

import vn.udn.dut.cinema.common.core.exception.base.BaseException;

/**
 * Tenant exception class
 *
 * @author HoaLD
 */
public class TenantException extends BaseException {

    @Serial
    private static final long serialVersionUID = 1L;

    public TenantException(String code, Object... args) {
        super("tenant", code, args, null);
    }
}
