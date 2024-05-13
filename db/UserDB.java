

package db;

import java.util.List;
import users.Staff;
import java.util.ArrayList;
/**
Represents the database of staff members.
This class maintains a list of Staff objects, providing methods to access and manage the staff list.
*/
public class UserDB{
    private List<Staff> staffList = new ArrayList<>();
    
    public List<Staff> getStaffList(){
        return staffList;
    }

}
