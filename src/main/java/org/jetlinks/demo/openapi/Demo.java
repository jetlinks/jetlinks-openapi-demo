package org.jetlinks.demo.openapi;




import java.io.IOException;
import java.util.Map;

/**
 * @author wangzheng
 * @see
 * @since 1.0
 */
public class Demo {

    private String clientId;


    public Response get(String url,String pathValue) throws IOException {
        HttpRequest request = new SimpleHttpRequest(url);

        return request.get();
    }

    public Response delete(String url, String pathValue) throws IOException {
        HttpRequest request = new SimpleHttpRequest(url);
        return request.delete();
    }

    public Response post(String url, Map<String, String> param) throws IOException {

        HttpRequest request = new SimpleHttpRequest(url);

        request.params(param);
        return request.post();
    }


}
