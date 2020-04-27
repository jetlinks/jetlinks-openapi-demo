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
public class GeoApiTest {

    private static final String base_url = "http://localhost:8844/api/v1/geo/object";

    /**
     * 查询geo对象测试
     */
    @Test
    void findGeoObjectTest() {
        String url = base_url + "/_search";

        System.out.println(url);
        HttpRequest request = new SimpleHttpRequest(url);
//        String body = "{" +
//                "\"shape\": {" +
//                "\"objectId\": \"youyang\" " +
//                "}," +
//                "\"filter\": {" +
//                "" +
//                "}" +
//                "}";

        //shape
        String body = "{\n" +
                "\t\"shape\": {\n" +
                "\t\t\"type\": \"Polygon\",\n" +
                "\t\t\"coordinates\": [\n" +
                "\t\t\t[\n" +
                "\t\t\t\t[108.3142, 28.9984],\n" +
                "\t\t\t\t[108.3252, 29.0039],\n" +
                "\t\t\t\t[108.3252, 28.96],\n" +
                "\t\t\t\t[108.3142, 28.9984]\n" +
                "\t\t\t]\n" +
                "\t\t]\n" +
                "\t},\n" +
                "\t\"filter\": {\n" +
                "\n" +
                "\t}\n" +
                "}";
        //filter
//        String body = "{\n" +
//                "\t\"shape\": {\n" +
//                "\t\t\"objectId\": \"youyang\"\n" +
//                "\t},\n" +
//                "\t\"filter\": {\n" +
//                "\t\t\"name\": \"酉阳土家族苗族自治县\",\n" +
//                "\t\t\"group\": \"china\"\n" +
//                "\t}\n" +
//                "\n" +
//                "}";

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
     * 查询geo对象并转为geoJson测试
     */
    @Test
    void findGeoObjectToGeoJsonTest() {
        String url = base_url + "/_search/geo.json";

        System.out.println(url);
        HttpRequest request = new SimpleHttpRequest(url);
        String body = "{" +
                "\"shape\": {" +
                "\"objectId\": \"youyang\" " +
                "}," +
                "\"filter\": {" +
                "" +
                "}" +
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
     * 根据geo json保存数据. 可使用 http://geojson.io/#map=4/32.18/105.38 生成json
     */
    @Test
    void saveByGeoJsonTest() {
        String url = base_url + "/geo.json";

        System.out.println(url);
        HttpRequest request = new SimpleHttpRequest(url);
        String body = "{\"type\":\"FeatureCollection\",\"features\":[{\"type\":\"Feature\",\"properties\":{\"id\":\"youyang\",\"objectId\":\"youyang\",\"objectType\":\"city\",\"name\":\"酉阳土家族苗族自治县\",\"group\":\"china\"},\"geometry\":{\"type\":\"Polygon\",\"coordinates\":[[[108.3142,28.9984],[108.3252,29.0039],[108.3252,28.96],[108.3142,28.9984]]]}}]}";

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


    private String getChongQingGeoJson() {
        HttpRequest request = new SimpleHttpRequest("https://a.amap.com/jsapi_demos/static/geojson/chongqing.json");
        String result = "";
        try {
            Response response = request.get();
            result = new String(response.asBytes());
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
