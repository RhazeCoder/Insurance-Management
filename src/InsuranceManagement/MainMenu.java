package InsuranceManagement;

import java.io.IOException;
import java.util.Scanner;

import utils.Sys;

public class MainMenu {
    private static Scanner scan = new Scanner(System.in);
    private static Sys sys = new Sys();

    // menu
    private static AddPolicyHolder add = new AddPolicyHolder();
    private static ViewAllPolicyHolder viewall = new ViewAllPolicyHolder();
    private static SearchPolicyHolder search = new SearchPolicyHolder();
    private static EditPolicyHolder edit = new EditPolicyHolder();
    
    private void navigateMenu(char choice) throws IOException {    
        switch(choice) {
            case 'a':
                add.start();
                break;
            case 'b':
                viewall.start();
                sys.pause();
                break;
            case 'c':
                search.start();
                sys.pause();
            break;
            case 'd':
                edit.start();
                sys.pause();
                break;
            case 'e':
                System.out.println("System Closed!");
                scan.close();
                break;
            default:
                System.out.println("\nInvalid Input!");
                break;
        }
    }
    
    public void show() throws IOException {
        char choice;
        do {
        	sys.cls();
            System.out.println("\n\t+|  MAIN MENU  |+");
            System.out.println("\t  [a] Add");
            System.out.println("\t  [b] View All");
            System.out.println("\t  [c] Search");
            System.out.println("\t  [d] Edit");
            System.out.println("\t  [e] Exit");
            System.out.print("\nEnter choice: ");
            choice = scan.next().charAt(0);
            sys.cls();
            navigateMenu(choice);
        } while(choice != 'e');
    }
}
