package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import java.time.Period;
import java.util.InputMismatchException;
import java.util.List;

public class ValidateInput {
	private static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	private static BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
	private static FileSystem fs = new FileSystem();

	private static boolean isValidDate(String input) throws IOException {
		try {
			String[] parts = input.split("/");
			int month = Integer.parseInt(parts[0]);
			int day = Integer.parseInt(parts[1]);
			int year = Integer.parseInt(parts[2]);
			
			if (month == 2 && day == 29) {
				return LocalDate.of(year, 1, 1).isLeapYear();
			} else if (month == 2 && day > 28) {
				return false;
			}
			
			return true;
			
		} catch(Exception e) {
			return false;
		}
	}
	
	public LocalDate birthday(String user_prompt) throws IOException {
		LocalDate currentDate = LocalDate.now();
		LocalDate date = null;
		int age = -1;

		while (date == null) {
			try {
				System.out.print(user_prompt);
				String input = read.readLine();
				date = LocalDate.parse(input, dateFormat);

				if (!isValidDate(input)) {
					System.out.println("Invalid date. Please check your input.");
	                date = null;
	                continue;
	            }
				 
				age = Period.between(date, currentDate).getYears();

				if (age <= 18 || age >= 65) {
					date = null;
					System.out.println("Policyholder must be 18 to 65 years old.");
				}
			} catch (DateTimeParseException e) {
				System.out.println("Invalid format. Please use (mm/dd/yyyy).");
			}
		}
		return date;
	}
	
	public boolean checkExistance(String nameInput) throws IOException {
	    List<String> records = List.of(fs.read());
	    String content = String.join("\n", records);
	    String[] individualRecords = content.split("=====\\s*");

	    for (String record : individualRecords) {
	        record = record.trim();
	        String[] lines = record.split("\n");

	        if (lines.length > 0) {
	            String name = lines[0].trim();
	            if (name.equalsIgnoreCase(nameInput)) return true;
	        }
	    }

	    return false;
	}
	
	public LocalDate editBirthday(String user_prompt) throws IOException {
	    LocalDate currentDate = LocalDate.now();
	    LocalDate date = null;
	    int age = -1;

	    while (true) {
	        System.out.print(user_prompt);
	        String input = read.readLine().trim();

	        if (input.isEmpty()) {
	            return null;
	        }

	        try {
	            date = LocalDate.parse(input, dateFormat);
	            
	            if (!isValidDate(input)) {
					System.out.println("Invalid date. Please check your input.");
	                date = null;
	                continue;
	            }
	            
	            age = Period.between(date, currentDate).getYears();

	            if (age <= 18 || age >= 65) {
	                date = null;
	                System.out.println("Policyholder must be 18 to 65 years old.");
	            } else {
	                break;
	            }
	        } catch (DateTimeParseException e) {
	            System.out.println("Invalid format. Please use (mm/dd/yyyy).");
	        }
	    }

	    return date;
	}
	
	public String editEmail(String prompt) throws IOException {
		String input = "";
		while ((!input.contains("@"))) {
			System.out.print(prompt);
			input = read.readLine().trim();
			
			if (input.isEmpty()) {
	            return null;
	        }
			
			if ((!input.contains("@"))) {
				System.out.println("Invalid input. Please enter a valid email");
			}
		}
		return input;
	}
	
	public String editInsuranceType(String prompt) throws IOException {
	    String input = "";
	    while (!input.equalsIgnoreCase("education") && 
	           !input.equalsIgnoreCase("health") && !input.equalsIgnoreCase("life")) {
	        System.out.print(prompt);
	        input = read.readLine().trim();
	        
	        if (input.isEmpty()) {
	            return null;
	        }
	        
	        if (!input.equalsIgnoreCase("education") && 
	        	!input.equalsIgnoreCase("health") && !input.equalsIgnoreCase("life")) {
	            System.out.println("Invalid input. Please enter education, health or life");
	        }
	    }
	    return input;
	}

	public LocalDate editLastPaymentDate(String user_prompt) throws IOException {
	    LocalDate currentDate = LocalDate.now();
	    LocalDate date = null;
	    int gap = 0;

	    while (date == null) {
	        try {
	            System.out.print(user_prompt);
	            String input = read.readLine().trim();
	            
	            if (input.isEmpty()) {
	                return null;
	            }
	            
	            date = LocalDate.parse(input, dateFormat);
	            
	            if (!isValidDate(input)) {
					System.out.println("Invalid date. Please check your input.");
	                date = null;
	                continue;
	            }
	            gap = Period.between(date, currentDate).getDays();
	            if (gap < 0) {
	                date = null;
	                System.out.println("The last payment date must not be later than today.");
	            }
	        } catch (DateTimeParseException e) {
	            System.out.println("Invalid format. Please use (mm/dd/yyyy).");
	        }
	    }
	    return date;
	}
	
	public long editMobileNumber(String prompt) throws IOException {
		while (true) {
			System.out.print(prompt);
			try {
				String input = read.readLine();
				String prefix = null;
				
				if (input.isEmpty()) {
		            return 0;
		        }
				
				if (input.length() >= 2) {
					prefix = input.substring(0, 2);
				}
				
				if (input.length() == 11 && prefix.equals("09")) {
					return Long.parseLong(input);
				}
				
				System.out.println("Invalid input. Please input a valid mobile number.");
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please input a valid mobile number.");
			}
		}
	}
	
	public String editName(String prompt, String currentName) throws IOException {
	    String input = "";
	    while (input == "") {
	        System.out.print(prompt);
	        input = read.readLine().trim();
	        
	        if (input.isEmpty()) {
	            return null;
	        }
	        
	        boolean nameExist = checkExistance(input);
	        
	        if (currentName.equalsIgnoreCase(input)) return input;
	        
	        if (nameExist) {
	        	System.out.println("That person is already a member!");
	        	input = "";
	        }
	    }
	    return input;
	}


	public int editPaymentAmount(String prompt, String paymentType, String current) throws IOException {
	    int minimum = paymentType.equals("annually") ? 4200 : 400;
	    int input = 0;

	    while(true) {
	        try {
	            System.out.print(prompt + minimum + "): ");
	            String inputString = read.readLine().trim();
	            
	            if (inputString.isEmpty() && input >= minimum) {
	                return 0;
	            }
	            
	            inputString = inputString.isEmpty() ? current : inputString;
	            
	            input = Integer.parseInt(inputString);
	            
	            if (input >= minimum) {
	                break;
	            }

	            System.out.println("Enter amount with minimum: " + minimum);

	        } catch(NumberFormatException e) {
	            System.out.println("Please enter a numeric value.");
	        }
	    }

	    return input;
	}

	public String editPremiumType(String prompt) throws IOException {
	    String input = "";
	    while (!input.equalsIgnoreCase("monthly") && !input.equalsIgnoreCase("annually")) {
	        System.out.print(prompt);
	        input = read.readLine().trim();
	        
	        if (input.isEmpty()) {
	            return null;
	        }
	        
	        if (!input.equalsIgnoreCase("monthly") && !input.equalsIgnoreCase("annually")) {
	            System.out.println("Invalid input. Please enter monthly or annually only.");
	        }
	    }
	    return input;
	}

	public int editPremiumYear(String prompt) throws IOException {
	    int input = 0;
	    while (input != 5 && input != 10) {
	        System.out.print(prompt);
	        try {
	            String inputStr = read.readLine().trim();
	            
	            if (inputStr.isEmpty()) {
	                return 0;
	            }
	            
	            input = Integer.parseInt(inputStr);
	            if (input != 5 && input != 10) {
	                System.out.println("Invalid input. Please enter only 5 or 10.");
	            }
	        } catch (NumberFormatException e) {
	            System.out.println("Invalid input. Please enter a numeric value (5 or 10).");
	        }
	    }
	    return input;
	}
	
	public String editPrompt(String prompt) throws IOException {
		String input = null;
		
		System.out.print(prompt);
		input = read.readLine().trim();
		
		if (input.isEmpty()) {
            return null;
        }
		
		return input;
	}
	
	public String editStatus(String prompt) throws IOException {
		String input = "";
		while (!input.equalsIgnoreCase("claimed") && !input.equalsIgnoreCase("-")) {
			System.out.print(prompt);
			input = read.readLine().trim();
			
			if (input.isEmpty()) {
				return null;
			}
			
			if (!input.equalsIgnoreCase("claimed") && !input.equalsIgnoreCase("-")) {
				System.out.println("Invalid input. Claimed and - only.");
			}
		}
		return input;
	}
	
	public LocalDate editStartDate(String user_prompt) throws IOException { 
	    LocalDate currentDate = LocalDate.now();
	    LocalDate date = null;
	    int gap = 0;

	    while (date == null) {
	        try {
	            System.out.print(user_prompt);
	            String input = read.readLine().trim();
	            
	            if (input.isEmpty()) {
	                return null;
	            }
	            
	            date = LocalDate.parse(input, dateFormat);
	            
	            if (!isValidDate(input)) {
					System.out.println("Invalid date. Please check your input.");
	                date = null;
	                continue;
	            }
	            
	            gap = Period.between(date, currentDate).getDays();
	            if (gap < 0) {
	                date = null;
	                System.out.println("The start date must not be later than today.");
	            }
	        } catch (DateTimeParseException e) {
	            System.out.println("Invalid format. Please use (mm/dd/yyyy).");
	        }
	    }
	    
	    return date;
	}

	public String editSex(String prompt) throws IOException {
		String input = "";
		while (!input.equalsIgnoreCase("male") && !input.equalsIgnoreCase("female")) {
			System.out.print(prompt);
			input = read.readLine().trim();
			
			if (input.isEmpty()) {
                return null;
            }
			
			if (!input.equalsIgnoreCase("male") && !input.equalsIgnoreCase("female")) {
				System.out.println("Invalid input. Please enter male or female only.");
			}
		}
		return input;
	}
	
	public String email(String prompt) throws IOException {
		String input = "";
		while (!input.contains("@")) {
			System.out.print(prompt);
			input = read.readLine().trim();
			if ((!input.contains("@"))) {
				System.out.println("Invalid input. Please enter a valid email");
			}
		}
		return input;
	}
	
	public String insuranceType(String prompt) throws IOException {
		String input = null;
		while (input == null || (!input.equalsIgnoreCase("education") && 
			   !input.equalsIgnoreCase("health") && !input.equalsIgnoreCase("life"))) {
			System.out.print(prompt);
			input = read.readLine().trim();
			if (!input.equalsIgnoreCase("education") && !input.equalsIgnoreCase("health") &&
				!input.equalsIgnoreCase("life")) {
				System.out.println("Invalid input. Please enter education, health and life");
			}
		}
		return input;
	}

	public LocalDate lastPayment(String user_prompt) throws IOException {
		LocalDate currentDate = LocalDate.now();
		LocalDate date = null;
		int gap = 0; 

		while (date == null) {
			try {
				System.out.print(user_prompt);
				String input = read.readLine();
				date = LocalDate.parse(input, dateFormat);
				
				if (!isValidDate(input)) {
					System.out.println("Invalid date. Please check your input.");
	                date = null;
	                continue;
	            }
				
				gap = Period.between(date, currentDate).getDays();
				if (gap < 0) {
					date = null;
					System.out.println("The last payment date must not be later than today.");
				}
			} catch (DateTimeParseException e) {
				System.out.println("Invalid format. Please use (mm/dd/yyyy).");
			}
		}
		return date;
	}
	
	public long mobileNumber(String prompt) throws IOException {
		while (true) {
			System.out.print(prompt);
			try {
				String input = read.readLine();
				String prefix = null;
				
				if (input.length() >= 2) {
					prefix = input.substring(0, 2);
				}
				
				if (input.length() == 11 && prefix.equals("09")) {
					return Long.parseLong(input);
				}
				
				System.out.println("Invalid input. Please input a valid mobile number.");
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please input a valid mobile number.");
			}
		}
	}
	
	public int paymentAmount(String prompt, String paymentType) throws IOException {
		int minimum = paymentType.equals("annually") ? 4200 : 400;
		int input = 0;
		
		while(true) {
			try {
				System.out.print(prompt + minimum + "): ");
				input = Integer.parseInt(read.readLine().trim());
				
				if (input >= minimum) {
					break;
				}
				
				System.out.println("Enter amount with minimum: " + minimum);
				
			} catch(NumberFormatException e) {
				System.out.println("Please enter a numeric value.");
			}
		}
		
		return input;
	}
	
	public String premiumType(String prompt) throws IOException {
		String input = null;
		while (input == null || (!input.equalsIgnoreCase("monthly") && !input.equalsIgnoreCase("annually"))) {
			System.out.print(prompt);
			input = read.readLine().trim();
			if (!input.equalsIgnoreCase("monthly") && !input.equalsIgnoreCase("annually")) {
				System.out.println("Invalid input. Please enter monthly or annually only.");
			}
		}
		return input;
	}

	public int premiumYear(String prompt) throws IOException {
		int input = 0;
		while (input != 5 && input != 10) {
			System.out.print(prompt);
			try {
				input = Integer.parseInt(read.readLine().trim());
				if (input != 5 && input != 10) {
					System.out.println("Invalid input. Please enter only 5 or 10.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please enter a numeric value (5 or 10).");
			}
		}
		return input;
	}

	public LocalDate promptDate(String user_prompt) throws IOException {
		LocalDate date = null;

		while (date == null) {
			try {
				String input = prompt(user_prompt);
				date = LocalDate.parse(input, dateFormat);
				
				if (!isValidDate(input)) {
					System.out.println("Invalid date. Please check your input.");
	                date = null;
	                continue;
	            }
				
			} catch (DateTimeParseException e) {
				System.out.println("Invalid format. Please use (dd/mm/yyyy).");
			} catch (InputMismatchException e) {
				System.out.println("Invalid format. Please use (dd/mm/yyyy).");
			}
		}

		return date;
	}

	public String prompt(String prompt) throws IOException {
		String input = null;
		
		while (input == null || input.isEmpty()) {
			System.out.print(prompt);
			input = read.readLine().trim();
		}
		
		return input;
	}

	public int promptInt(String prompt) throws IOException {
	    while (true) {
	        System.out.print(prompt);
	        try {
	            String input = read.readLine().trim();
	            if (input == null) {
	                throw new IOException("Input stream closed.");
	            }
	            return Integer.parseInt(input);
	        } catch (NumberFormatException e) {
	            System.out.println("Invalid input. Please enter a numeric value.");
	        }
	    }
	}


	public String sex(String prompt) throws IOException {
		String input = null;
		while (input == null || (!input.equalsIgnoreCase("male") && !input.equalsIgnoreCase("female"))) {
			System.out.print(prompt);
			input = read.readLine().trim();
			if (!input.equalsIgnoreCase("male") && !input.equalsIgnoreCase("female")) {
				System.out.println("Invalid input. Please enter male or female only.");
			}
		}
		return input;
	}
	
	public LocalDate startDate(String user_prompt) throws IOException {
		LocalDate currentDate = LocalDate.now();
		LocalDate date = null;
		int gap = 0;

		while (date == null) {
			try {
				System.out.print(user_prompt);
				String input = read.readLine();
				date = LocalDate.parse(input, dateFormat);
				
				if (!isValidDate(input)) {
					System.out.println("Invalid date. Please check your input.");
	                date = null;
	                continue;
	            }
				
				gap = Period.between(date, currentDate).getDays();
				if (gap < 0) {
					date = null;
					System.out.println("The start date must not be later than today.");
				}
			} catch (DateTimeParseException e) {
				System.out.println("Invalid format. Please use (mm/dd/yyyy).");
			}
		}
		return date;
	}
}