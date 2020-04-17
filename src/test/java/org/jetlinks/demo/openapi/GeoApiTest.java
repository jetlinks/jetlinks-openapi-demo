package org.jetlinks.demo.openapi;

import org.jetlinks.demo.openapi.util.Utils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

/**
 * @author wangzheng
 * @see
 * @since 1.0
 */
public class GeoApiTest {

    private static final String base_url = "http://localhost:8844/api/v1/geo/object";

    /**
     * 设备功能调用测试
     */
    @Test
    void invokeFunctionTest() {
        String url = base_url + "/_search";

        System.out.println(url);
        HttpRequest request = new SimpleHttpRequest(url);
        String body = "{\"start_date\":\"2020-04-12\"}";

        request.headers(HeaderUtils.createHeadersOfJsonString(body));
        System.out.println("Headers:===========>" + HeaderUtils.createHeadersOfJsonString(body));
        request.requestBody(body);

        try {
            Response response = request.post();
            Map<String, String> result = Utils.queryStringToMap(new String(response.asBytes(), "utf8"), "utf8");
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
