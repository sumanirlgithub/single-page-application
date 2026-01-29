package com.core.abstractclassexample.badcode;

class Solution {
    public static void main(String[] args) {
        TextDocument textDocument = new TextDocument();
        SpreadsheetDocument spreadsheetDocument = new SpreadsheetDocument();

        textDocument.open();
        textDocument.process();
        textDocument.close();

        spreadsheetDocument.open();
        spreadsheetDocument.process();
        spreadsheetDocument.close();
    }
}

class TextDocument {
    public void open() {
        System.out.println("Opening document");
    }

    public void process() {
        System.out.println("Processing text document");
    }

    public void close() {
        System.out.println("Closing document");
    }
}

class SpreadsheetDocument {
    public void open() {
        System.out.println("Opening document");
    }

    public void process() {
        System.out.println("Processing spreadsheet document");
    }

    public void close() {
        System.out.println("Closing document");
    }
}