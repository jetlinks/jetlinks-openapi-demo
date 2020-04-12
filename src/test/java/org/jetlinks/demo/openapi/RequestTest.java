package org.jetlinks.demo.openapi;

import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.jetlinks.demo.openapi.util.Utils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

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
        String url = base_url +"/" + deviceId + "/property/" + property;

        HttpRequest request = new SimpleHttpRequest(url);
        request.headers(createHeadersOfParams(new HashMap<>()));
        System.out.println(createHeadersOfParams(new HashMap<>()));
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

        request.headers(createHeadersOfJsonString(JSON.toJSONString(params)));
        System.out.println("Headers:===========>"+ createHeadersOfParams(params));
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

        request.headers(createHeadersOfJsonString(body));
        System.out.println("Headers:===========>"+ createHeadersOfJsonString(body));
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

        String url = base_url +"/" + deviceId + "/_detail";

        HttpRequest request = new SimpleHttpRequest(url);
        request.headers(createHeadersOfParams(new HashMap<>()));
        System.out.println(createHeadersOfParams(new HashMap<>()));
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
        request.headers(createHeadersOfJsonString(body));
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
        request.headers(createHeadersOfJsonString(body));
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

        request.headers(createHeadersOfJsonString(body));
        System.out.println("Headers:===========>"+ createHeadersOfJsonString(body));
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

        String url = base_url +"/" + deviceId + "/properties/_latest";

        HttpRequest request = new SimpleHttpRequest(url);
        request.headers(createHeadersOfParams(new HashMap<>()));
        System.out.println(createHeadersOfParams(new HashMap<>()));
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

        request.headers(createHeadersOfJsonString(body));
        System.out.println("Headers:===========>"+ createHeadersOfJsonString(body));
        request.requestBody(body);

        try {
            Response response = request.post();
            Map<String, String> result = Utils.queryStringToMap(new String(response.asBytes(), "utf8"), "utf8");
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<String, String> createHeadersOfParams(Map<String, Object> params) {
        //时间戳
        String xTimestamp = String.valueOf(new Date().getTime());
        //openApi客户端id
        String xClientId = "kFpJfaXB2AS5zHRZ";
        //密钥
        String secureKey = "xHZ2MyCG5TEdntyFhX72Gx6K";
        //将参数按ASCII排序后拼接为k1=v1&k2=v2的格式
        String paramString = new TreeMap<>(params).entrySet()
                .stream()
                .map(e -> e.getKey().concat("=").concat(String.valueOf(e.getValue())))
                .collect(Collectors.joining("&"))
                ;

        System.out.println(paramString);

        //param+X-Timestamp+SecureKey通过MD5加密
        MessageDigest digest = DigestUtils.getMd5Digest();
        System.out.println(paramString+xTimestamp+secureKey);
        digest.update(paramString.getBytes());
        digest.update(xTimestamp.getBytes());
        digest.update(secureKey.getBytes());

        Map<String, String> headers = new HashMap<>();
        headers.put("X-Sign", Hex.encodeHexString(digest.digest()));
        headers.put("X-Client-Id", xClientId);
        headers.put("X-Timestamp", xTimestamp);

        return headers;
    }


    private Map<String, String> createHeadersOfJsonString(String jsonString) {
        //时间戳
        String xTimestamp = String.valueOf(new Date().getTime());
        //openApi客户端id
        String xClientId = "kFpJfaXB2AS5zHRZ";
        //密钥
        String secureKey = "xHZ2MyCG5TEdntyFhX72Gx6K";


        //param+X-Timestamp+SecureKey通过MD5加密
        MessageDigest digest = DigestUtils.getMd5Digest();
        System.out.println(jsonString+xTimestamp+secureKey);
        digest.update(jsonString.getBytes());
        digest.update(xTimestamp.getBytes());
        digest.update(secureKey.getBytes());

        Map<String, String> headers = new HashMap<>();
        headers.put("X-Sign", Hex.encodeHexString(digest.digest()));
        headers.put("X-Client-Id", xClientId);
        headers.put("X-Timestamp", xTimestamp);

        return headers;
    }
}
