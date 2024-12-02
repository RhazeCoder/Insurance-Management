package InsuranceManagement;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import utils.Calculate;
import utils.FileSystem;
import utils.Sys;
import utils.ValidateInput;

public class SearchPolicyHolder {
    private static Calculate calculate = new Calculate();
    private static FileSystem fs = new FileSystem();
    private static Sys sys = new Sys();
    private static ValidateInput validate = new ValidateInput();
    
    // Define the date formatter
    private static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    
    private static void displayInfo(String member) throws IOException {
        String[] details = member.split("\n");

        int premiumYear = Integer.parseInt(details[12]);
        LocalDate birthDate = LocalDate.parse(details[2]);
        LocalDate startDate = LocalDate.parse(details[15]);
        LocalDate endDate = calculate.endDate(startDate, premiumYear);
        LocalDate lastPayment = LocalDate.parse(details[16]);

        System.out.println("=============== Member Information ===============");
        System.out.println(" Policy Holder Info");
        System.out.println("  Name: " + details[0]);
        System.out.println("  Age: " + details[1]);
        System.out.println("  Birthdate: " + birthDate.format(dateFormat));
        System.out.println("  Sex: " + details[3]);
        System.out.println("  Phone #: " + details[4]);
        System.out.println("  Email: " + details[5]);
        System.out.println("  Address: " + details[6]);
        System.out.println("\n Beneficiary Info");
        System.out.println("  Name: " + details[7]);
        System.out.println("  Relationship w/ Policy Holder: " + details[8]);
        System.out.println("  Phone #: " + details[9]);
        System.out.println("  Email: " + details[10]);
        System.out.println("\n Membership Info");
        System.out.println("  Insurance Type: " + details[11] + " Insurance");
        System.out.println("  Premium Years: " + details[12] + " years");
        System.out.println("  Payment Interval: " + details[13]);
        System.out.println("  Payment Amount: " + details[14]);
        System.out.println("  Start Date: " + startDate.format(dateFormat));
        System.out.println("  End Date: " + endDate.format(dateFormat));
        System.out.println("  Last Payment Date: " + lastPayment.format(dateFormat));
        System.out.println("  Status: " + (!details[17].equalsIgnoreCase("claimed") ? calculate.status(startDate, endDate, lastPayment) : "Claimed"));
        System.out.println("=================================================\n\n");
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

    public void start() throws IOException {
        List<String> records = List.of(fs.read());

        if (records.size() <= 0) {
            System.out.println("No records!\n");
            return;
        }
        
        String ph_name = validate.prompt("\nSearch Policy Holder Name: ");
        boolean nameExist = validate.checkExistance(ph_name);

        if (!nameExist) {
            System.out.println("\nPolicy Holder with that name does not exist!\n");
            return;
        }
        
        sys.cls();

        List<String> members = getMembers(records);

        for (String member : members) {
            String name = member.split("\n")[0];
            if (name.equalsIgnoreCase(ph_name)) {
                displayInfo(member);
            }
        }
    }
}