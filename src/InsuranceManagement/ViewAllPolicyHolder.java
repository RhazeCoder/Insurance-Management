package InsuranceManagement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import utils.Calculate;
import utils.FileSystem;

public class ViewAllPolicyHolder {
    private static Calculate calculate = new Calculate();
    private static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private static FileSystem fs = new FileSystem();

    private static void displayInfo(String member) throws IOException {
        BufferedReader reader = new BufferedReader(new StringReader(member));
        List<String> details = new LinkedList<>();

        String line;
        while ((line = reader.readLine()) != null) {
            details.add(line);
        }

        int premiumYear = Integer.parseInt(details.get(12));
        LocalDate startDate = LocalDate.parse(details.get(15));
        LocalDate endDate = calculate.endDate(startDate, premiumYear);
        LocalDate lastPayment = LocalDate.parse(details.get(16));
        LocalDate birthDate = LocalDate.parse(details.get(2));

        System.out.println("=============== Member Information ===============");
        System.out.println(" Policyholder Info");
        System.out.println("  Name: " + details.get(0));
        System.out.println("  Age: " + details.get(1));
        System.out.println("  Birthdate: " + birthDate.format(dateFormat));
        System.out.println("  Sex: " + details.get(3));
        System.out.println("  Phone #: " + details.get(4));
        System.out.println("  Email: " + details.get(5));
        System.out.println("  Address: " + details.get(6));
        System.out.println("\n Beneficiary Info");
        System.out.println("  Name: " + details.get(7));
        System.out.println("  Relationship w/ Policyholder: " + details.get(8));
        System.out.println("  Phone #: 0" + details.get(9));
        System.out.println("  Email: " + details.get(10));
        System.out.println("\n Membership Info");
        System.out.println("  Insurance Type: " + details.get(11) + " Insurance");
        System.out.println("  Premium Years: " + details.get(12) + " years");
        System.out.println("  Payment Interval: " + details.get(13));
        System.out.println("  Payment Amount: " + details.get(14));
        System.out.println("  Start Date: " + startDate.format(dateFormat));
        System.out.println("  End Date: " + endDate.format(dateFormat));
        System.out.println("  Last Payment Date: " + lastPayment.format(dateFormat));
        System.out.println("  Status: " + (!details.get(17).equalsIgnoreCase("claimed") ? calculate.status(startDate, endDate, lastPayment) : "Claimed"));
        System.out.println("=================================================\n\n");
    }

    private static List<String> getMembers(List<String> records) {
        List<String> members = new LinkedList<>();
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

        List<String> members = getMembers(records);

        for (String member : members) {
            displayInfo(member);
        }
    }
}