package com.inventory;

import java.util.Scanner;

public class Main {

    private static InventoryManager manager = new InventoryManager();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {




        System.out.println("Welcome to Inventory Management System!");
        loadSampleData();

        boolean running = true;

        while (running) {
            System.out.println("=== MAIN MENU ===");
            System.out.println("1. Add Product");
            System.out.println("2. View Inventory");
            System.out.println("3. Sell Product");
            System.out.println("4. Add Stock");
            System.out.println("5. View Statistics");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    // Prompt and call addProduct
                    break;
                case 2:
                    // Call viewInventory
                    break;
                case 3:
                    // Prompt and call sellProduct
                    break;
                case 4:
                    // Prompt and call addStock
                    break;
                case 5:
                    // Call getInventoryValue or getLowStockProducts
                    break;
                case 6:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    /**
     * TODO: Load sample data for testing
     */
    private static void loadSampleData() {
        // TODO: Add sample products for testing
        // Example: Books and Electronics with different prices
        manager.addProduct("B001", "Java Programming", "BOOK", 29.99, 10);
        manager.addProduct("B002", "Design Patterns", "BOOK", 35.50, 8);
        manager.addProduct("E001", "Laptop", "ELECTRONICS", 999.99, 5);
        manager.addProduct("E002", "Mouse", "ELECTRONICS", 25.99, 15);
    }

}
