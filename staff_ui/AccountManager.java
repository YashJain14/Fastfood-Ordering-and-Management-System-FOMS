package staff_ui;

import db.UserDBController;
import db.BranchDBController;

import java.util.Scanner; 
import users.Staff; 
/**
Manages staff accounts, including adding and removing staff members.
This class interacts with the UserDBController and BranchDBController to create new staff accounts, remove existing accounts, and handle associated operations such as branch closure and staff promotion when necessary.
*/
public class AccountManager {
	private Scanner scanner; 
	private UserDBController userDBController; 
	private BranchDBController branchDBController; 
	
	public AccountManager(Scanner scanner, UserDBController userDBController, BranchDBController branchDBController) {
		this.scanner = scanner; 
		this.userDBController = userDBController;
		this.branchDBController = branchDBController; 
	}
	
	// add account	
	public void addAccount(int branchId) {		
		// asking user for details 
        System.out.println("Enter Staff ID:");
        String staffId = scanner.nextLine();

        System.out.println("Enter Age:");
        int age = scanner.nextInt();
        scanner.nextLine(); // consume newline

        System.out.println("Enter Name:");
        String name = scanner.nextLine();

        System.out.println("Enter Gender (M/F):");
        char gender = scanner.nextLine().charAt(0);

        System.out.println("Enter Role:");
        char role = scanner.nextLine().charAt(0);
        
        // create user using constructor 
     	Staff user = new Staff(staffId, name, role, gender, age, branchId);
		
        int result = userDBController.addStaff(user);
        
        switch(result) {
        // 0: successfully added the user 
		case 0:
			System.out.println("Staff added successfully\n");
			break;
		// 1: branch has too many managers
		case 1:
			System.out.println("Branch has too many managers\n");
			break;
		// 2: branch has too many staff
		case 2:
			System.out.println("Branch has too many staff\\n");
			break;
		// 3: branch not found
		case 3:
			System.out.println("Branch not found\\n");
			break;

		case 4:
			System.out.println("staffId was already taken\\n");
			break;
        }
	}
	
	// remove account
	public void removeAccount(String userId, int branchId) {	
		String answer;
        int result = userDBController.removeStaff(userId, branchId);

        switch(result) {
        // 0: successfully removed the user 
		case 0:
			System.out.println("Staff removed successfully\n");
			break;
			
		// 1: no. of staff and manager is 0: ask if wanna close branch? no -> error yes-> closebranch()
		case 1: 
			System.out.println("No Staff/Managers. Close branch? yes/no\n");
			answer = scanner.nextLine();
        	if (answer.equalsIgnoreCase("yes")) {
        		System.out.println("Closing the branch...\n");
        	    
        	    // [check needed inputs] code that handles branch closure

				Staff staff = userDBController.getStaffByLoginId(userId);

				branchDBController.closeBranch(staff.getBranchId());

        	    
        	} else { 
        	    System.out.println("Error: Account not deleted\n");
        	}
        	break;
        	
        // 2: theres no managers but theres staff -> prompt to promote staff.
   		case 2: 
 			System.out.println("No Managers. Promote staff or transer manager to delete account\n");
			System.out.println("Error: Account not deleted.");
        	break;
        	
        // 3: staff doesnt exist in branch 
   		case 3:
   			System.out.println("Error: Staff does not exist in branch\n");
   			break;
		}
    } 	
}
