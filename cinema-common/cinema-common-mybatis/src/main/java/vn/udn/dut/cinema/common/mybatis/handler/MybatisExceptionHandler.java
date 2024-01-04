package vn.udn.dut.cinema.common.mybatis.handler;

import lombok.extern.slf4j.Slf4j;
import vn.udn.dut.cinema.common.core.domain.R;

import org.mybatis.spring.MyBatisSystemException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Mybatis exception handler
 *
 * @author HoaLD
 */
@Slf4j
@RestControllerAdvice
public class MybatisExceptionHandler {

    /**
     * Primary key or UNIQUE index, abnormal data duplication
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public R<Void> handleDuplicateKeyException(DuplicateKeyException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("Request address '{}', record '{}' already exists in the database", requestURI, e.getMessage());
        return R.fail("This record already exists in the database, please contact the administrator to confirm");
    }

    /**
     * Mybatis system exception general processing
     */
    @ExceptionHandler(MyBatisSystemException.class)
    public R<Void> handleCannotFindDataSourceException(MyBatisSystemException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String message = e.getMessage();
        if (message.contains("CannotFindDataSourceException")) {
            log.error("Request address '{}', no data source found", requestURI);
            return R.fail("The data source was not found, please contact the administrator for confirmation");
        }
        log.error("Request address '{}', Mybatis system exception", requestURI, e);
        return R.fail(message);
    }

}
