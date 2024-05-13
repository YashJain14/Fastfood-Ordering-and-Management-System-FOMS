
package customer_ui;

// import java.util.Scanner;

import db.OrderListController; 
import orders.Order; 
import orders.OrderState;
/**
Handles inquiries and updates related to order status for customers.
This class allows customers to view the current status of their order and to mark orders as picked up (completed) when they are ready.
*/

public class OrderStatusHandler {
	private OrderListController orderListController;  
	
	public OrderStatusHandler( OrderListController orderListController) {
		this.orderListController = orderListController;
	}

	public void printOrderStatus(int orderId) {


		Order order = orderListController.getOrder(orderId);

		if ( order == null){
			System.out.println("Order not found");
			return;
		}
		
		OrderState orderStatus = order.getStatus();
		System.out.println("Order " + orderId + " has order status: " + orderStatus);
	}
	
	public void setOrderStatus(int orderId) {

 
		Order order = orderListController.getOrder(orderId);

		if ( order == null){
			System.out.println("Order not found");
			return;
		}
		
		// to check if currentstate and newstate are the same 
		if (order.getStatus().equals(OrderState.READY_TO_PICKUP)) {
            order.setStatus(OrderState.COMPLETED);
			System.out.println("Order " + orderId + " has been picked up");
			return;
		
		}
		
        System.out.println("Unable to pick up order " + orderId + " Order is not Ready To Pickup.");
        
				
	}
}