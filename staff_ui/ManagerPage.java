

package staff_ui;

import java.util.Scanner;
import db.BranchDBController;
import db.OrderListController;
import db.UserDBController;
import users.Staff;
/**
Implements the main interface for branch manager users.
This class extends the BranchStaffPage and adds additional functionalities for managers, such as viewing the staff list and editing the menu.
*/


public class ManagerPage extends BranchStaffPage {
	private StaffListViewer staffListViewer; 
	private MenuItemHandler menuItemHandler; 
	private Scanner scanner; 

    public ManagerPage(Scanner scanner, Staff branchStaff, OrderListController orderListController, UserDBController userDBController, BranchDBController branchDBController) {
	
		super(scanner, branchStaff, orderListController);
        this.scanner = scanner; 
		this.menuItemHandler = new MenuItemHandler(scanner, branchStaff.getBranchId(), branchDBController);
		this.staffListViewer = new StaffListViewer(userDBController);
        
    }

    // Override genUI method to include additional functionalities
    @Override
    public boolean genUI() {
    	System.out.println("\n\n\n\n\n\n\n\nManager Actions\n");
		System.out.printf("Welcome %s, what would you like to do? \n", branchStaff.getName());
	 

        
        while (true) {
			System.out.println("");
			System.out.println("Select an action:\n");
			System.out.println("1. View New Orders\n");
			System.out.println("2: List All OrderIds\n");
			System.out.println("3. View a Particular Order\n");
			System.out.println("4. Check Order Status\n");
			System.out.println("5. Change Order Status\n");
			System.out.println("6. View Staff List\n");
			System.out.println("7. Edit Menu\n");
		    System.out.println("8. Exit\n");
		   
		    String choice = scanner.nextLine();
			int orderId;
		   
		    switch (choice){
		    // View New Orders
		    case "1":
		    	orderViewer.viewUnfinishedOrders();
		    	break;
		     

			case "2":
				System.out.println("The orders you can view are: ");
				orderViewer.listOrders();
				break;

		    // View a Particular Order
		    case "3":
				orderId = getOrderID();
				if (orderId == -1) break;
		    	orderViewer.viewOrder(orderId);
		    	break;
		     
		    // Check Order Status
		    case "4":
				orderId = getOrderID();
				if (orderId == -1) break;
		        orderStatusHandler.printOrderStatus(orderId);
		        break; 
		     
		    // Update Order Status
		    case "5":
				System.out.println("Set Order as Ready to Collect");
				orderId = getOrderID();
				if (orderId == -1) break;
		        orderStatusHandler.setOrderStatus(orderId);		        
		        break;
		     		     
		    // view staff list  
		    case "6":
            	staffListViewer.viewStaffList(branchStaff.getBranchId());
                break;

            // Edit Menu
            case "7":
            	selectMenuEdit(); // check if manager chose to go back
                
                break;

            // Exit
            case "8":
				System.out.println("Are you sure you want to Log Out?");
				System.out.println("1. Yes I am sure 2. No");
					
				String option = scanner.nextLine();
				
				if (option.equals("1")) {
					return true;
				}
            default:
                System.out.println("Invalid choice, please select again.");
                break;
            }
        }  
    }
    
    private void selectMenuEdit() {
    	String editChoice; 
    	
    	while(true) {
			System.out.println("\n\n\n\nSelect Menu Edits:\n");
	        System.out.println("1. Add Item \n");
			System.out.println("2. Remove Item \n");
			System.out.println("3. Edit Price \n");
			System.out.println("4. Edit Availability \n");
			System.out.println("5. View Menu \n");
			System.out.println("6. Back to Previous Page \n");
			editChoice = scanner.nextLine(); 
			
			switch(editChoice) {
			case "1":
				menuItemHandler.addItem(); 
				break; 
				
			case "2":
				
		        menuItemHandler.removeItem();
				break;
				
			case "3":
				menuItemHandler.editPrice();
				break;
				
			case "4":
				menuItemHandler.editAvailability();
				break;

			case "5":
				menuItemHandler.viewMenu();
				break;

				
			case "6":
				return; // go back to previous page
				
			default:
	            System.out.println("Invalid choice, please select again.");
				return;	
			}
    	}
    }
}



