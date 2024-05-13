
package branch_management;


// import branch_management.FoodCategory;
/**
Represents a generic food item on the menu.
FoodItem is an abstract class that provides basic attributes and methods for food items, including name, price, category, description, and ID.
Concrete food item classes should extend this class and provide specific details for each type of food item.
*/
public abstract class FoodItem  {
    protected String name;
    protected double price;
    protected FoodCategory category;
    protected int itemId;
    protected String description;  // New attribute

    public FoodItem(String name, double price, FoodCategory category, String description) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.description = description;  // Initialize description
    }

    public FoodItem(FoodItem other) {
        this.name = other.name;
        this.price = other.price;
        this.category = other.category;
        this.itemId = other.itemId;
        this.description = other.description;  // Copy description
    }    
    
    public int getId() {
    	return itemId;
    }
    
    public void setId(int itemId) {
    	this.itemId = itemId;
    }
    
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public FoodCategory getCategory() {
        return category;
    }
    

    public void setCategory(FoodCategory category) {
        this.category = category;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public String toString() {
        return name + " - $" + price + " (" + category + ") - ";
    }
}





