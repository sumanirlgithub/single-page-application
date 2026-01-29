package com.core.methodoverloadingoverriding.badcode.example2;

import java.util.Map;

/**
 * we'll focus on designing a flexible and clean way to handle data formatting. The current starter code is tangled with conditions that unnecessarily
 * complicate the logic of formatting data into CSV or JSON formats. Your task is to refactor the code by introducing a Formatter interface with concrete
 * implementations for each format type. This refactoring will streamline the code, making it more maintainable and easy to extend. The Data class,
 * which acted as an intermediary, should be removed to allow users direct access to specific formatters based on their needs. Let's bring all of your
 * clean coding skills together!
 */
class Solution {
    public static void main(String[] args) {
        Data data = new Data();
        System.out.println(data.format("Hello World", FormatType.CSV));
        System.out.println(data.format(Map.of("key1", "value1", "key2", "value2"), FormatType.JSON));
        System.out.println(data.format(new String[]{"one", "two", "three"}, FormatType.CSV));
    }
}

enum FormatType {
    CSV, JSON
}

class Data {
    public String format(Object content, FormatType formatType) {
        if (content instanceof String) {
            if (formatType == FormatType.CSV) {
                return ((String) content).replaceAll(",", "\\,");
            } else {
                return "\"" + ((String) content).replace("\"", "\\\"") + "\"";
            }
        } else if (content instanceof Map) {
            StringBuilder result = new StringBuilder();
            Map<?, ?> mapContent = (Map<?, ?>) content;
            if (formatType == FormatType.CSV) {
                mapContent.forEach((key, value) -> result.append(key).append(",").append(value).append("\n"));
            } else {
                result.append("{");
                mapContent.forEach((key, value) -> result.append("\"").append(key).append("\":\"").append(value).append("\","));
                if (result.length() > 1) result.setLength(result.length() - 1);
                result.append("}");
            }
            return result.toString();
        } else if (content instanceof String[]) {
            StringBuilder result = new StringBuilder();
            if (formatType == FormatType.CSV) {
                result.append(String.join(",", (String[]) content));
            } else {
                result.append("[");
                for (String element : (String[]) content) {
                    result.append("\"").append(element).append("\",");
                }
                if (result.length() > 1) result.setLength(result.length() - 1);
                result.append("]");
            }
            return result.toString();
        } else {
            throw new IllegalArgumentException("Unrecognized data type");
        }
    }
}