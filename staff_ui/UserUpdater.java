

package staff_ui;
import db.UserDBController;
import java.util.Scanner;
import users.Staff;
/**
Allows for updating staff member information.
This class provides an interface for updating specific attributes of a staff member, such as name, gender, age, and Staff ID.
*/
public class UserUpdater {

    private UserDBController userDBController;
    private Scanner scanner;

    public UserUpdater(Scanner scanner, UserDBController userDBController){
        this.scanner = scanner;
        this.userDBController = userDBController;
    }


    public boolean updateUser(){
        System.out.print("Enter the Staff Login ID of the staff member to update: ");
        String loginId = scanner.nextLine();
        Staff staffToUpdate = this.userDBController.getStaffByLoginId(loginId);
        if (staffToUpdate == null) {
            System.out.println("Staff member not found.");
            return false;
        }

        System.out.println("Select which attribute to update:");
        System.out.println("1. Name");
        System.out.println("2. Gender");
        System.out.println("3. Age");
        System.out.println("4. StaffId");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.print("Enter new name: ");
                String newName = scanner.nextLine();
                staffToUpdate.setName(newName);
                break;
          
            case 2:
                System.out.print("Enter new gender: ");
                char newGender = scanner.nextLine().charAt(0);
                staffToUpdate.setGender(newGender);
                break;
            case 3:
                System.out.print("Enter new age: ");
                int newAge = scanner.nextInt();

                if (newAge < 0){
                    System.out.print("Invalid age");
                    return false;
                }
                staffToUpdate.setAge(newAge);
                scanner.nextLine();
                break;
            case 4:
                System.out.print("Enter new staffId: ");
                String newId = scanner.nextLine();
                staffToUpdate.setId(newId);
                break;
            default:
                System.out.println("Invalid choice.");
                return false;
     }

     return true;

}
}