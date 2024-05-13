

package login_system;

import java.util.*;
import branch_management.Branch;
import db.BranchDBController;
/**
Provides an interface for users to select a branch.
This class interacts with the BranchDBController to retrieve available branches and presents them to the user for selection. It also handles invalid input and allows users to exit the selection process.
*/
public class BranchSelector {
    private Scanner scanner;
    private BranchDBController branchDBController;

    public BranchSelector(Scanner scanner, BranchDBController branchDBController) {
        this.scanner = scanner;
        this.branchDBController = branchDBController;
    }

    public int selectBranch(boolean isCustomer) {
        HashMap<Integer, String> branches = branchDBController.getOpenBranches();
        
        List <Integer> branchIds = new ArrayList<> (branches.keySet());
        Collections.sort(branchIds);

        if (branches.isEmpty()) {
            System.out.println("\n\nNo branches available.");
            return -1;
        }

        System.out.println("\n\nAvailable branches:");

        for (int i : branchIds) {
            System.out.println((i) + ". " + branches.get(i));
        }

        Branch selectedBranch = null;
        int branchId = -1;
        while (selectedBranch == null) {
            System.out.print("\nPlease input a branch number: ");
            try {
                branchId = Integer.parseInt(scanner.nextLine()); // assuming 1-based user input
                
                if (branchId == -1){
                    return branchId;
                }

                if (branches.containsKey(branchId)){
                    selectedBranch = branchDBController.getBranchById(branchId);
                }
                else {
                    System.out.println("\nInvalid selection. Please try again. Or enter -1 to exit");
                }

            } catch (NumberFormatException e) {
                System.out.println("\nPlease enter a valid number. Or enter '-1' to exit.");
            }
        }

        return branchId;
    }




    public int selectBranch() {
        HashMap<Integer, String> branches = branchDBController.getAllBranches();
        
        List <Integer> branchIds = new ArrayList<> (branches.keySet());
        Collections.sort(branchIds);

        if (branches.isEmpty()) {
            System.out.println("\nNo branches available.");
            return -1;
        }

        System.out.println("\n\nAvailable branches:");

        for (int i : branchIds) {
            System.out.println((i) + ". " + branches.get(i));
        }

        Branch selectedBranch = null;
        int branchId = -1;
        while (selectedBranch == null) {
            System.out.print("\n\nPlease input a branch number: ");
            try {
                branchId = Integer.parseInt(scanner.nextLine()); // assuming 1-based user input
                
                if (branchId == -1){
                    return branchId;
                }

                if (branches.containsKey(branchId)){
                    selectedBranch = branchDBController.getBranchById(branchId);
                }
                else {
                    System.out.println("\n\nInvalid selection. Please try again. Or enter -1 to exit");
                }

            } catch (NumberFormatException e) {
                System.out.println("\nPlease enter a valid number. Or enter '-1' to exit.");
            }
        }

        return branchId;
    }
    
}
