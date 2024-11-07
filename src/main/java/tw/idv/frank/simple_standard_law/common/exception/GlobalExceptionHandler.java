package tw.idv.frank.simple_standard_law.common.exception;

import jakarta.validation.UnexpectedTypeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
import tw.idv.frank.simple_standard_law.common.security.exception.UserNotFoundException;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public CommonResult baseExceptionHandler(BaseException e) {
        log.error(e.getMessage());
        return new CommonResult(e.getCode(), e.getMes());
    }

    @ExceptionHandler(RuntimeException.class)
    public CommonResult runtimeExceptionHandler(RuntimeException e) {
        log.error(e.getMessage());
        return new CommonResult(CommonCode.ERROR_999);
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
    public CommonResult SqlExceptionHandler(Exception e) {
        String eMsg = e.getCause().getMessage();
        log.error(eMsg);

        String msg = getAlreadyExistsMsg(eMsg);
        if(msg != null){
            return new CommonResult(HttpStatus.BAD_REQUEST.value(), msg);
        }
        return new CommonResult(CommonCode.DB_ERROR, eMsg);
    }

    private String getAlreadyExistsMsg(String msg) {
        // Key (str1)=(str2) already exists
        // 此正則表達式設定了捕獲 str2的值
        String regex = "Key \\([^\\)]+\\)=\\(([^)]+)\\) already exists";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(msg);

        if (matcher.find()) {
            return "帳號 : [%s] 已經存在".formatted(matcher.group(1));
        }else {
            return null;
        }
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
