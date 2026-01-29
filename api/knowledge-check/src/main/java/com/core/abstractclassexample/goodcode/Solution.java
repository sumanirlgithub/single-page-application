package com.core.abstractclassexample.goodcode;

class Solution {
    public static void main(String[] args) {
        TextDocument textDocument = new TextDocument();
        SpreadsheetDocument spreadsheetDocument = new SpreadsheetDocument();

        textDocument.processDocument();

        spreadsheetDocument.processDocument();
    }
}

abstract class Document {
    public void open() {
        System.out.println("Opening document");
    }

    public void close() {
        System.out.println("Closing document");
    }

    abstract void process();

    public void processDocument() {
        open();
        process();
        close();
    }
}

class TextDocument extends Document {

    public void process() {
        System.out.println("Processing text document");
    }

}

class SpreadsheetDocument extends Document {
    public void process() {
        System.out.println("Processing spreadsheet document");
    }

}