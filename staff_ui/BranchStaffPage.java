

package staff_ui;

import java.util.InputMismatchException;
import java.util.Scanner;


import db.OrderListController;
import interfaces.UserInterface;
import users.Staff;
/**
Implements the main interface for branch staff users.
This class provides a menu-driven interface for branch staff to view and manage orders, check order status, and update order status as needed.
*/
public class BranchStaffPage implements UserInterface{
    protected OrderViewer orderViewer;
    protected OrderStatusHandler orderStatusHandler;
    protected Scanner scanner;
    protected Staff branchStaff;

    public BranchStaffPage(Scanner scanner, Staff BranchStaff, OrderListController orderListController) {
        this.scanner = scanner;
        this.branchStaff = BranchStaff;
        this.orderViewer = new OrderViewer(BranchStaff.getBranchId(), orderListController);
        this.orderStatusHandler = new OrderStatusHandler(orderListController, branchStaff.getBranchId());
    }
	
	@Override
	public boolean genUI() {
		System.out.println("\n\n\n\n\n\nBranch Staff Actions\n");
		System.out.printf("Welcome %s, what would you like to do? \n", branchStaff.getName());
	 
		int orderId; 
		  
		while (true) {
			System.out.println("\n");
			System.out.println("Select an action:\n");
			System.out.println("1. View New Orders\n");
			System.out.println("2: List All OrderIds\n");
			System.out.println("3. View a Particular Order\n");
			System.out.println("4. Check Order Status\n");
			System.out.println("5. Change Order Status\n");
		    System.out.println("6. Exit\n");
		   
		    String choice = scanner.nextLine();
		   
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
		     
		    // exit 
		    case "6":
				System.out.println("Are you sure you want to Log Out?\n");
				System.out.println("1. Yes I am sure 2. No");
					
				
				String option = this.scanner.nextLine();
				
				if (option.equals("1")) {
					return true;
				} 
		    	
		    default:
                System.out.println("Invalid choice, please select again.\n");
                break;
		     
		    }
		  }
	}
	
	public int getOrderID() {
		int orderId = -1; 
        System.out.println("\nInput your orderId: ");

		try {
			orderId = Integer.parseInt(scanner.nextLine());

			
		} catch (InputMismatchException e) {
			System.out.println("Invalid input. Please enter a valid integer.");
			return -1;		
		}
        
        return orderId;
    }
	
	// // get state 
	// public OrderState selectState() {
	// 	OrderState selectedState = null;
	//     boolean validInput = false;
	    
	//     while (!validInput) {
	//         System.out.println("Select the new order status:\n");
	//         System.out.println("1. New Order \n");
	//         System.out.println("2. Ready to Pick up \n");
	//         System.out.println("3. Completed \n");
	//         int statusChoice = scanner.nextInt();
	        
	//         switch (statusChoice) {
	//             case 1:
	//                 selectedState = OrderState.NEW_ORDER;
	//                 validInput = true;
	//                 break;
	//             case 2:
	//                 selectedState = OrderState.READY_TO_PICKUP;
	//                 validInput = true;
	//                 break;
	//             case 3:
	//                 selectedState = OrderState.COMPLETED;
	//                 validInput = true;
	//                 break;
	//             default:
	//                 System.out.println("Invalid choice. Please enter a valid option (1-3).");
	//                 break;
	//         }
	//     } 
	//    return selectedState;
	// }
	   
}
	
	
