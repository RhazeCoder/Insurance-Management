package InsuranceManagement;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Authenticate {
    private static Scanner scan = new Scanner(System.in);
    private static final int PINCODE = 1234;

    private int validatePINInput() {
        while (true) {
            try {
                return scan.nextInt();
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Enter PIN: ");
                scan.next();
            }
        }
    }
    
    public boolean start() {
    	while (true) {
    		System.out.print("Enter 4-Digit PIN: ");
    		int enteredPin = validatePINInput(); 
    		
    		if (enteredPin == PINCODE) {
    			System.out.println("Access Granted");
    			return true;
    		}
    		
    		System.out.println("Invalid Pin. Access Denied!");
    	}
    }
    
}
