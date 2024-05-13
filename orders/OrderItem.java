
package orders;

// import branch_management.FoodCategory;
import branch_management.FoodItem;
import branch_management.MenuItem;

import java.util.*;
/**
Represents a single item within a customer order.
Each order item is associated with a specific food item from the menu and includes details such as customizations, quantity, and the total price for that item.
*/
public class OrderItem extends FoodItem{
	private List <String> customisation;
	private int quantity;
	private double totalPrice;
	
	
	
	public OrderItem(MenuItem item, List <String> customisation, int quantity) {
		super(item);
		
		this.totalPrice = item.getPrice() * quantity;
		this.customisation = customisation;
		this.quantity = quantity;
	}
	
	
	public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.totalPrice = super.getPrice() * quantity;
    }
    
    @Override
    public double getPrice() {
        return totalPrice;
    }
    
    public List<String> getCustomisation() {
		return customisation;
	}

	public void addCustomisation(String customisation) {
		this.customisation.add(customisation);
	}
	
	

	@Override
    // useful for debugging 
    public String toString() {


        String customisationString = String.join(" ; ", customisation);


        return "OrderItem{" +
        "food=" + super.getName() +
        ", quantity=" + quantity +
        String.format(", totalPrice=%.2f", totalPrice) + ", customisations=" + customisationString + '}';
    }
}	