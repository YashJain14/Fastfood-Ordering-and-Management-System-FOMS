

package staff_ui;
import users.Staff;
import db.UserDBController;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
/**
Provides filtering options for staff lists.
This class allows users to filter the staff list based on criteria such as role, gender, branch, Staff ID, and age.
*/
public class FilterCriteria {
   
    private UserDBController userDBController;
    Scanner scanner;

    public FilterCriteria(Scanner scanner, UserDBController userDBController) {
        this.scanner = scanner;
        this.userDBController = userDBController; 
    }

    public void promptFilterOptions() {
        List<Staff> allStaff = userDBController.getAllStaff();

        Set<String> uniqueRoles = allStaff.stream()
                                          .map(staff -> String.valueOf(staff.getRole()))
                                          .collect(Collectors.toSet());
        Set<String> uniqueGenders = allStaff.stream()
                                            .map(staff -> String.valueOf(staff.getGender()))
                                            .collect(Collectors.toSet());
        Set<String> uniqueBranches = allStaff.stream()
                                             .map(staff -> String.valueOf(staff.getBranchId()))
                                             .collect(Collectors.toSet());

        uniqueBranches.remove("-1");

        System.out.println("Choose a filter criteria:");
        System.out.println("1. Role");
        System.out.println("2. Gender");
        System.out.println("3. Branch");
        System.out.println("4. StaffId");
        System.out.println("5. Age");        

        String choice = scanner.nextLine();

        Stream<Staff> staffStream = userDBController.getAllStaff().stream();

        switch (choice) {
            case "1":
                System.out.println("Available Roles: " + uniqueRoles);
                System.out.print("Enter role to filter by: ");

                String  role = scanner.nextLine().trim();


                if (role.equals("A") | role.equals("S") | role.equals("M")){
                    System.out.printf("Staff members with role %s: \n", role);
                    staffStream.filter(s -> String.valueOf(s.getRole()).equals(role))
                           .forEach(s -> printStaff(s));
                    return;
                }

                else {
                    System.out.println("Invalid input. Exiting.\n");
                    return;
                }

            case "2":
                System.out.println("Available Genders: " + uniqueGenders);
                System.out.print("Enter gender to filter by: ");
                
                String gender = scanner.nextLine().trim();

                if (gender.equals("M") | gender.equals("F")){
                    System.out.printf("%s Staff: \n", gender);
                    staffStream.filter(s -> String.valueOf(s.getGender()).equals(gender))
                        .forEach(s -> printStaff(s));

                    return;
                }
                else {
                    System.out.println("Invalid input. Exiting.\n");
                    return;
                }


            case "3":
                System.out.println("Available Branches: " + uniqueBranches);
                System.out.print("Enter branch to filter by: ");
                
                String branchId = scanner.nextLine().trim();

                if (uniqueBranches.contains(branchId)){  

                    System.out.printf("Staff in branch in branch number %s: \n", branchId);

                    staffStream.filter(s -> String.valueOf(s.getBranchId()).equals(branchId))
                                .forEach(s -> printStaff(s));
                }

                break;

            case "4":
                System.out.print("Which Staff do you want to view?: ");
                String staffId = scanner.nextLine();

                try {  
                    Staff staff = userDBController.getStaffByLoginId(staffId);  
                    printStaff(staff);
                } catch(NullPointerException e){  
                    System.out.println("No such staff found\n");
                    return;
                }  

                break;


            case "5":
                System.out.print("Enter age to filter by: ");
                String age = scanner.nextLine();

                try {  
                    Integer.parseInt(age);  
                  } catch(NumberFormatException e){  
                    System.out.println("Invalid input. Exiting.\n");
                    return;
                  }  

                System.out.printf("Staff members that are of age %s: \n", age);


                staffStream.filter(s -> String.valueOf(s.getAge()).equals(age))
                            .forEach(s -> printStaff(s));

                break;

            
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }

    private void printStaff(Staff s){

        if (s.getRole() == 'A') System.out.println("\nName: " + s.getName() +  ", StaffId: " + s.getStaffId() + ", Role: " + s.getRole() + ", Gender: " + s.getGender() + ", Age: " + s.getAge());

        else System.out.println("\nBranchId:" + s.getBranchId() + ", Name: " + s.getName() +  ", StaffId: " + s.getStaffId() + ", Role: " + s.getRole() + ", Gender: " + s.getGender() + ", Age: " + s.getAge());
    }

 
    

  
}
