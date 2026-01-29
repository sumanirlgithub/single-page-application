package com.designpattern.singleton;

/**
 * PrinterManager - Controls access to the office printer
 * This is a Singleton because we only want ONE manager for our printer
 */
public class PrinterManager {

    // Step 1: Keep the single instance here (like having one manager's desk)
    private static PrinterManager singleManager = null;

    // Step 2: Track printer status
    private boolean printerBusy = false;
    private String currentJob = "";

    // Step 3: Private constructor - nobody else can create a manager!
    private PrinterManager() {
        System.out.println("🖨️ PrinterManager created - I'm in charge of the printer!");
    }

    // Step 4: The ONLY way to get the manager
    public static PrinterManager getManager() {
        if (singleManager == null) {
            singleManager = new PrinterManager();
        }
        return singleManager;
    }

    // Method to print a document
    public void printDocument(String document, String person) {
        if (printerBusy) {
            System.out.println("❌ Sorry " + person + ", printer is busy with: " + currentJob);
            return;
        }

        printerBusy = true;
        currentJob = document + " (by " + person + ")";

        System.out.println("🖨️ Printing: " + document + " for " + person);

        // Simulate printing time
        try {
            Thread.sleep(2000); // 2 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("✅ Finished printing: " + document);
        printerBusy = false;
        currentJob = "";
    }

    // Check printer status
    public void checkStatus() {
        if (printerBusy) {
            System.out.println("📋 Printer Status: BUSY - " + currentJob);
        } else {
            System.out.println("📋 Printer Status: READY");
        }
    }

    // Getter methods for testing
    public boolean isPrinterBusy() {
        return printerBusy;
    }

    public String getCurrentJob() {
        return currentJob;
    }
}