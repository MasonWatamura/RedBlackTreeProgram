package program2cs232;

public class CCData{
	
	public static class Date{  //separate date class that stores the date and changes the format if needed
		private String date;
		public Date(String date) {
			this.date = date;
		}
		
		public String getDate() {
			if (date.contains("-")) { //changing how the date is formatted so everything is the same
				String[] dateSplit = date.split("-");
				String split = dateSplit[0];  //sets a variable to the year
				dateSplit[0] = dateSplit[1];  //changes where the year was to the month
				dateSplit[1] = dateSplit[2];  //the month changes to the day
				dateSplit[2] = split;         //changes the day to the set variable of the year
				date = String.join("/", dateSplit);  //joins all of it together forming the date
			}
			return date;
		}
	}
	
	private String date;  //creating date variable to store the date
	private double numbers; //creating number variable to store the numerical data entry
	
	public CCData(String date, double numbers) {
		this.date = date;
		this.numbers = numbers;
	}
	
	public String getDate() {  //returns the date thats associated with the data being used
		return date;
	}
	public double getNumber() { //returns the number thats associated with the data being used
		return numbers;
	}
}
