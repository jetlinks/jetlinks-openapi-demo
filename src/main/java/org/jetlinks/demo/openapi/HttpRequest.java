package org.jetlinks.demo.openapi;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;

/**
 * @author wang
 * @see
 * @since 1.0
 */
public interface HttpRequest extends Closeable {

    Response get() throws IOException;

    Response post() throws IOException;

    Response put() throws IOException;

    Response delete() throws IOException;

    Response patch() throws IOException;

    HttpRequest encode(String encode);

    HttpRequest contentType(String type);

    HttpRequest param(String name, String value);

    HttpRequest params(Map<String, Object> params);

    HttpRequest header(String name, String value);

    HttpRequest headers(Map<String, String> headers);

    HttpRequest requestBody(String body);

    HttpRequest resultAsJsonString();
}
