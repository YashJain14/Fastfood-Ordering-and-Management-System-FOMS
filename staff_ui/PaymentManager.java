

package staff_ui;

import orders.PaymentMethodEnum;

import java.util.*;

import db.PaymentMethodDBController;
/**
Manages the availability of different payment methods for the application.
This class allows admin users to enable or disable payment methods like Credit Card, PayNow, and PayPal, and to view the current status of available payment options.
*/
public class PaymentManager {
    private HashMap <PaymentMethodEnum, Integer> paymentMethodsStatus;
    private PaymentMethodDBController paymentMethodDBController;

    private Scanner scanner;

    public PaymentManager(Scanner scanner, PaymentMethodDBController paymentMethodDBController) {

        this.paymentMethodDBController = paymentMethodDBController;

        paymentMethodsStatus = new HashMap<>();

        paymentMethodsStatus.put(PaymentMethodEnum.CreditCardPayment, paymentMethodDBController.isAvailable(PaymentMethodEnum.CreditCardPayment) ? 1:0);
        paymentMethodsStatus.put(PaymentMethodEnum.PaynowPayment, paymentMethodDBController.isAvailable(PaymentMethodEnum.PaynowPayment) ? 1:0);
        paymentMethodsStatus.put(PaymentMethodEnum.PayPal, paymentMethodDBController.isAvailable(PaymentMethodEnum.PayPal) ? 1:0);
        
        this.scanner = scanner;


        
    }

    public void managePayments() {
        String input;
        while (true) {
            System.out.println("\nAdd/Remove Payment Methods:");
            System.out.println("1. Add Payment");
            System.out.println("2. Remove Payment");
            System.out.println("3. Show Current Payment Methods");
            System.out.println("4. Exit\n");
            System.out.print("Choose an option: ");
            input = scanner.nextLine();

            switch (input) {
                case "1":
                    addPayment(scanner);
                    break;
                case "2":
                    removePayment(scanner);
                    break;
                case "3":
                    showPayments();
                    break;
                case "4":
                    System.out.println("Exiting Payment Manager.\n\n");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private void addPayment(Scanner scanner) {
        System.out.println("Available Payments to Add:");

        for (PaymentMethodEnum paymentMethodEnum : paymentMethodsStatus.keySet()){
            if (paymentMethodsStatus.get(paymentMethodEnum) == 0)
                System.out.println(paymentMethodEnum);
        }

       

        System.out.print("Enter Payment Method to Enable (CreditCard, PayNow, PayPal): ");
        String methodName = scanner.nextLine().trim();
        PaymentMethodEnum key = methodName.equalsIgnoreCase("CreditCard") ? PaymentMethodEnum.CreditCardPayment :
                     methodName.equalsIgnoreCase("PayNow") ? PaymentMethodEnum.PaynowPayment :
                     methodName.equalsIgnoreCase("PayPal") ? PaymentMethodEnum.PayPal : null;

        if (key != null && paymentMethodsStatus.containsKey(key) && (paymentMethodsStatus.get(key) == 0)) {
            paymentMethodsStatus.replace(key, 1);
            paymentMethodDBController.setAvailable(key, 1);
            System.out.println(key + " has been enabled.");
        } else {
            System.out.println("Invalid method name or method already enabled.");
        }
    }

    private void removePayment(Scanner scanner) {
        System.out.println("Enabled Payments:");
        for (PaymentMethodEnum paymentMethodEnum : paymentMethodsStatus.keySet()){
            if (paymentMethodsStatus.get(paymentMethodEnum) == 0)
                System.out.println(paymentMethodEnum);
        }


        System.out.print("Enter Payment Method to Disable (CreditCard, PayNow, PayPal): ");
        String methodName = scanner.nextLine().trim();
        PaymentMethodEnum key = methodName.equalsIgnoreCase("CreditCard") ? PaymentMethodEnum.CreditCardPayment :
                     methodName.equalsIgnoreCase("PayNow") ? PaymentMethodEnum.PaynowPayment :
                     methodName.equalsIgnoreCase("PayPal") ? PaymentMethodEnum.PayPal : null;

        System.out.println(methodName);
        System.out.println(paymentMethodsStatus.get(key));

        if (key != null && paymentMethodsStatus.containsKey(key) && (paymentMethodsStatus.get(key) == 1)) {
            paymentMethodsStatus.replace(key, 0);
            System.out.println(key + " has been disabled.");

            paymentMethodDBController.setAvailable(key, 0);
        } else {
            System.out.println("Invalid method name or method already disabled.");
        }
    }

    private void showPayments() {
        System.out.println("Current Payment Methods Status:");
        for (PaymentMethodEnum paymentMethodEnum : paymentMethodsStatus.keySet()){
            System.out.println(paymentMethodEnum + " : " + (paymentMethodsStatus.get(paymentMethodEnum) == 1));
        }
    }
}
