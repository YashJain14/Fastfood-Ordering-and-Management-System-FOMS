

package staff_ui;
import users.Staff;
import db.UserDBController;
import db.BranchDBController;
import interfaces.UserInterface;
import db.PaymentMethodDBController;

import java.util.Scanner; 
/**
Implements the main interface for admin users.
This class provides a menu-driven interface for admin users to perform various tasks related to staff management, branch management, payment methods, and more.
*/
public class AdminPage implements UserInterface{
	private Scanner scanner; 
	private FilterCriteria filterCriteria;
	private AccountManager accountManager; 
	private BranchStatusHandler branchStatusHandler; 
	private TransferManager transferManager; 
	private Staff user; 
	private PaymentManager paymentManager;
	private login_system.BranchSelector branchSelector;
	private ListBranches listBranches;
	
	public AdminPage(Scanner scanner, Staff user, UserDBController userDBController, BranchDBController branchDBController, PaymentMethodDBController paymentMethodDBController) {
        this.scanner = scanner;
        this.user = user;
        this.filterCriteria = new FilterCriteria(scanner, userDBController);
        this.accountManager = new AccountManager(scanner, userDBController, branchDBController);
        this.transferManager = new TransferManager(userDBController);
		this.branchStatusHandler = new BranchStatusHandler(scanner, branchDBController);
		this.paymentManager = new PaymentManager(scanner, paymentMethodDBController);
		this.branchSelector = new login_system.BranchSelector(scanner, branchDBController);
		this.listBranches = new ListBranches(branchDBController);
		

    }
	
	@Override
	public boolean genUI() {
		System.out.println("\n\n\n\n\n\nAdmin Actions\n");
		System.out.printf("Welcome %s, what would you like to do?\n", user.getName());
	 
		String choice;
		  
		while (true) {
			System.out.println("\n");
			System.out.println("Select an action:\n");
			System.out.println("1. Display Filtered Staff List\n");
			System.out.println("2. Edit Staff Accounts\n");
			System.out.println("3. Assign Branch Managers\n");
			System.out.println("4. Transfer Staff/Manager among branches\n");
			System.out.println("5. Add/remove payment method\n");
			System.out.println("6. Open/close branch\n");
			System.out.println("7. List Branches\n");
		    System.out.println("8. Logout\n");
		   
		    choice = scanner.nextLine();
		    int branchId;
		    String staffId;
		   
		    switch (choice){

		    // display staff list 
		    case "1":
				filterCriteria.promptFilterOptions();
		    	break;
		     
		    // edit staff accounts 
		    case "2":
		    	System.out.println("Select an Edit action:\n");
		        System.out.println("1. Add Account \n");
		        System.out.println("2. Remove Account \n");


		        String editChoice = scanner.nextLine();
		        
		        switch (editChoice) {
		            case "1":
		            	System.out.println("Enter Branch ID:\n");
		            	String addbranchId = scanner.nextLine();

						try {  
							branchId = Integer.parseInt(addbranchId);  
						  } catch(NumberFormatException e){  
							System.out.println("Invalid input. Exiting.\n");
							break;
						  }  

		                accountManager.addAccount(branchId);
		                break;
		            case "2":
						System.out.println("Enter Branch ID:\n");
						String rembranchId = scanner.nextLine();
		            	
						try {  
							branchId = Integer.parseInt(rembranchId);  
						  } catch(NumberFormatException e){  
							System.out.println("Invalid input. Exiting.\n");
							break;
						  }  

						System.out.println("Enter Staff ID:\n");
		            	staffId = scanner.nextLine();
		                accountManager.removeAccount(staffId, branchId);
		                break;
		            default:
		                System.out.println("Invalid choice. Exiting.\n\n");
		                break;
				}
				break;
		     
		    // assign branch managers
		    case "3":
				branchId = this.branchSelector.selectBranch();
		    	System.out.println("Enter Staff ID:\n");
            	staffId = scanner.nextLine();
		        transferManager.assignManager(branchId, staffId);
		        break; 
		     
		        
		    // transfer Staff/Manager among branches
		    case "4": 
				System.out.println("\nEnter Destination Branch ID:");
				int dstBranchId = Integer.parseInt(scanner.nextLine());
				System.out.println("\nEnter Source Branch ID:");
				int srcBranchId = Integer.parseInt(scanner.nextLine());
				System.out.println("\nEnter Staff ID:");
				staffId = scanner.nextLine();
				transferManager.transferStaff(dstBranchId, srcBranchId, staffId);
				break;
		    	
		    // add/remove payment method
		    case "5": 
				this.paymentManager.managePayments();
				break;
		    	
		    // open/close branch 
		    case "6": 
				branchId = this.branchSelector.selectBranch();

				this.branchStatusHandler.changeBranchStatus(branchId);

				break;

			case "7":
				this.listBranches.listBranches();
				break;
		     
		    // exit 
		    case "8":
				System.out.println("Are you sure you want to Log Out?");
				System.out.println("1. Yes I am sure 2. No");
					
				String option = scanner.nextLine();
				
				if (option.equals("1")) {
					return true;
				}
				break;
		    	
		    default:
                System.out.println("Invalid choice, please select again.");
                break;
		    }
		  }
	}
	}

