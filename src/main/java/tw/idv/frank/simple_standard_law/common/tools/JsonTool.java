package tw.idv.frank.simple_standard_law.common.tools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonTool {

    private static final Gson gson = new GsonBuilder().create();
    static ObjectMapper mapper = new ObjectMapper();

    public static String toJson(Object obj) {
        try {
            return gson.toJson(obj);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }


    public static JsonNode fromJson(String json) {
        try {
            return mapper.readTree(json);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public static <T> T convertJsonToObject(String jsonString, Class<T> valueType) throws JsonProcessingException {
        // 使用注入的ObjectMapper將JSON字符串轉換為Java對象  valueType指定用哪個類別裝
        return mapper.readValue(jsonString, valueType);
    }

    public static <T> T convertJsonToObject(String jsonString, TypeReference<T> typeReference) throws JsonProcessingException {
        // 使用 ObjectMapper 和 TypeReference 將 JSON 字符串轉換為 Java 對象
        return mapper.readValue(jsonString, typeReference);
    }

}
