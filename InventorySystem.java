import java.sql.*;
import java.util.Scanner;

public class InventorySystem {
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        try {
            while (true) {
                showMenu();
                int choice = scanner.nextInt();
                scanner.nextLine(); // Clear buffer
                
                switch (choice) {
                    case 1: addItem(); break;
                    case 2: viewItems(); break;
                    case 3: updateItem(); break;
                    case 4: deleteItem(); break;
                    case 5: checkStock(); break;
                    case 6: 
                        System.out.println("Goodbye!");
                        return;
                    default: 
                        System.out.println("Invalid choice!");
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
    
    private static void showMenu() {
        System.out.println("\n=== Inventory System ===");
        System.out.println("1. Add item");
        System.out.println("2. View items");
        System.out.println("3. Update item");
        System.out.println("4. Delete item");
        System.out.println("5. Check stock");
        System.out.println("6. Exit");
        System.out.print("Choice: ");
    }
    
    private static void addItem() {
        try (Connection conn = Database.getConnection()) {
            System.out.print("Name: ");
            String name = scanner.nextLine();
            
            System.out.print("Price: ");
            double price = scanner.nextDouble();
            
            System.out.print("Quantity: ");
            int quantity = scanner.nextInt();
            
            String sql = "INSERT INTO items (name, price, quantity) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, name);
                pstmt.setDouble(2, price);
                pstmt.setInt(3, quantity);
                pstmt.executeUpdate();
                System.out.println("Item added!");
            }
        } catch (SQLException e) {
            System.out.println("Error adding item: " + e.getMessage());
        }
    }
    
    private static void viewItems() {
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM items")) {
            
            System.out.println("\nID\tName\t\tPrice\tQuantity");
            System.out.println("----------------------------------------");
            
            while (rs.next()) {
                System.out.printf("%d\t%-15s\t%.2f\t%d\n",
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getInt("quantity"));
            }
        } catch (SQLException e) {
            System.out.println("Error viewing items: " + e.getMessage());
        }
    }
    
    private static void updateItem() {
        try (Connection conn = Database.getConnection()) {
            System.out.print("Enter ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            
            System.out.print("New name: ");
            String name = scanner.nextLine();
            
            System.out.print("New price: ");
            double price = scanner.nextDouble();
            
            System.out.print("New quantity: ");
            int quantity = scanner.nextInt();
            
            String sql = "UPDATE items SET name=?, price=?, quantity=? WHERE id=?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, name);
                pstmt.setDouble(2, price);
                pstmt.setInt(3, quantity);
                pstmt.setInt(4, id);
                
                if (pstmt.executeUpdate() > 0) {
                    System.out.println("Updated!");
                } else {
                    System.out.println("Item not found!");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error updating item: " + e.getMessage());
        }
    }
    
    private static void deleteItem() {
        try (Connection conn = Database.getConnection()) {
            System.out.print("Enter ID: ");
            int id = scanner.nextInt();
            
            String sql = "DELETE FROM items WHERE id=?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                
                if (pstmt.executeUpdate() > 0) {
                    System.out.println("Deleted!");
                } else {
                    System.out.println("Item not found!");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error deleting item: " + e.getMessage());
        }
    }
    
    private static void checkStock() {
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM items WHERE quantity < 10")) {
            
            System.out.println("\nLow Stock Items:");
            System.out.println("Name\t\tQuantity");
            System.out.println("------------------------");
            
            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.printf("%-15s\t%d\n",
                    rs.getString("name"),
                    rs.getInt("quantity"));
            }
            
            if (!found) {
                System.out.println("No low stock items!");
            }
        } catch (SQLException e) {
            System.out.println("Error checking stock: " + e.getMessage());
        }
    }
} 