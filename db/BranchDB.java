

package db;

import java.util.*;
import branch_management.Branch;
/**
Represents the database of branch information.
This class maintains a list of Branch objects and a map for efficient retrieval of branches by ID. It provides methods for adding branches and accessing branch data.
*/
public class BranchDB{
	
    private HashMap<Integer, String> branchIdToNameMap;
    private List<Branch> branchList;
    public BranchDB() {
        this.branchIdToNameMap = new HashMap<>();
        this.branchList = new ArrayList<>();
    }

    public void addBranch(Branch branch) {
        // Add branch object to list
        branchList.add(branch);
        // Map branch ID to branch name
        branchIdToNameMap.put(branch.getBranchId(), branch.getBranchName());
    }

    public List<Branch> getBranchList() {
        return branchList;
    }

    
    public HashMap<Integer, String> getIdToNameMap(){
    	return branchIdToNameMap;
    }

}

