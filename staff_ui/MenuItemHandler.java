

package staff_ui;

import java.util.InputMismatchException;
import java.util.Scanner;
import branch_management.Menu;
import branch_management.Branch;
import branch_management.FoodCategory;
import db.BranchDBController;
import branch_management.MenuItem;
/**
Provides functionality for branch managers to edit the menu.
This class allows managers to add new menu items, remove items, edit prices, and update the availability of items on the menu.
*/
public class MenuItemHandler {
    private Scanner scanner;
    private Menu menu;

    public MenuItemHandler(Scanner scanner, int branchId, BranchDBController branchDBController) {
        this.scanner = scanner;
        Branch branch = branchDBController.getBranchById(branchId);
        this.menu = branch.getMenu();
    }

    // Method to add item
    public void addItem() {
        System.out.println("\n\nAdding new item");
        System.out.println("\nEnter new menu item name: ");
        String name = scanner.nextLine();
        
        
        double price;
        try{
            System.out.println("\nEnter the new price: ");
            price = scanner.nextDouble();
        }catch(InputMismatchException e){
            System.out.println("Invalid input. Returning to Main Page");
            scanner.nextLine();
            return;
        }
        scanner.nextLine(); // consume newline
        System.out.println("\nSelect the Food Category:");
        FoodCategory category = selectFoodCategory();
        System.out.println("\nEnter description for the menu item:");
        String description = scanner.nextLine();

        boolean success = menu.addItem(menu.getFoodList().size() + 1, name, price, category, true, description);
        if (success) {
            System.out.println("\nItem added successfully.");
        } else {
            System.out.println("\nFailed to add item. Item with name '" + name + "' already exists.");
        }
    }

    public void removeItem() {
        System.out.println("\n\nEnter menu item name to be removed: ");
        String name = scanner.nextLine();
        boolean success = menu.removeFoodByName(name);
        if (success) {
            System.out.println("\nItem removed successfully.");
        } else {
            System.out.println("\nFailed to remove item. Item not found.");
        }
    }

    public void editPrice() {
        System.out.println("\n\nEnter the name of the item to edit price: ");
        String itemName = scanner.nextLine();

        double newPrice;
        try{
            System.out.println("\nEnter the new price: ");
            newPrice = scanner.nextDouble();
            scanner.nextLine(); // consume newline left over
        }catch(InputMismatchException e){
            
            System.out.println("Invalid input. Returning to Main Page.");
            scanner.nextLine();
            return;
        }

        // Update the price
        boolean success = menu.updateFoodPrice(itemName, newPrice);
        if (success) {
            System.out.println("\nPrice of item '" + itemName + "' updated successfully to: $" + newPrice);
        } else {
            System.out.println("\nFailed to update price. Item not found.");
        }
    }

    public void editAvailability() {
        System.out.println("\n\n Enter the name of the item to edit availability: ");
        String itemName = scanner.nextLine();

        System.out.println("\n Enter the new availability (true/false): ");
        boolean newAvailability = scanner.nextBoolean();
        scanner.nextLine(); // consume newline left over

        // Update its availability
        MenuItem item = menu.findFoodByName(itemName);
        if (item != null) {
            item.setAvailable(newAvailability);
            System.out.println("\n Availability of item '" + itemName + "' updated successfully to: " + newAvailability);
        } else {
            System.out.println("\nFailed to update availability. Item not found.");
        }
    }

    // Helper method to select food category
    public FoodCategory selectFoodCategory() {
        while (true) {
            System.out.println("1. BURGER");
            System.out.println("2. SIDE");
            System.out.println("3. DRINK");
            System.out.println("4. SET_MEAL");
            System.out.print("Enter your choice (1-4): ");
            int categoryChoice;
            try{
                categoryChoice = scanner.nextInt();
            }catch(InputMismatchException e){
                System.out.println("Invalid input. Returning to Main Page");
                scanner.nextLine();
                continue;
            }

            
            scanner.nextLine(); // consume newline left over
            switch (categoryChoice) {
                case 1:
                    return FoodCategory.BURGER;
                case 2:
                    return FoodCategory.SIDE;
                case 3:
                    return FoodCategory.DRINK;
                case 4:
                    return FoodCategory.SET_MEAL;
                default:
                    System.out.println("\nInvalid choice. Please enter a number between 1 and 4.\n");
            }
        }
    }

    public void viewMenu(){
        System.out.println('\n');
        for (MenuItem food : this.menu.getFoodList()) {
			
            System.out.println("ID: " + food.getId() +
                    "\t Name: " + food.getName() +
                    "\t Price: " + food.getPrice() +
                    "\t Category: " + food.getCategory() +
                    "\t Availability: " + food.isAvailable() + 
                    "\t Description: " + food.getDescription()+
                    "\n");
		
        }
    }
}
