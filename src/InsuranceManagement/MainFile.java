package InsuranceManagement;

import java.io.IOException;

import utils.Sys;

public class MainFile {
	public static void main(String[] args) throws IOException {
		Authenticate auth = new Authenticate();
		Sys sys = new Sys();
		MainMenu menu = new MainMenu();
		
		sys.cls();
		
		System.out.println("\tINSURANCE MANAGEMENT SYSTEM\n");
		boolean isAuthenticated = auth.start();
		
		if (isAuthenticated) {
			sys.cls();
			menu.show();
		}
		
		System.exit(1);
	}
}