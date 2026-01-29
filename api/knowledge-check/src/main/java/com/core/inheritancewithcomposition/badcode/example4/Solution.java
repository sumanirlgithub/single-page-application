package com.core.inheritancewithcomposition.badcode.example4;

import java.util.Map;

/**
 * you'll apply what you've learned about clean coding principles such as interface segregation and code organization.
 * You will refactor the given code by introducing an interface for formatting data into different styles like CSV, JSON, and HTML.
 * The current code format method handles multiple formats and is too complex to understand easily. By introducing the Formatter interface with specific
 * implementations for each format, you'll make the code more modular and maintainable.
 *
 * The initial code is a single class responsible for formatting data in multiple ways, which leads to a breach of the single responsibility principle.
 * Let's tidy it up by separating concerns using interfaces!
 */
class Solution {
    public static void main(String[] args) {
        Map<String, String> data = Map.of("name", "John Doe", "age", "30");
        FormatType formatType = FormatType.CSV;

        Formatter formatter = new Formatter();
        System.out.println(formatter.format(data, formatType));
    }
}

enum FormatType { CSV, JSON, HTML }

class Formatter {
    String format(Map<String, String> data, FormatType formatType) {
        switch (formatType) {
            case CSV:
                return formatCSV(data);
            case JSON:
                return formatJSON(data);
            case HTML:
                return formatHTML(data);
            default:
                throw new IllegalArgumentException("Unknown format type");
        }
    }

    private String formatCSV(Map<String, String> data) {
        StringBuilder csv = new StringBuilder();
        for (Map.Entry<String, String> entry : data.entrySet()) {
            csv.append(entry.getKey()).append(",").append(entry.getValue()).append("\n");
        }
        return csv.toString();
    }

    private String formatJSON(Map<String, String> data) {
        StringBuilder json = new StringBuilder("{\n");
        for (Map.Entry<String, String> entry : data.entrySet()) {
            json.append("  \"").append(entry.getKey()).append("\": \"").append(entry.getValue()).append("\",\n");
        }
        json.delete(json.length() - 2, json.length()); // Remove last comma
        json.append("\n}");
        return json.toString();
    }

    private String formatHTML(Map<String, String> data) {
        StringBuilder html = new StringBuilder("<table>\n");
        for (Map.Entry<String, String> entry : data.entrySet()) {
            html.append("  <tr><td>").append(entry.getKey()).append("</td><td>").append(entry.getValue()).append("</td></tr>\n");
        }
        html.append("</table>");
        return html.toString();
    }
}