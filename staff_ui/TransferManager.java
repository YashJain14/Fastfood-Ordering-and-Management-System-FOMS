

package staff_ui;
import db.UserDBController; 

/**
Handles the transfer and promotion of staff members between branches.
This class interacts with the UserDBController to update staff member information and ensure that transfer and promotion operations adhere to branch staffing rules and constraints.
*/
public class TransferManager {
	private UserDBController userDBController;
	
	public TransferManager( UserDBController userDBController) {
		this.userDBController = userDBController;
	}
	
	// transfer staff
	public void transferStaff(int dstBranch, int srcBranch, String staffId) {
		int result = userDBController.transferStaff(dstBranch, srcBranch, staffId);

		switch (result){
			case 0 : 
			System.out.println("Transferred successfully.");
			break;

			case 1: 
				System.out.printf("Source Branch (id: %d) has no Staff and Managers left. ", srcBranch);
				System.out.println("\nTransfer Staff to Source Branch or close Source Branch to transfer user\n");
				System.out.println("Error: User not transfered\n\n");
				break;

			case 2:
				System.out.printf("Source Branch (id: %d) has no Managaers", srcBranch);
				System.out.println("\n Transder Staff to Source Branch or Promote Staff.");
				System.out.println("Error: User not transfered\n\n");
				break;

			case 3:
				System.out.printf("Destination Branch (id: %d) has too many Managaers", dstBranch);
				System.out.println("\n Transfer Staff out of Destination Branch or remove users");
				System.out.println("Error: User not transfered\n\n");
				break;

			case 4: 
				System.out.printf("Destination Branch (id: %d) has exceeded the staff threshold", dstBranch);
				System.out.println("\n Transfer Staff out of Destination Branch or remove users");
				System.out.println("Error: User not transfered\n\n");
				break;

			case 5:
				System.out.println("Error: User not found\n\n");
				break;

			default:
				System.out.println("Error: Invalid option\n\n");
				break;
			
		}

		
		
	}
	
	// assign manager 
	public void assignManager(int branchId, String staffId) {		
		int result = userDBController.assignManager(branchId, staffId);
		switch(result) {
		// 0: success
		case 0:
			System.out.println("Assigned successfully\n");
			break;
			
		// 1: too many managers, error 
		case 1: 
			System.out.println("Error\n");
        	System.out.println("Too many managers\n");
        	break;
        	
        // 2: manager or staff not found, error
   		case 2: 
 			System.out.println("Error\n");
    	    System.out.println("Staff/Branch not found\n");
    	    break;

		case 3:
			System.out.println("Error\n");
			System.out.println(staffId + " is already a manager");
		}
	}
}
