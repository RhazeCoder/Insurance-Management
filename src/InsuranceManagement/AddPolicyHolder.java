package InsuranceManagement;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

import utils.Calculate;
import utils.FileSystem;
import utils.Sys;
import utils.ValidateInput;

public class AddPolicyHolder {
	private static Calculate calculate = new Calculate();
	private static FileSystem fs = new FileSystem();
	private static Scanner scan = new Scanner(System.in);
	private static Sys sys = new Sys();
	private static ValidateInput validate = new ValidateInput();
	
	private static String addData() throws IOException {
	    System.out.println("\n Policy Holder Info");
	    String ph_name = validate.prompt("  Name: ");
	    boolean nameExist = validate.checkExistance(ph_name);
	    
	    if (nameExist) {
	    	System.out.println("\nPolicy Holder with that name already exist!");
	    	return "existed";
	    }
	    
	    LocalDate ph_birthday = validate.birthday("  Birthdate (mm/dd/yyyy): ");
	    int ph_age = calculate.age(ph_birthday);
	    String sex = validate.sex("  Sex (male, female): ");
	    long ph_mobileNumber = validate.mobileNumber("  Mobile number: ");
	    String ph_email = validate.email("  Email: ");
	    String ph_address = validate.prompt("  Address: ");

	    System.out.println("\n Beneficiary Info");
	    String b_name = validate.prompt("  Name: ");
	    String b_relationship = validate.prompt("  Relationship with policy holder: ");
	    long b_mobileNumber = validate.mobileNumber("  Mobile number: ");
	    String b_email = validate.email("  Email: ");

	    System.out.println("\n Membership Info");
	    String insurance_type = validate.insuranceType("  Insurance type (education, health, life): ");
	    int premium_year = validate.premiumYear("  Premium year (5, 10): ");
	    String payment_type = validate.premiumType("  Premium payment interval (monthly, annually): ");
	    int payment_amount = validate.paymentAmount("  Payment amount (min. ", payment_type);
	    LocalDate startDate = validate.startDate("  Start date (mm/dd/yyyy): ");
	    LocalDate lastPaymentDate = validate.lastPayment("  Last payment date (mm/dd/yyyy): ");

	    String data = ph_name + "\n" + ph_age + "\n" + ph_birthday + "\n" + sex + "\n" + ph_mobileNumber + "\n" + 
	    			  ph_email +  "\n" + ph_address + "\n" +  b_name + "\n" + b_relationship + "\n" + b_mobileNumber + "\n" + 
	    			  b_email + "\n" + insurance_type + "\n" + premium_year + "\n" + payment_type + "\n" + 
	    			  payment_amount + "\n" + startDate + "\n" + lastPaymentDate + "\n-\n=====";

	    return data;
	}

	
    public void start() throws IOException {
        boolean addMore = false;
        
        do {
        	String data = addData();
        	if (!data.equals("existed"))  {
        		fs.write(data);
        		System.out.println("\nRecord saved!");
        	}
        	System.out.print("\nAdd more policy holder? (y/n): ");
        	char choice = scan.next().charAt(0);
        	addMore = (choice == 'y') ? true : false;
        	sys.cls();
        } while(addMore);
    }
}