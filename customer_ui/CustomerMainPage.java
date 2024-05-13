
package customer_ui;

import java.util.InputMismatchException;
import java.util.Scanner;

import interfaces.UserInterface;
import db.*;

import users.Customer;
/**
Implements the main interface for customer interactions.
This class provides a menu-driven interface for customers to view the menu, add items to their cart, manage their cart, make payments, view receipts, check order status, and pick up orders.
*/

public class CustomerMainPage implements UserInterface{
	
	Scanner scanner;
	PaymentHandler paymentHandler;
	ReceiptPrinter receiptPrinter;
	MenuHandler menuHandler;
	OrderStatusHandler orderStatusHandler;
	CartHandler cartHandler;
	OrderPlacer orderPlacer;
	TakeoutHandler takeoutHandler;
	Customer user;
	
	public CustomerMainPage(Scanner scanner,PaymentMethodDBController paymentMethodDBController, Customer user, BranchDBController branchDBController, OrderListController orderListController) {
		this.user = user;
		this.scanner = scanner;
		
		this.menuHandler = new MenuHandler(user.getBranch(), branchDBController);
		
		this.cartHandler = new CartHandler(scanner, user.getBranch(), branchDBController);
		this.paymentHandler = new PaymentHandler(scanner, this.cartHandler.getCart(), paymentMethodDBController);
		this.receiptPrinter = new ReceiptPrinter(scanner, orderListController);
		this.orderStatusHandler = new OrderStatusHandler(orderListController);
		this.orderPlacer = new OrderPlacer(this.cartHandler.getCart(), orderListController);
		this.takeoutHandler = new TakeoutHandler(scanner);
		
		
	}
	
	public boolean genUI() {
		
		System.out.println("\n\n\n\n\n\nWelcome, what would you like to do?");
		
		
		while (true) {
			System.out.println("\n");
			System.out.println("Select an action:\n");
			System.out.println("1. View Menu\n");
			System.out.println("2. Add Item to Cart\n");
			System.out.println("3. View Cart\n");
			System.out.println("4. Remove Item from Cart\n");
			System.out.println("5. Clear Cart\n");
			System.out.println("6. Make Payment\n");
			System.out.println("7. View Receipt\n");
			System.out.println("8. View Order Status\n");
			System.out.println("9. Receive Order\n");
			System.out.println("10. Exit\n");
			
			

			String choice  = scanner.nextLine();
			
			
			switch (choice) {
				case "1":
					
					this.menuHandler.viewMenu();
					
					break;
			
				case "2":
					boolean added = this.cartHandler.addItem();
			
					if (added) {
						System.out.println("\nItem successfully added to Cart");
					}
					break;
			
				case "3":
					
					this.cartHandler.printCart();
					
					break;
			
				case "4":
					boolean removed = this.cartHandler.removeItem();
			
					if (removed) {
						System.out.println("\nItem successfully removed from Cart");
					}
			
					break;
			
				case "5":
					boolean cleared = this.cartHandler.clearCart();
			
					if (cleared) {
						System.out.println("\nCart was successfully cleared");
					}
					break;
			
				case "6":
					int option;
					boolean isTakeAway;
			
					boolean paymentSuccessful = this.paymentHandler.processPayment();
			
					if (paymentSuccessful) {
			
						option = this.takeoutHandler.checkTakeAway();
			
						if (option == 0) break;
			
						else isTakeAway = option == 1;
			
						int orderId = this.orderPlacer.placeOrder(isTakeAway, this.user.getBranch());
			
						user.addOrder(orderId);
			
			
						System.out.printf("\nYour Order Number is: %d", orderId);
					} else {
						System.out.println("\nPayment not successful returning to Main Menu.");
					}
					break;
			
				case "7":
					if (user.getOrders().isEmpty()) {
						System.out.println("\nNo Orders Yet. Exiting");
						break;
					}
			
					this.receiptPrinter.printReceipt(user.getOrders());
					break;
			
				case "8":
			
					System.out.println("\nWhich order do you want view status of?");
			
					option = scanner.nextInt();
			
					this.orderStatusHandler.printOrderStatus(option);
					break;
			
				case "9":
					System.out.println("\nWhat is your order number?");
			
					option = scanner.nextInt();

			
					this.orderStatusHandler.setOrderStatus(option);
					break;
			
				case "10":
					System.out.println("\n\nAre you sure you want to exit? Your Cart will be lost.\n");
			
					System.out.println("1. Yes I am sure 2. No");
			
					String exit = scanner.nextLine();
			
					if (exit.equals("1")) {
						return true;
					}
					break;
			
				default:
					System.out.println("\nInvalid option.\n");
					break;
			}
			
			
		}
		
	}
	

}
