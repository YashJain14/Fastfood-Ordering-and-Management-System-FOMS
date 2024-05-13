
package customer_ui;

import branch_management.*;
import db.BranchDBController;
/**
Manages the display of the menu to customers.
This class retrieves menu information from the BranchDBController and displays available menu items with their details to the customer.
*/
public class MenuHandler {
	
	private Menu menu;
	
	
	
	public MenuHandler(int branchId, BranchDBController branchDBController) {
		
		Branch branch = branchDBController.getBranchById(branchId);
		
		this.menu = branch.getMenu();
		
		
	}
	

    public void viewMenu() {
		
		if (this.menu != null && this.menu.getFoodList() != null) {
		for (MenuItem food : this.menu.getFoodList()) {
			if (food.isAvailable()) {
				System.out.println("ID: " + food.getId() +
                    "\t Name: " + food.getName() +
                    "\t Price: " + food.getPrice() +
                    "\t Category: " + food.getCategory() +
                    "\t Description: " + food.getDescription()
					+ "\n");
			}
		}
		} else {
			System.out.println("Menu is empty or not available.");
		}
	}
	
    
	
}
