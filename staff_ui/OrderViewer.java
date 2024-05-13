

package staff_ui;

import db.OrderListController; // we use methods from this class 
import orders.Order; 
import orders.OrderItem;
import orders.OrderState;
/**
Provides order viewing capabilities for branch staff.
This class allows staff to view specific orders, unfinished orders, and the next order in the queue for their branch.
*/
public class OrderViewer {
	private int branchid;
	private OrderListController orderListController; 
	
	public OrderViewer(int branchid, OrderListController orderListController) {
		this.branchid = branchid; 
		this.orderListController = orderListController;
	}
	
	// view a specific order 
	public void viewOrder(int orderid) {
		Order order = orderListController.getOrder(orderid);

		if (order != null | order.getBranchId() != this.branchid){ 

			printOrderDetails(order);
		}
	}
	
	// view new orders
	public void viewUnfinishedOrders() {
		Order[] orders = orderListController.getUnfinishedOrders(branchid);
		for (Order order : orders) {
			printOrderDetails(order);
		}
	}

	public void listOrders(){
		for (Order order : orderListController.getAllOrders()) {
			if (order.getBranchId() == this.branchid) {
				System.out.println(order.getOrderId());
			}
        }
	}
	
	// view next orders
	public void viewNextOrder() {
		Order order = orderListController.getNextOrder(branchid);
		printOrderDetails(order);
	}
	
	// helper function to print details 
	public void printOrderDetails(Order order) {
		System.out.println("Order ID: " + order.getOrderId());
        System.out.println("Is Takeaway: " + order.getTakeaway());
        System.out.println("Order Items:");
        for (OrderItem item : order.getOrderItems()) {
			System.out.println("\t" + item.toString()); 
		}

		
        System.out.println("Status: " + order.getStatus().toString());

		
		if (!order.getStatus().equals(OrderState.NEW_ORDER)){
			// System.out.print(order.getCancelTime());
			System.out.println("Cancellation Time: " + String.join("  ", order.getCancelTime().toString().split("T")));
			// System.out.println("Cancellation Time: " + order.getCancelTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG)));
		}
	}
}
