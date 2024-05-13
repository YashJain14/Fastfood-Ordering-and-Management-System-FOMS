

package users;

import java.util.List; 
import java.util.ArrayList; 
/**
Represents a customer of the fast-food restaurant.
Each customer is associated with a specific branch and maintains a list of their order IDs.
*/
public class Customer extends User {
	private int branchId;
    private List<Integer> orders;
    
    public Customer(int branchId) {
        this.branchId = branchId;
        this.orders = new ArrayList<>();
    }
    
    public int getBranch() {
    	return this.branchId; 
    }
    
    public List<Integer> getOrders(){
    	return this.orders; 
    }
    
    public void addOrder(int orderId) {
    	orders.add(orderId); // add new orderid into the orders list 
    }
}
