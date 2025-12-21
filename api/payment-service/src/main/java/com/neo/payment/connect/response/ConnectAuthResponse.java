package com.neo.payment.connect.response;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class ConnectAuthResponse {

    private byte[] rowResponse;

    private String decryptedContent;

    private int statusCode;

    private final Map<String, String> responseHeaders = new HashMap<>();

    public String extractAuthToken() {
        String content = this.getDecryptedContent();
        String target = extractByRegex(content, "\"access_token\":\\s*\"(.*?)\"");
        return StringUtils.trim(target);
    }

    private static String extractByRegex(String source, String expression) {
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(source);
        return matcher.find() ? matcher.group(1) : null;
    }
}
