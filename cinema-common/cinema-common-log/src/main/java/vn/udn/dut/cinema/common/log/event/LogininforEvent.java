package vn.udn.dut.cinema.common.log.event;

import lombok.Data;

import jakarta.servlet.http.HttpServletRequest;

import java.io.Serial;
import java.io.Serializable;

/**
 * login event
 *
 * @author HoaLD
 */

@Data
public class LogininforEvent implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * tenant ID
     */
    private String tenantId;

    /**
     * user account
     */
    private String username;

    /**
     * Login status 0 success 1 failure
     */
    private String status;

    /**
     * Prompt message
     */
    private String message;

    /**
     * request body
     */
    private HttpServletRequest request;

    /**
     * Other parameters
     */
    private Object[] args;

}
