package tw.idv.frank.simple_standard_law.common.exception;

import jakarta.validation.UnexpectedTypeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import tw.idv.frank.simple_standard_law.common.constant.CommonCode;
import tw.idv.frank.simple_standard_law.common.dto.CommonResult;

import java.sql.SQLException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public CommonResult baseExceptionHandler(BaseException e) {
        return new CommonResult(e.getCode(), e.getMes());
    }

    /**
     * 未通過 @valid驗證的 Exception
     * @param e
     * @return
     */
    @ExceptionHandler({
                        MethodArgumentNotValidException.class,
                        HandlerMethodValidationException.class,
                        HttpMessageNotReadableException.class
    })
    public CommonResult validExceptionHandler(Exception e) {
        log.error(e.getMessage());
        return new CommonResult(CommonCode.PARAMETER_ERROR, e.getMessage());
    }

    /**
     * 請求參數格式錯誤的 Exception
     * @param e
     * @return
     */
    @ExceptionHandler({MethodArgumentTypeMismatchException.class, UnexpectedTypeException.class})
    public CommonResult parameterTypeExceptionHandler(RuntimeException e) {
        log.error(e.getMessage());
        return new CommonResult(CommonCode.PARAMETER_TYPE_ERROR, e.getMessage());
    }

    /**
     * DB Exception
     * @param e
     * @return
     */
    @ExceptionHandler({SQLException.class})
    public CommonResult parameterTypeExceptionHandler(Exception e) {
        log.error(e.getMessage());
        return new CommonResult(CommonCode.DB_ERROR, e.getMessage());
    }

    /**
     * 密碼錯誤的 Exception
     * @param e
     * @return
     */
    @ExceptionHandler(BadCredentialsException.class)
    public CommonResult badCredentialsExceptionHandler(AuthenticationException e) {
        log.error("密碼錯誤!");
        return new CommonResult(CommonCode.LOGIN_ERROR, "密碼錯誤!");
    }

    /**
     * 帳號不存在的 Exception
     * @param e
     * @return
     */
    @ExceptionHandler(UserNotFoundException.class)
    public CommonResult userNotFoundExceptionHandler(AuthenticationException e) {
        log.error(e.getMessage());
        return new CommonResult(CommonCode.LOGIN_ERROR, e.getMessage());
    }

    @ExceptionHandler(DisabledException.class)
    public CommonResult disabledExceptionHandler(AuthenticationException e) {
        log.error(e.getMessage());
        return new CommonResult(CommonCode.U903, e.getMessage());
    }
}
