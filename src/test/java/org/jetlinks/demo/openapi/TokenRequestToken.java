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
public class TokenRequestToken {

    /**
     * 申请token测试
     */
    @Test
    void batchDeleteTest() {
        String url = "http://localhost:8844/api/v1/token";

        HttpRequest request = new SimpleHttpRequest(url);

        String body = "{\"expires\": 7200}";
        request.headers(HeaderUtils.createHeadersOfJsonString(body));
        request.requestBody(body);

        System.out.println("Headers:===========>" + HeaderUtils.createHeadersOfJsonString(body));

        try {
            Response response = request.post();
            Map<String, String> result = Utils.queryStringToMap(new String(response.asBytes(), "utf8"), "utf8");
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
