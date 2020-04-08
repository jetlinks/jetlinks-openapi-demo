package org.jetlinks.demo.openapi;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.Map;

/**
 * @author wangzheng
 * @see
 * @since 1.0
 */
public class Demo {

    private String clientId;


    public Object get(String pathValue) {
        CloseableHttpClient client = HttpClientBuilder.create()
                .build();

        return null;
    }

    public Object delete(String pathValue) {
        return null;
    }

    public Object post(Map<String, Object> param) {

        return null;
    }


}
