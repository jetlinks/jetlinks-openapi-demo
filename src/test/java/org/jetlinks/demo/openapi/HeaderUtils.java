package org.jetlinks.demo.openapi;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

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
public class HeaderUtils {

    public static Map<String, String> createHeadersOfParams(Map<String, Object> params) {
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
                .collect(Collectors.joining("&"));

        System.out.println(paramString);

        //param+X-Timestamp+SecureKey通过MD5加密
        MessageDigest digest = DigestUtils.getMd5Digest();
        System.out.println(paramString + xTimestamp + secureKey);
        digest.update(paramString.getBytes());
        digest.update(xTimestamp.getBytes());
        digest.update(secureKey.getBytes());

        Map<String, String> headers = new HashMap<>();
        headers.put("X-Sign", Hex.encodeHexString(digest.digest()));
        headers.put("X-Client-Id", xClientId);
        headers.put("X-Timestamp", xTimestamp);

        return headers;
    }


    static Map<String, String> createHeadersOfJsonString(String jsonString) {
        //时间戳
        String xTimestamp = String.valueOf(new Date().getTime());
        //openApi客户端id
        String xClientId = "kFpJfaXB2AS5zHRZ";
        //密钥
        String secureKey = "xHZ2MyCG5TEdntyFhX72Gx6K";


        //param+X-Timestamp+SecureKey通过MD5加密
        MessageDigest digest = DigestUtils.getMd5Digest();
        System.out.println(jsonString + xTimestamp + secureKey);
        digest.update(jsonString.getBytes());
        digest.update(xTimestamp.getBytes());
        digest.update(secureKey.getBytes());

        Map<String, String> headers = new HashMap<>();
        headers.put("X-Sign", Hex.encodeHexString(digest.digest()));
        headers.put("X-Client-Id", xClientId);
        headers.put("X-Timestamp", xTimestamp);

        System.out.println(Hex.encodeHexString(digest.digest()) +"|"+xClientId+"|"+ xTimestamp);
        return headers;
    }
}
