

package staff_ui;

import java.util.Scanner; 
import db.BranchDBController; // using getbranch method 
import branch_management.Branch; 
/**
Manages the opening and closing of branches.
This class interacts with the BranchDBController to update the status of a branch and allows admin users to open or close branches as needed.
*/
public class BranchStatusHandler {
	private Scanner scanner; 
	private BranchDBController branchDBController;
	
	public BranchStatusHandler(Scanner scanner, BranchDBController branchDBController) {
		this.scanner = scanner; 
		this.branchDBController = branchDBController; 
	}
	
	public void changeBranchStatus(int branchId) {
		Branch branch = branchDBController.getBranchById(branchId); // get branch instance 
		
		// get current branch status	
		System.out.println("Current branch status is " + (branch.getisOpen() ? "Open" : "Closed") + ".");
        System.out.print("Change it? (Yes/No): ");
        String input = scanner.nextLine();

        if (input.equalsIgnoreCase("yes")) {
            // toggle the branch status
			

            branch.setOpen(!branch.getisOpen());
            System.out.println("Branch status is changed to: " + (branch.getisOpen() ? "Open" : "Closed"));
        } else { // if user type no 
            System.out.println("Branch status remains unchanged.");
        }
	}
}
