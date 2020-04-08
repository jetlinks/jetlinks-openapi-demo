package org.jetlinks.demo.openapi;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author wang
 * @see
 * @since 1.0
 */
public class SimpleResponse implements Response {

    HttpResponse response;

    public SimpleResponse(HttpResponse response) {
        this.response = response;
    }

    @Override
    public byte[] asBytes() throws IOException {
        return EntityUtils.toByteArray(response.getEntity());
    }
}
