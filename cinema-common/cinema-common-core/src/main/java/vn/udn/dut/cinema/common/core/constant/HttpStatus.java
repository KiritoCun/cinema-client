package vn.udn.dut.cinema.common.core.constant;

/**
 * return status code
 *
 * @author HoaLD
 */
public interface HttpStatus {
    /**
     * Successful operation
     */
    int SUCCESS = 200;

    /**
     * Object created successfully
     */
    int CREATED = 201;

    /**
     * request has been accepted
     */
    int ACCEPTED = 202;

    /**
     * The operation was executed successfully, but no data was returned
     */
    int NO_CONTENT = 204;

    /**
     * Resource has been removed
     */
    int MOVED_PERM = 301;

    /**
     * redirect
     */
    int SEE_OTHER = 303;

    /**
     * resource has not been modified
     */
    int NOT_MODIFIED = 304;

    /**
     * Argument list error (missing, format mismatch)
     */
    int BAD_REQUEST = 400;

    /**
     * unauthorized
     */
    int UNAUTHORIZED = 401;

    /**
     * Access restricted, authorization expired
     */
    int FORBIDDEN = 403;

    /**
     * resource, service not found
     */
    int NOT_FOUND = 404;

    /**
     * http method not allowed
     */
    int BAD_METHOD = 405;

    /**
     * Resource conflict, or resource is locked
     */
    int CONFLICT = 409;

    /**
     * Unsupported data, media type
     */
    int UNSUPPORTED_TYPE = 415;

    /**
     * Internal System Error
     */
    int ERROR = 500;

    /**
     * interface not implemented
     */
    int NOT_IMPLEMENTED = 501;

    /**
     * system warning message
     */
    int WARN = 601;
}
