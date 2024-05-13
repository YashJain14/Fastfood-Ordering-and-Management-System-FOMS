

package branch_management;

import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.Optional;
/**
Represents a menu containing a list of food items.
This class provides methods to:
Add new food items to the menu with description.
Remove food items by ID or name.
Update the price of a food item.
Find food items by ID or name.
Get the list of all food items.
Print the menu for verification and debugging.
Save the menu to a file for persistence.
*/
public class Menu {
    private List<MenuItem> foodList = new ArrayList<>();
    private static int nextItemNo = 1;

    public Menu() {
        this.foodList = new ArrayList<>();
    }

    // Updated addItem method to include description
    public boolean addItem(String name, double price, FoodCategory category, boolean availability, String description) {
        if (findFoodByName(name) == null) {
            return false; // Item already exists, return false to indicate failure
        }
        MenuItem newItem = new MenuItem(name, price, category, availability, description);
        newItem.setId(nextItemNo++);
        foodList.add(newItem);
        return true;
    }

    public boolean addItem(int id, String name, double price, FoodCategory category, boolean availability, String description) {
        
        if (findFoodByName(name) != null) {
            return false; // Item already exists, return false to indicate failure
        }

        else if (findItemByNo(id) != null){
            return false;
        }


        MenuItem newItem = new MenuItem(name, price, category, availability, description);
        newItem.setId(id);
        if (id > nextItemNo){
            nextItemNo = id + 1;
        }
        this.foodList.add(newItem);
        return true;
    }


    // Remove food by id number
    public boolean removeFoodById(int itemId) {
        Optional<MenuItem> item = foodList.stream()
                                            .filter(i -> i.getId() == itemId)
                                            .findFirst();
        if (item.isPresent()) {
            foodList.remove(item.get());
            adjustItemNumbersAfter(itemId);
            return true;
        }
        return false;
    }

    // Helper function to ensure that id numbers of MenuItem are sequential
    private void adjustItemNumbersAfter(int itemNo) {
        foodList.stream()
                .filter(i -> i.getId() > itemNo)
                .forEach(i -> i.setId(i.getId() - 1));
    }

    public MenuItem findItemByNo(int itemNo) {
        return foodList.stream()
                       .filter(i -> i.getId() == itemNo)
                       .findFirst()
                       .orElse(null);
    }

    // Remove a menu item by name
    public boolean removeFoodByName(String foodName) {
        for (MenuItem item : foodList){
            if (item.getName().equalsIgnoreCase(foodName)){
                foodList.remove(item);
                return true;
            }

        }
        return false;
    }

    // Update the price of a menu item
    public boolean updateFoodPrice(String foodName, double newPrice) {
        MenuItem food = findFoodByName(foodName);
        if (food != null) {
            food.setPrice(newPrice);
            return true;
        }
        return false; // No such item found
    }

    // Helper method to find a food item by name
    public MenuItem findFoodByName(String name) {
        for (MenuItem item : foodList){
            if (item.getName().equalsIgnoreCase(name)){
                return item;
            }

        }
        return null;
    }

    // Print all menu items for verification and debugging
    public void printMenu() {
        foodList.forEach(food -> System.out.println("Name: " + food.getName() +
                                                    ", Price: " + food.getPrice() +
                                                    ", Category: " + food.getCategory() +
                                                    ", Availability: " + food.isAvailable() +
                                                    ", Description: " + food.getDescription()));
    }


    public List<MenuItem> getFoodList() {
        return foodList;
    }

    public static void saveMenu(Menu menu, String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(menu);
        }
    }
}
