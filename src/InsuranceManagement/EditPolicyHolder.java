package InsuranceManagement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import utils.Calculate;
import utils.FileSystem;
import utils.Sys;
import utils.ValidateInput;

public class EditPolicyHolder {
    private static Calculate calculate = new Calculate();
    private static FileSystem fs = new FileSystem();
    private static Scanner scan = new Scanner(System.in);
    private static Sys sys = new Sys();
    private static ValidateInput validate = new ValidateInput();

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    private static void editPolicyHolder(List<String> details, String name) throws IOException {
        LocalDate currentBirthdate = LocalDate.parse(details.get(2));
        
        sys.cls();
        System.out.println("\nEditing Policyholder Info (press enter to skip)");
        
        System.out.println("\nCurrent Name: " + details.get(0));
        String newName = validate.editName("Enter new name: ", details.get(0));
        details.set(0, newName == null ? details.get(0) : newName);

        System.out.println("\nCurrent Birthdate: " + currentBirthdate.format(DATE_FORMAT));
        LocalDate newBirthdate = validate.editBirthday("Enter new birthdate (mm/dd/yyyy): ");
        details.set(2, newBirthdate == null ? details.get(2) : newBirthdate.toString());

        int newAge = newBirthdate == null ? calculate.age(currentBirthdate) : calculate.age(newBirthdate);
        details.set(1, String.valueOf(newAge));

        System.out.println("\nCurrent Sex: " + details.get(3));
        String newSex = validate.editSex("Enter new sex: ");
        details.set(3, newSex == null ? details.get(3) : newSex);

        System.out.println("\nCurrent Mobile Number: 0" + details.get(4));
        long newMobileNumber = validate.editMobileNumber("Enter new mobile number: ");
        details.set(4, newMobileNumber == 0 ? details.get(4) : String.valueOf(newMobileNumber));

        System.out.println("\nCurrent Email: " + details.get(5));
        String newEmail = validate.editEmail("Enter new email: ");
        details.set(5, newEmail == null ? details.get(5) : newEmail);

        System.out.println("\nCurrent Address: " + details.get(6));
        String newAddress = validate.editPrompt("Enter new address: ");
        details.set(6, newAddress == null ? details.get(6) : newAddress);

        saveUpdate(details, name);
    }

    private static void editBeneficiary(List<String> details, String name) throws IOException {
        sys.cls();
        System.out.println("\nEditing Beneficiary Info (press enter to skip)");
        
        System.out.println("\nCurrent Name: " + details.get(7));
        String newName = validate.editPrompt("Enter new name: ");
        details.set(7, newName == null ? details.get(7) : newName);

        System.out.println("\nCurrent Relationship: " + details.get(8));
        String newRelationship = validate.editPrompt("Enter new relationship: ");
        details.set(8, newRelationship == null ? details.get(8) : newRelationship);

        System.out.println("\nCurrent Mobile Number: 0" + details.get(9));
        long newBMobileNumber = validate.editMobileNumber("Enter new mobile number: ");
        details.set(9, newBMobileNumber == 0 ? details.get(9) : String.valueOf(newBMobileNumber));

        System.out.println("\nCurrent Email: " + details.get(10));
        String newBEmail = validate.editEmail("Enter new email: ");
        details.set(10, newBEmail == null ? details.get(10) : newBEmail);
        
        saveUpdate(details, name);
    }

    private static void editMembership(List<String> details, String name) throws IOException {
        sys.cls();
        System.out.println("\nEditing Membership Info (press enter to skip)");
        
        System.out.println("\nCurrent Insurance Type: " + details.get(11));
        String newInsuranceType = validate.editInsuranceType("Enter new type (education, health, life): ");
        details.set(11, newInsuranceType == null ? details.get(11) : newInsuranceType);
        
        System.out.println("\nCurrent Premium Year: " + details.get(12));
        int newPremiumYear = validate.editPremiumYear("Enter new premium year (5, 10): ");
        details.set(12, newPremiumYear == 0 ? details.get(12) : String.valueOf(newPremiumYear));
        
        System.out.println("\nCurrent Payment Interval: " + details.get(13));
        String newPaymentType = validate.editPremiumType("Enter new payment interval (monthly, annually): ");
        details.set(13, newPaymentType == null ? details.get(13) : newPaymentType);
        
        System.out.println("\nCurrent payment amount: " + details.get(14));
        int newPaymentAmount = validate.editPaymentAmount("New payment amount (min. ", details.get(13), details.get(14));
        details.set(14, newPaymentAmount == 0 ? details.get(14) : String.valueOf(newPaymentAmount));
        
        System.out.println("\nCurrent Start Date: " + LocalDate.parse(details.get(15)).format(DATE_FORMAT));
        LocalDate newStartDate = validate.editStartDate("Enter new start date (mm/dd/yyyy): ");
        details.set(15, newStartDate == null ? details.get(15) : newStartDate.toString());

        System.out.println("\nCurrent Last Payment Date: " + LocalDate.parse(details.get(16)).format(DATE_FORMAT));
        LocalDate newLastPaymentDate = validate.editLastPaymentDate("Enter new last payment date (mm/dd/yyyy): ");
        details.set(16, newLastPaymentDate == null ? details.get(16) : newLastPaymentDate.toString());

        LocalDate startDate = LocalDate.parse(details.get(15));
        LocalDate endDate = calculate.endDate(startDate, Integer.parseInt(details.get(12)));
        LocalDate lastPayment = LocalDate.parse(details.get(16));

        System.out.println("\nCurrent Status: " + 
        	    (!details.get(17).equalsIgnoreCase("claimed") 
        	     ? calculate.status(startDate, endDate, lastPayment) 
        	     : details.get(17)));

        	String newStatus = validate.editStatus("Enter new status (Claimed, - \"to predict\"): ");
        	details.set(17, newStatus != null ? newStatus : details.get(17));


        saveUpdate(details, name);
    }

    private static void saveUpdate(List<String> details, String name) throws IOException {
        StringBuilder newDetails = new StringBuilder();
        for (String detail : details) {
            newDetails.append(detail).append("\n");
        }

        List<String> records = List.of(fs.read());
        List<String> members = getMembers(records);

        for (int i = 0; i < members.size(); i++) {
            String[] member = members.get(i).split("\n");
            String dbName = member[0];
            
            if (dbName.equalsIgnoreCase(name)) {
                members.set(i, newDetails.toString().trim());
                break;
            }
        }

        List<String> updatedRecords = new ArrayList<>();
        for (int i = 0; i < members.size(); i++) {
            String member = members.get(i).trim();
            updatedRecords.add(member);
            updatedRecords.add("=====");
        }

        fs.writeEdit(updatedRecords);

        System.out.println("\nInformation updated successfully!\n");
    }

    private static List<String> getMembers(List<String> records) {
        List<String> members = new ArrayList<>();
        StringBuilder member = new StringBuilder();

        for (String record : records) {
            if (record.equals("=====")) {
                members.add(member.toString());
                member.setLength(0);
            } else {
                member.append(record).append("\n");
            }
        }

        if (member.length() > 0) {
            members.add(member.toString().trim());
        }

        return members;
    }

    public static void navigateMenu(List<String> details, String name) throws IOException {
        char choice;
        do {
            System.out.println("\n Choose Info to Edit");
            System.out.println("  [a] Policyholder Info");
            System.out.println("  [b] Beneficiary Info");
            System.out.println("  [c] Membership Info");
            System.out.println("  [d] Cancel");
            System.out.print("Enter choice: ");
            choice = scan.next().charAt(0);

            switch (choice) {
                case 'a':
                    editPolicyHolder(details, name);
                    return;
                case 'b':
                    editBeneficiary(details, name);
                    return;
                case 'c':
                    editMembership(details, name);
                    return;
                case 'd':
                    System.out.println("Editing canceled!\n");
                    break;
                default:
                    System.out.println("Invalid Input!\n");
                    break;
            }

        } while (choice != 'd');
    }

    public void start() throws IOException {
        List<String> records = List.of(fs.read());

        if (records.size() <= 0) {
            System.out.println("No records!\n");
            return;
        }

        boolean nameExist = false;
        String ph_name = "";
        
        while (!nameExist) {
            ph_name = validate.editPrompt("\nEdit Policyholder Name: ");
            nameExist = validate.checkExistance(ph_name);
            
            if (!nameExist) {
                System.out.println("\nPolicyholder with that name does not exist!");
                
                System.out.print("\nTypo? Would you like to try again (y/n): ");
                char choice = scan.next().charAt(0);
                scan.nextLine();
                
                if (choice != 'y') return;
                
                sys.cls();
            }
        }
        
        sys.cls();

        List<String> members = getMembers(records);

        for (String member : members) {
            String name = member.split("\n")[0];
            if (name.equalsIgnoreCase(ph_name)) {
                BufferedReader reader = new BufferedReader(new StringReader(member));
                List<String> details = new ArrayList<>();

                String line;
                while ((line = reader.readLine()) != null) {
                    details.add(line);
                }

                navigateMenu(details, ph_name);
            }
        }
    }
}