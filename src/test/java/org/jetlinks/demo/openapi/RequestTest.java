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
     * 批量保存设备测试
     */
    @Test
    void saveDeviceTest() {
        String url = base_url;

        HttpRequest request = new SimpleHttpRequest(url);

        String body = "[{\n" +
                "\t\t\"id\": \"test002\",\n" +
                "\t\t\"name\": \"设备002\",\n" +
                "\t\t\"productId\": \"1236859833832701952\",\n" +
                "\t\t\"configuration\": {\n" +
                "\t\t\t\"username\": \"test002\",\n" +
                "\t\t\t\"password\": \"test002\"\n" +
                "\t\t},\n" +
                "\t\t\"tags\": [{\n" +
                "\t\t\t\"deviceId\": \"test002\",\n" +
                "\t\t\t\"key\": \"area\",\n" +
                "\t\t\t\"name\": \"地区\",\n" +
                "\t\t\t\"value\": \"chongqing\"\n" +
                "\t\t}]\n" +
                "\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"id\": \"test003\",\n" +
                "\t\t\"name\": \"设备名称\",\n" +
                "\t\t\"productId\": \"1236859833832701952\",\n" +
                "\t\t\"configuration\": {}\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"id\": \"test004\",\n" +
                "\t\t\"name\": \"设备名称\",\n" +
                "\t\t\"productId\": \"1236859833832701952\",\n" +
                "\t\t\"configuration\": {}\n" +
                "\t}\n" +
                "]";
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
     * 批量激活/注销设备测试
     */
    @Test
    void batchDeployTest() {
        String url = base_url + "/_deploy";//激活
        //String url = base_url + "/_unDeploy";//注销

        HttpRequest request = new SimpleHttpRequest(url);

        String body = "[\"test003\",\"test004\"]";
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
     * 批量删除设备测试（只会删除状态为未激活的设备）
     */
    @Test
    void batchDeleteTest() {
        String url = base_url + "/_delete";

        HttpRequest request = new SimpleHttpRequest(url);

        String body = "[\"test002\",\"test003\",\"test004\"]";
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
