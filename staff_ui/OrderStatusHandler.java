

package staff_ui;

// import java.util.Scanner;

import db.OrderListController; 
import orders.OrderState; 
import orders.Order;
/**
Handles order status inquiries and updates for branch staff.
This class allows branch staff to view the status of orders and to mark orders as ready for pickup. It ensures that only orders belonging to the staff member's branch are accessible.
*/
public class OrderStatusHandler {
	private OrderListController orderListController; 
	private int branchId;
	
	public OrderStatusHandler( OrderListController orderListController, int branchId) {
		this.orderListController = orderListController;
		this.branchId = branchId;
	}

	public void printOrderStatus(int orderId) {
		Order order = orderListController.getOrder(orderId);
		if (order == null){
			System.out.println("Order not found");
			return;
		}
		else if (order.getBranchId() != this.branchId){
			System.out.println("Order not found");
			return;
		}

		OrderState orderStatus = order.getStatus();
		System.out.println("Order " + orderId + " has order status: " + orderStatus);
	}
	
	public void setOrderStatus(int orderId) {
		Order order = orderListController.getOrder(orderId);

		if (order == null){
			System.out.println("Order not found");
			return;
		}

		else if (order.getBranchId() != this.branchId){
			System.out.println("Order not found");
			return;
		}
		
		// to check if currentstate and newstate are the same 
		if (order.getStatus().equals(OrderState.NEW_ORDER)) {
            order.markReady();
			System.out.println("Order " + orderId + " is ready to be picked up.");
			return;
		
		}
		
        System.out.println("Error: Unable to pick up order " + orderId + ". Please verify your orderId is correct.");
        
				
	}
}
