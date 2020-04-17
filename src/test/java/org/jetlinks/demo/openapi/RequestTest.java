package org.jetlinks.demo.openapi;

import com.alibaba.fastjson.JSON;
import org.jetlinks.demo.openapi.util.Utils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangzheng
 * @see
 * @since 1.0
 */
class RequestTest {

    private static final String base_url = "http://localhost:8844/api/v1/device";
    private static final String deviceId = "test001";

    /**
     * 获取设备属性测试
     */
    @Test
    void getPropertyTest() {

        String property = "temperature";//温度属性，属性在设备管理的设备型号中设置
        String url = base_url + "/" + deviceId + "/property/" + property;

        HttpRequest request = new SimpleHttpRequest(url);
        request.headers(HeaderUtils.createHeadersOfParams(new HashMap<>()));
        System.out.println(HeaderUtils.createHeadersOfParams(new HashMap<>()));
        try {

            Response response = request.get();
            Map<String, String> result = Utils.queryStringToMap(new String(response.asBytes(), "utf8"), "utf8");

            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 设置设备属性测试
     */
    @Test
    void writePropertiesTest() {
        String url = base_url + "/" + deviceId + "/properties";

        System.out.println(url);
        HttpRequest request = new SimpleHttpRequest(url);
        Map<String, Object> params = new HashMap<>();
        params.put("temperature", 50F);

        request.headers(HeaderUtils.createHeadersOfJsonString(JSON.toJSONString(params)));
        System.out.println("Headers:===========>" + HeaderUtils.createHeadersOfParams(params));
        request.requestBody(JSON.toJSONString(params));

        try {
            Response response = request.post();
            Map<String, String> result = Utils.queryStringToMap(new String(response.asBytes(), "utf8"), "utf8");
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设备功能调用测试
     */
    @Test
    void invokeFunctionTest() {
        String functionId = "get-log";
        String url = base_url + "/" + deviceId + "/function/" + functionId;

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

    /**
     * 获取设备详情测试
     */
    @Test
    void getDeviceDetailTest() {

        String url = base_url + "/" + deviceId + "/_detail";

        HttpRequest request = new SimpleHttpRequest(url);
        request.headers(HeaderUtils.createHeadersOfParams(new HashMap<>()));
        System.out.println(HeaderUtils.createHeadersOfParams(new HashMap<>()));
        try {

            Response response = request.get();
            Map<String, String> result = Utils.queryStringToMap(new String(response.asBytes(), "utf8"), "utf8");

            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 根据设备ID类型和动态查询参数查询设备相关数据测试
     */
    @Test
    void queryDeviceLogPostTest() {
        String url = base_url + "/" + deviceId + "/log/_query";

        System.out.println(url);
        HttpRequest request = new SimpleHttpRequest(url);

        //String body = "{\"pageSize\":25,\"pageIndex\":0,\"where\":\"productId is 1236859833832701952\"}";
        String body = "{\"pageSize\":25,\"pageIndex\":0,\"terms\":[{\"column\":\"productId\",\"value\":\"1236859833832701952\"}]}";
        request.headers(HeaderUtils.createHeadersOfJsonString(body));
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
     * 查询设备列表测试
     */
    @Test
    void getDeviceDetailPostTest() {
        String url = base_url + "/_query";

        System.out.println(url);
        HttpRequest request = new SimpleHttpRequest(url);

        //String body = "{\"pageSize\":25,\"pageIndex\":0,\"where\":\"productId is 1236859833832701952\"}";
        String body = "{\"pageSize\":25,\"pageIndex\":0,\"terms\":[{\"column\":\"productId\",\"value\":\"1236859833832701952\"}]}";
        request.headers(HeaderUtils.createHeadersOfJsonString(body));
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
    void queryDevicePropertiesPostTest() {
        String url = base_url + "/" + deviceId + "/properties/_query";

        System.out.println(url);
        HttpRequest request = new SimpleHttpRequest(url);

        String body = "{\n" +
                "              \"pageSize\":25,\n" +
                "              \"pageIndex\":0,\n" +
                "              \"terms\":[\n" +
                "                 {\n" +
                "                   \"column\":\"property\",\n" +
                "                   \"value\":\"temperature\"\n" +
                "                 }\n" +
                "              ]\n" +
                "          }";

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
     * 获取设备最新的全部属性测试
     */
    @Test
    void queryDevicePropertiesTest() {

        String url = base_url + "/" + deviceId + "/properties/_latest";

        HttpRequest request = new SimpleHttpRequest(url);
        request.headers(HeaderUtils.createHeadersOfParams(new HashMap<>()));
        System.out.println(HeaderUtils.createHeadersOfParams(new HashMap<>()));
        try {

            Response response = request.get();
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

        String url = base_url + "/" + deviceId + "/event/" + eventId + "/_query";

        System.out.println(url);
        HttpRequest request = new SimpleHttpRequest(url);

        String body = "{\n" +
                "              \"pageSize\":25,\n" +
                "              \"pageIndex\":0,\n" +
                "              \"terms\":[\n" +
                "                 {\n" +
                "                   \"column\":\"a_name\",\n" +
                "                   \"value\":\"未来科技城\"\n" +
                "                 }\n" +
                "              ]\n" +
                "          }";

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
