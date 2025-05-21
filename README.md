# Simple Inventory System

A basic inventory management system in Java.

## Setup

1. Create database:
   - Run `database.sql` in PostgreSQL
   - Update password in `Database.java`

2. Compile:
   ```
   javac Database.java 
   javac Item.java
   javac InventorySystem.java
   ```

3. Run:
   ```
   java InventorySystem
   ```

## Files
- `InventorySystem.java` - Main program
- `Database.java` - Database connection
- `Item.java` - Item class
- `database.sql` - Database setup 