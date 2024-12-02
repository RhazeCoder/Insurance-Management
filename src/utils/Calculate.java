package utils;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;

public class Calculate {
	public int age(LocalDate birthDate) throws IOException {
        LocalDate currentDate = LocalDate.now();

        Period age = Period.between(birthDate, currentDate);
        return age.getYears();
    }
	
	public LocalDate endDate(LocalDate startDate, int year) throws IOException {
	    return startDate.plusYears(year);
	}

	public String status(LocalDate start, LocalDate end, LocalDate lastPayment) throws IOException {
		LocalDate currentDate = LocalDate.now();
		
		if (currentDate.isAfter(end)) {
			return "Completed";
		}
		
		int gap = Period.between(lastPayment, currentDate).getMonths();
		
		if (gap < 1) {
			return "Active";
		} else {
			return "Inactive";
		}
	}
}
