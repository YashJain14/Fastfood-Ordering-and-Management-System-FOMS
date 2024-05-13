
package customer_ui;

import java.util.*;
import orders.Cart;
import orders.OrderItem;
import branch_management.*;
import db.BranchDBController;
/**
Handles operations related to the customer's cart.
This class allows customers to add items to their cart, remove items, clear the cart, and view the contents of the cart with the total cost.
*/

public class CartHandler {

	private Scanner scanner;
	private Cart cart;
	private Menu menu;
	
	public CartHandler(Scanner scanner,  int branchId, BranchDBController branchDBController) {
		this.scanner = scanner;
		Branch branch = branchDBController.getBranchById(branchId);
		
		this.menu = branch.getMenu();
		this.cart = new Cart();
	}
	
	public Cart getCart() {
		return this.cart;
	}
	
	public void printCart() {
		System.out.printf("\n\nCart: ");
		
		System.out.println("\nID \t Item \t Quantity \t Price \t Customizations \n");
		
		int index = 1;
		
		for (OrderItem item : this.cart.getItems()) {
			// System.out.printf("%d \t %s \t %d \t %f \t", index++, item.getName(), item.getQuantity(), item.getPrice());
			System.out.printf("%d \t %s   %d \t %.2f   ", index++, item.getName(), item.getQuantity(), item.getPrice());

			item.getCustomisation().forEach(s -> System.out.printf("%s, ", s));
			System.out.println("");
        }
		
		System.out.println("Total Price: ");
		System.out.printf("%f \n", this.cart.getTotalCost());
		
	}
	
	
	public boolean addItem() {
		
		String input;
		int int_input;
		MenuItem menuItem = null;

		scanner.nextLine();
		
		while (menuItem == null) {
			
			System.out.println("\nInput the name or ID of the item to add to cart: ");
	
			input = scanner.nextLine();
		
			try {
				int itemId = Integer.parseInt(input);
				
				if (itemId == -1) {
					return false;
				}
				
				menuItem = menu.findItemByNo(itemId);
			}catch (NumberFormatException e) {
				menuItem = menu.findFoodByName(input);
			}
			
			if (menuItem == null) {
				System.out.println("\nInvalid Item Id or Name. Try again or input -1 to exit");
			}

			else if(! menuItem.isAvailable()){
				System.out.println("\nInvalid Item Id or Name. Try again or input -1 to exit");
			}
			
		} 

		System.out.println("\nAdding Item: " + menuItem.getName() + "\n\n");

		List<String> customisations = new ArrayList<>(menuItem.getCustomisation().getcustomization());
		
		List<String> selected_cust = new ArrayList<>();

		while(customisations.size() > 0) {
			
			System.out.println("\nAdd Customizations");

			System.out.println("0: No More Customizations");
			for (int x = 0; x <  customisations.size(); x++) {
				System.out.printf("%d: %s \n", x+1, customisations.get(x));
				}

			System.out.println("-1: Cancel");
			
			int_input = scanner.nextInt();
				
			if (int_input == -1) {
				return false;
			}
			else if (int_input == 0) {
				break;
			}
			
			else if (int_input >= customisations.size() | int_input < 0) {
				System.out.println("Invalid option. Try Again or input -1 to exit");
				continue;
			}
			
			else {
				selected_cust.add(customisations.get(int_input - 1));
				customisations.remove(int_input - 1);
			}

			System.out.println("\n\nCurrent Customizations:");
			for (String cust : selected_cust) {
				System.out.printf( "%s \n", cust);
				}

		}
		
		while(true) {
			System.out.println("\nHow many items would you like?");
			
			int_input = scanner.nextInt();
			
			if (int_input == -1) {
				return false;
			}
			
			else if (int_input <= 0) {
				System.out.println("\nInvalid input. Try again or input -1 to exit");
				continue;
			}
			
			else {
				break;
			}
			
		}
		
		if (selected_cust.isEmpty()){
			selected_cust.add("None");
		}
		
		
		OrderItem orderItem = new OrderItem(menuItem, selected_cust, int_input);
		
		this.cart.addItem(orderItem);
		
		return true;
		
	}
	
	
	public boolean removeItem() {
		int input;
		
		System.out.println("\nInput the ID of the item to remove:");
			
		input = scanner.nextInt();
			
		return this.cart.removeItem(input);	
	}
	
	public boolean clearCart() {
		int input;
		
		System.out.println("\nAre you sure you want to clear ALL items from cart?");
		
		System.out.println("1: Yes, 2: No");
		
		input = scanner.nextInt();
		
		if (input == 1) {
			this.cart.clearCart();
			return true;
		}
		
		return false;
	}
	
	
	
}
