
package staff_ui;
import db.UserDBController;
import users.Staff;
/**
Provides functionality to view the list of staff members.
This class retrieves staff information from the UserDBController and displays a list of staff members along with their details, filtered by the specified branch ID.
*/

public class StaffListViewer {
    private UserDBController userDBController;

    public StaffListViewer(UserDBController userDBController) {
        this.userDBController = userDBController;
    }

	public void viewStaffList(int branchId){


        for (Staff staff : userDBController.getAllStaff()) {
			if (staff.getBranchId() == branchId) {
				System.out.println("StaffId: " + staff.getStaffId() +
						"Name: " + staff.getName() +
                        ", Role: " + staff.getRole() +
                        ", Branch: " + staff.getBranchId() +
                        ", Gender: " + staff.getGender());
			}
        }
        

	}


}
