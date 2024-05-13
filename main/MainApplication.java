

package main;

import customer_ui.CustomerMainPage;
import staff_ui.*;
import users.*;
import login_system.LoginPage;
import db.*;
import java.util.Scanner;

/**
The main entry point of the application.
This class initializes the database controllers, sets up the scanner for user input, and manages the main application loop. It handles user login, navigates to appropriate user interfaces (customer or staff), and ensures proper shutdown procedures, including saving data to files.
*/
public class MainApplication {

    private static String userDBFile = "resources/staff_list.csv";
    private static String branchDBFile = "resources/branch_list.csv";
    private static String orderListFile = "resources/orderlist.csv";
    private static String paymentDBFile = "resources/payment.csv";

    public static void main (String[] args){
        UserDBController userDBController = new UserDBController();
        userDBController.readDB(userDBFile);

        BranchDBController branchDBController = new BranchDBController();
        branchDBController.readDB(branchDBFile);

      

        OrderListController orderListController = new OrderListController(branchDBController);
        orderListController.readDB(orderListFile);



        OrderChecker checker = OrderChecker.getInstance(orderListController);
        

        PaymentMethodDBController paymentMethodDBController = new PaymentMethodDBController();
        paymentMethodDBController.readDB(paymentDBFile);


        Scanner scanner = new Scanner(System.in);


        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\n\nShutting down...");
            checker.stopChecker();  
            userDBController.writeDB(userDBFile);
            paymentMethodDBController.writeDB(paymentDBFile);
            branchDBController.writeDB(branchDBFile);
            orderListController.writeDB(orderListFile);
            scanner.close();
        }));


        

        LoginPage loginPage = new LoginPage(scanner, userDBController, branchDBController);


        while (true){
            boolean working = loginPage.genUI();

            if (working){
                User user = loginPage.getUser();


                if (user instanceof Customer){
                    CustomerMainPage customerMainPage = new CustomerMainPage(scanner, paymentMethodDBController, (Customer) user, branchDBController, orderListController);

                    customerMainPage.genUI();
                }

                else{
                    Staff staff = (Staff) user;

                    if (staff.getRole() == 'M'){
                        ManagerPage managerPage = new ManagerPage(scanner, staff, orderListController, userDBController, branchDBController);

                        managerPage.genUI();
                    }

                    else if (staff.getRole() == 'A'){
                        AdminPage adminPage = new AdminPage(scanner, staff, userDBController, branchDBController, paymentMethodDBController);
                        adminPage.genUI();
                    }

                    else{
                        BranchStaffPage branchStaffPage = new BranchStaffPage(scanner, staff, orderListController);
                        branchStaffPage.genUI();
                    }
            
                }
                System.out.println("\n\n\n\n\n\n\n\n\n");
            }
            else{
                break;
            }
        }


        scanner.close();
        


    }
    
}
