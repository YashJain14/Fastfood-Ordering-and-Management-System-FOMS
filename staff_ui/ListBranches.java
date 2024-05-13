

package staff_ui;
import java.util.HashMap;
import db.BranchDBController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import branch_management.Branch;
import branch_management.BranchHR;
/**
Provides a list of all branches with their status and staff information.
*/
public class ListBranches {
    private BranchDBController branchDBController;

    public ListBranches(BranchDBController branchDBController){
        this.branchDBController = branchDBController;
    }


    public void listBranches(){
        HashMap<Integer, String> branches = branchDBController.getAllBranches();
        
        List <Integer> branchIds = new ArrayList<> (branches.keySet());
        Collections.sort(branchIds);

        if (branches.isEmpty()) {
            System.out.println("No branches available.");
            return;
        }

        System.out.println("Available branches:");

        for (int i : branchIds) {
            Branch branch = branchDBController.getBranchById(i);
            BranchHR branchHR = branch.getBranchHR();
            System.out.println((i) + ". " + branches.get(i) + "     Current Status: "  + (branch.getisOpen() ? "Open  " : "Closed") + "      Staff Quota: " + branchHR.getStaffQuota() + "       No Staff: " + branchHR.getNoStaff() + "              No Managers:" + branchHR.getNoManagers());
        }
    }
    
}
