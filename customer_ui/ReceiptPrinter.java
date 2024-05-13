


package customer_ui;
// import branch_management.MenuItem;
import db.OrderListController;
import orders.Order;
import orders.OrderItem;
import java.util.List;

import java.util.Scanner;
/**
Generates and prints receipts for customer orders.
This class retrieves order information from the OrderListController and formats it into a receipt format, displaying order details, items, customizations, and the total price.
*/
public class ReceiptPrinter {
	
	private Scanner scanner;
	private OrderListController orderListController;
	
	public ReceiptPrinter(Scanner scanner, OrderListController orderListController) {
		this.scanner = scanner;
		this.orderListController = orderListController;


	}

	public void printReceipt(List <Integer> orderIds) {
		
		System.out.println("Which order do you want a receipt for?");
		
		int option;
		System.out.println("OrderIds: ");

		for (int id: orderIds) {
			System.out.printf("%d\n", id);
		}
		
		option = scanner.nextInt();
		
		if (! orderIds.contains(option)) {
			System.out.println("Invalid Id input. Exiting.");
			return;
		}
		
		Order order = this.orderListController.getOrder(option);
		
		System.out.printf("OrderId: %d", order.getOrderId());
		
		System.out.println("\nID \t Item \t Quantity \t Price \t Customizations \n");
		
		int index = 1;
		
		for (OrderItem item : order.getOrderItems()) {
			System.out.printf("%d \t %s \t %d \t %f \t", index++, item.getName(), item.getQuantity(), item.getPrice());
			
			item.getCustomisation().forEach(s -> System.out.printf("%s, ", s));
			System.out.println("");
        }
		
		System.out.println("Total Price: ");
		System.out.printf("%f \n", order.getPrice());
		
	}
}
