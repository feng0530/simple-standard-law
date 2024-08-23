package tw.idv.frank.simple_standard_law.common.tools;

import jakarta.servlet.http.HttpServletResponse;
import tw.idv.frank.simple_standard_law.common.constant.CommonCode;
import tw.idv.frank.simple_standard_law.common.dto.CommonResult;

import java.io.IOException;

public class ResponseTool {

    public static void getRes(HttpServletResponse response, CommonCode code) throws IOException {

        // 設置響應狀態碼為 403 Forbidden
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        // 設置響應的內容類型為 JSON
        response.setContentType("application/json");
        // 設置響應的內容類型為 UTF-8
        response.setCharacterEncoding("UTF-8");

        CommonResult commonResult = new CommonResult(code);
        String jsonString = JsonTool.toJson(commonResult);

        response.getWriter().print(jsonString);
    }
}
