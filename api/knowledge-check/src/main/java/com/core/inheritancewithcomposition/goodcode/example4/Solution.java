package com.core.inheritancewithcomposition.goodcode.example4;

import java.util.Map;

class Solution {
    public static void main(String[] args) {
        Map<String, String> data = Map.of("name", "John Doe", "age", "30");
        FormatType formatType = FormatType.CSV;

        switch (formatType) {
            case CSV:
                Formatter csvFormatter = new CSVFormatter();
                System.out.println(csvFormatter.format(data));
                break;

            case JSON:
                Formatter jsonFormatter = new JSONFormatter();
                System.out.println(jsonFormatter.format(data));
                break;

            case HTML:
                Formatter htmlFormatter = new HTMLFormatter();
                System.out.println(htmlFormatter.format(data));
                break;

            default:
                throw new IllegalArgumentException("Unknown format type");
        }

    }
}

enum FormatType { CSV, JSON, HTML }

/**
 * solution
 */
interface Formatter {

    String format(Map<String, String> data);


}

class CSVFormatter implements Formatter {
    @Override
    public String format(Map<String, String> data) {
        return this.formatCSV(data);
    }

    private String formatCSV(Map<String, String> data) {
        StringBuilder csv = new StringBuilder();
        for (Map.Entry<String, String> entry : data.entrySet()) {
            csv.append(entry.getKey()).append(",").append(entry.getValue()).append("\n");
        }
        return csv.toString();
    }

}

class JSONFormatter implements Formatter {

    @Override
    public String format(Map<String, String> data) {
        return this.formatJSON(data);
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

}

class HTMLFormatter implements Formatter {
    @Override
    public String format(Map<String, String> data) {
        return this.formatHTML(data);
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