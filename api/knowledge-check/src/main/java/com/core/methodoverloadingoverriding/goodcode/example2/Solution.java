package com.core.methodoverloadingoverriding.goodcode.example2;

import java.util.Map;

class Solution {
    public static void main(String[] args) {
        Formatter csvFormatter = new CSVFormatter();
        Formatter jsonFormatter = new JSONFormatter();
        System.out.println(csvFormatter.format("Hello World"));
        System.out.println(jsonFormatter.format(Map.of("key1", "value1", "key2", "value2")));
        System.out.println(csvFormatter.format(new String[]{"one", "two", "three"}));
    }
}

enum FormatType {
    CSV, JSON
}

interface Formatter {
    String format(String content);
    String format(String[] content);
    String format(Map<String, String> content);
}

class CSVFormatter implements Formatter {

    @Override
    public String format(String content) {
        return ((String) content).replaceAll(",", "\\,");
    }

    @Override
    public String format(String[] content) {
        StringBuilder result = new StringBuilder();
        result.append(String.join(",", (String[]) content));
        return result.toString();
    }

    @Override
    public String format(Map<String, String> content) {
        StringBuilder result = new StringBuilder();
        Map<?, ?> mapContent = (Map<?, ?>) content;
        mapContent.forEach((key, value) -> result.append(key).append(",").append(value).append("\n"));
        return result.toString();
    }

}

class JSONFormatter implements Formatter {

    @Override
    public String format(String content) {
        return "\"" + ((String) content).replace("\"", "\\\"") + "\"";
    }

    @Override
    public String format(String[] content) {
        StringBuilder result = new StringBuilder();
        result.append("[");
        for (String element : (String[]) content) {
            result.append("\"").append(element).append("\",");
        }
        if (result.length() > 1) result.setLength(result.length() - 1);
        result.append("]");

        return result.toString();
    }

    @Override
    public String format(Map<String, String> content) {
        StringBuilder result = new StringBuilder();
        Map<?, ?> mapContent = (Map<?, ?>) content;
        mapContent.forEach((key, value) -> result.append("\"").append(key).append("\":\"").append(value).append("\","));
        if (result.length() > 1) result.setLength(result.length() - 1);
        result.append("}");
        return result.toString();
    }

}

