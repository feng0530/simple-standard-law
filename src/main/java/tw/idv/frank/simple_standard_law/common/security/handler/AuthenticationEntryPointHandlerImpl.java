package tw.idv.frank.simple_standard_law.common.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tw.idv.frank.simple_standard_law.common.constant.CommonCode;
import tw.idv.frank.simple_standard_law.common.tools.ResponseTool;

import java.io.IOException;

@Slf4j
@Component
public class AuthenticationEntryPointHandlerImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        String msg = authException.getMessage() + ": " + request.getRequestURI();
        log.warn(msg);
        log.warn(CommonCode.RE_LOGIN.getMes());
        ResponseTool.getRes(response, CommonCode.RE_LOGIN);
    }
}
