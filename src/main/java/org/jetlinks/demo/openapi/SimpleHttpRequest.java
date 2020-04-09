package org.jetlinks.demo.openapi;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wang
 * @see
 * @since 1.0
 */
public class SimpleHttpRequest implements HttpRequest {

    private Map<String, Object> params = new LinkedHashMap<>();
    private Map<String, String> headers = new LinkedHashMap<>();
    private String url;
    private String requestBody;
    private String contentType;
    private String encode = "utf-8";
    protected HttpClient httpClient;

    public SimpleHttpRequest(String url) {
        this.url = url;
        createHttpClient();
    }

    public SimpleHttpRequest(String url, HttpClient client) {
        this.url = url;
        this.httpClient = client;
    }

    protected void createHttpClient() {
        if (httpClient == null) {
            HttpClientBuilder builder = HttpClientBuilder.create();
            httpClient = builder.build();

        }
    }

    @Override
    public Response get() throws IOException {
        String param = EntityUtils.toString(createUrlEncodedFormEntity());
        String tmpUrl = url + (url.contains("?") ? "&" : "?") + param;
        HttpGet get = new HttpGet(tmpUrl);
        HttpResponse response = execute(get);
        return getResultValue(response);
    }


    @Override
    public Response post() throws IOException {
        HttpPost post = new HttpPost(url);
        if (requestBody != null)
            post.setEntity(new StringEntity(requestBody, ContentType.create(contentType,encode)));
        else {
            post.setEntity(createUrlEncodedFormEntity());
        }
        HttpResponse response = execute(post);
        return getResultValue(response);
    }

    @Override
    public Response put() throws IOException {
        HttpPut put = new HttpPut(url);
        if (requestBody != null)
            put.setEntity(new StringEntity(requestBody, ContentType.create(contentType, encode)));
        else {

            put.setEntity(createUrlEncodedFormEntity());
        }
        HttpResponse response = execute(put);
        return getResultValue(response);
    }

    protected UrlEncodedFormEntity createUrlEncodedFormEntity() throws UnsupportedEncodingException {
        List<NameValuePair> nameValuePair = params.entrySet()
                .stream().map(stringStringEntry ->
                        new BasicNameValuePair(stringStringEntry.getKey(), String.valueOf(stringStringEntry.getValue())))
                .collect(Collectors.toList());
        return new UrlEncodedFormEntity(nameValuePair, encode);
    }

    @Override
    public Response delete() throws IOException {
        HttpDelete delete = new HttpDelete(url);
        HttpResponse response = execute(delete);
        return getResultValue(response);
    }

    @Override
    public Response patch() throws IOException {
        HttpPatch delete = new HttpPatch(url);
        if (requestBody != null)
            delete.setEntity(new StringEntity(requestBody, ContentType.create(contentType)));
        else {
            delete.setEntity(createUrlEncodedFormEntity());
        }
        return getResultValue(execute(delete));
    }

    @Override
    public void close() throws IOException {
        if (httpClient != null && httpClient instanceof CloseableHttpClient) {
            ((CloseableHttpClient) httpClient).close();
        }
    }

    @Override
    public HttpRequest resultAsJsonString() {
        header("Accept", "application/json");
        return this;
    }

    @Override
    public HttpRequest requestBody(String body) {
        contentType("application/json");
        this.requestBody = body;
        return this;
    }

    @Override
    public HttpRequest encode(String encode) {
        this.encode = encode;
        return this;
    }

    @Override
    public HttpRequest contentType(String type) {
        this.contentType = type;
        return this;
    }

    @Override
    public HttpRequest param(String name, String value) {
        this.params.put(name, value);
        return this;
    }

    @Override
    public HttpRequest params(Map<String, Object> params) {
        this.params.putAll(params);
        return this;
    }

    @Override
    public HttpRequest header(String name, String value) {
        this.headers.put(name, value);
        return this;
    }

    @Override
    public HttpRequest headers(Map<String, String> header) {
        this.headers.putAll(header);
        return this;
    }

    private Response getResultValue(HttpResponse res) throws IOException {
        return new SimpleResponse(res);
    }

    private HttpResponse execute(HttpRequestBase request) throws IOException {
        putHeader(request);
        return httpClient.execute(request);
    }

    private void putHeader(HttpUriRequest request) {
        headers.forEach(request::setHeader);
    }
}
