
package customer_ui;

import db.OrderListController;
import orders.Cart;
/**
Facilitates the placement of orders for customers.
This class interacts with the OrderListController to create a new order based on the items in the customer's cart and the chosen takeaway/dine-in option.
*/
public class OrderPlacer {

    private Cart cart;
    private OrderListController orderListController;
    
    public OrderPlacer(Cart cart, OrderListController orderListController){
        this.cart = cart;
        this.orderListController = orderListController;
    }
    
    public int placeOrder(boolean isTakeAway, int branchId) {
        
        // Check if the cart has items before placing an order

        System.out.println("Placing order");
        return this.orderListController.createOrder(cart, isTakeAway, branchId);
        
    }
}
