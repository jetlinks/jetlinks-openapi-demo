package org.jetlinks.demo.openapi;

import com.alibaba.fastjson.JSON;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jetlinks.demo.openapi.util.Utils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

/**
 * @author wangzheng
 * @see
 * @since 1.0
 */
public class TokenRequest {
    private static HttpClient httpClient = HttpClientBuilder.create().build();

    /**
     * 申请token测试
     */
    @Test
    void tokenTest() {
        String url = "http://localhost:8844/api/v1/token";

        HttpRequest request = new SimpleHttpRequest(url, httpClient);

        String body = "{\"expires\": 7200}";
        request.headers(HeaderUtils.createHeadersOfJsonString(body));
        request.requestBody(body);

        System.out.println("Headers:===========>" + HeaderUtils.createHeadersOfJsonString(body));

        try {
            Response response = request.post();
            System.out.println(JSON.parse(response.asBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
