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
public class DeviceProductApiTest {

    private static final String base_url = "http://localhost:8844/api/v1/product";
    private static final String productId = "1236859833832701952";

    /**
     * 根据产品ID和动态查询参数查询设备相关数据测试
     */
    @Test
    void queryDeviceLogTest() {
        String url = base_url + "/" + productId + "/log/_query/";

        System.out.println(url);
        HttpRequest request = new SimpleHttpRequest(url);
        String body = "{\n" +
                "\t\"pageSize\": 25,\n" +
                "\t\"pageIndex\": 0,\n" +
                "\t\"terms\": [{\n" +
                "\t\t\"column\": \"deviceId\",\n" +
                "\t\t\"value\": \"test001\"\n" +
                "\t}]\n" +
                "}";

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

    /**
     * 分页查询设备属性测试
     */
    @Test
    void queryDevicePropertiesTest() {
        String url = base_url + "/" + productId + "/properties/_query/";

        System.out.println(url);
        HttpRequest request = new SimpleHttpRequest(url);
        String body = "{\n" +
                "\t\"pageSize\": 25,\n" +
                "\t\"pageIndex\": 0,\n" +
                "\t\"terms\": [{\n" +
                "\t\t\"column\": \"property\",\n" +
                "\t\t\"value\": \"temperature\"\n" +
                "\t}]\n" +
                "}";

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

    /**
     * 查询设备事件测试
     */
    @Test
    void queryDeviceEventsTest() {
        String eventId = "fire_alarm";
        String url = base_url + "/" + productId + "/event/" + eventId + "/_query/";

        System.out.println(url);
        HttpRequest request = new SimpleHttpRequest(url);
        String body = "{\n" +
                "\t\"pageSize\": 25,\n" +
                "\t\"pageIndex\": 0,\n" +
                "\t\"terms\": [{\n" +
                "\t\t\"column\": \"deviceId\",\n" +
                "\t\t\"value\": \"test001\"\n" +
                "\t}]\n" +
                "}";

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
