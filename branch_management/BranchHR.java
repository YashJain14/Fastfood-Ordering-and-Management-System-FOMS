

package branch_management;
import users.Staff;
/**
Manages the human resources for a branch.
This class keeps track of the number of staff and managers, as well as the staff quota for the branch. It provides methods to add staff and managers, and to check if the branch has reached its staff quota.
*/

public class BranchHR {
    private int noManagers;
    private int noStaff;
    private int staffQuota;

    public BranchHR(int staffQuota) {
        this.noManagers = 0;
        this.noStaff = 0;
        this.staffQuota = staffQuota;
    }

    public BranchHR(int staffQuota, int noStaff, int noManager) {
        this.noManagers = noManager;
        this.noStaff = noStaff;
        this.staffQuota = staffQuota;
    }

    public boolean checkManagerisZero() {
        if (noManagers == 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkStaffisZero() {
        if (noStaff == 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean addStaff(Staff newStaff) {
        if (newStaff == null) {
            return false; 
        }
         if (noStaff >= staffQuota) {
            return false; 
        }
        noStaff++;
        return true;
    }

    public boolean addManager(Staff newManager) {
        if (newManager == null) {
            return false; 
        }
        if (noStaff >= staffQuota || noManagers >= calculateExpectedManagers()) {
            return false; 
        }
        noManagers++;
        noStaff--;
        return true;
    }

    private int calculateExpectedManagers() {
        return (noStaff + 4) / 5;
    }

    public int getNoManagers() {
        return noManagers;
    }

    public int getNoStaff() {
        return noStaff;
    }

    public int getStaffQuota() {
        return staffQuota;
    }

    
}