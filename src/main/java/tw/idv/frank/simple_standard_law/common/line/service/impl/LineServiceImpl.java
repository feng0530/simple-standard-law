package tw.idv.frank.simple_standard_law.common.line.service.impl;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tw.idv.frank.simple_standard_law.common.constant.CommonCode;
import tw.idv.frank.simple_standard_law.common.exception.BaseException;
import tw.idv.frank.simple_standard_law.common.line.dto.LineRes;
import tw.idv.frank.simple_standard_law.common.line.service.LineService;
import tw.idv.frank.simple_standard_law.common.tools.JsonTool;

@Service
public class LineServiceImpl implements LineService {
    @Override
    public String getOAuthUrl() {
        return new StringBuilder()
                    .append("https://notify-bot.line.me/oauth/authorize")
                    .append("?response_type=code")
                    .append("&client_id=uWBNyrW4eOQ57KkUZAYAzf")
                    .append("&redirect_uri=https://localhost:8080/main.html")
                    .append("&scope=notify")
                    .append("&state=NO_STATE")
                    .toString();
    }

    @Override
    public LineRes getLineToken(String code) throws BaseException {

        String url = "https://notify-bot.line.me/oauth/token";

        // 定義請求參數
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String body = "grant_type=authorization_code" +
                "&code=" + code +
                "&redirect_uri=https://localhost:8080/main.html" +
                "&client_id=uWBNyrW4eOQ57KkUZAYAzf" +
                "&client_secret=ect46TBEDO4sN9hb67yMumacJk4FFWGGrUKQufcy1tE";

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        // 使用 RestTemplate 發送 POST 請求
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
            return JsonTool.convertJsonToObject(response.getBody(), LineRes.class);
        } catch (Exception e) {
            throw new BaseException(CommonCode.ERROR_999);
        }
    }

    @Override
    public LineRes sendPicture(String token) throws BaseException {

        String url = new StringBuilder().append("https://notify-api.line.me/api/notify")
                                        .append("?message=Welcome to Q-Service")
                                        .append("&imageThumbnail=https://www.bibs.com.tw/cdn/shop/articles/cry.jpg?v=1682226076")
                                        .append("&imageFullsize=https://www.bibs.com.tw/cdn/shop/articles/cry.jpg?v=1682226076")
                                        .toString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<String> request = new HttpEntity<>(null, headers);

        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
            return JsonTool.convertJsonToObject(response.getBody(), LineRes.class);
        } catch (Exception e) {
            throw new BaseException(CommonCode.ERROR_999);
        }
    }
}
