

package customer_ui;

import java.util.Scanner;
/**
Handles the option for customers to choose takeaway or dine-in for their orders.
*/
public class TakeoutHandler {

	private Scanner scanner;
	
	public TakeoutHandler(Scanner scanner) {
		this.scanner = scanner;
	}
	
	public int checkTakeAway(){
		String option;
		
		while (true) {
			System.out.println("Would you like to takeaway?");
			System.out.println("1. Yes  2: No");
			
			option = scanner.nextLine();
			
					
			if (option.equals("1")) {
				return 1;
			}
			
			else if (option.equals("2")) {
				return 2;
			}
			
			else if (option.equals("-1")) {
				return 0;
			}
			
			else {
				System.out.println("Invalid input. Try again or input -1 to cancel payment.");
			}
		}
	}
}
