package utils;

import java.util.Scanner;

public class Sys {
	private static Scanner scan = new Scanner(System.in);
	
	public void cls() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
	
	public void pause() {
		System.out.print("Press enter key to continue...");
		scan.nextLine();
	}
	
	public void next() {
		System.out.print("\nPress enter key to continue...");
		scan.nextLine();
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
}
