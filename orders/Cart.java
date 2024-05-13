

package orders;


import java.util.ArrayList;
import java.util.List;
/**
Represents a customer's shopping cart.
This class stores a list of order items and provides methods to add items to the cart, remove items, calculate the total cost, and clear the cart.
*/
public class Cart {
    private List<OrderItem> items;
    
    // private String sessionId;

    public Cart() {
        items = new ArrayList<>();
        // this.sessionId = sessionId;
    }

    public void addItem(OrderItem item) {
        items.add(item);
    }

    public boolean removeItem(int itemId) {
    	
    	if(itemId > items.size() || itemId <= 0) {
    		return false;
		}
    	
        items.remove(itemId - 1);
        return true;
    }
    
    
    public List<OrderItem> getItems() {
        return items;
    }

    public double getTotalCost() {
    	
        double total = 0.0;
        for (OrderItem item : items) {
            total += item.getPrice();
        }
        return total;
    }

    public void clearCart() {
        items.clear();
    }
    
    // public String getSessionId() {
    // 	return this.sessionId;
    // }
    
}
