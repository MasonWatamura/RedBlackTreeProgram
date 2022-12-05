package program2cs232;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Program2cs232 {
	
	public static void start() throws FileNotFoundException {
		//gets files set up to be read
		File co2 = new File("co2.csv");
		File seaLevel = new File("sea_level.csv");
		File temperature = new File("temperature_anomaly.csv");  //has multiple Entities. Need to go to just World
		Scanner co2In = new Scanner(co2);
		Scanner seaIn = new Scanner(seaLevel);
		Scanner temperatureIn = new Scanner(temperature);
		
		//initializes red black trees
		RBtree<String, Double> co2date = new RBtree<String, Double>();
		RBtree<Double, String> co2highlow = new RBtree<Double, String>();
		RBtree<String, Double> seaLeveldate = new RBtree<String, Double>();
		RBtree<Double, String> seaLevelhighlow = new RBtree<Double, String>();
		RBtree<String, Double> tempdate = new RBtree<String, Double>();
		RBtree<Double, String> temphighlow = new RBtree<Double, String>();
		
		//read one file at a time
		
		//starting with the seaLevel file
		seaIn.nextLine(); //skips first line with titles
		while(seaIn.hasNextLine()) {  //going through the file line by line
			String[] a = seaIn.nextLine().split(",");
			CCData.Date date = new CCData.Date(a[2]);  //changes how the date looks
			//many of the comments will be the same for what happens in the next while loops for the other files
			CCData data = new CCData(date.getDate(), Double.parseDouble(a[3])); //puts the data into the data class
			seaLevelhighlow.put(data.getNumber(), data.getDate()); //puts number as the key and date as the value
			seaLeveldate.put(data.getDate(), data.getNumber());  //puts date as the key and number as the value to be compared to for printing
		}
		
		//reading co2 file
		co2In.nextLine(); //skips first line with titles
		while(co2In.hasNextLine()) {  //going through the file line by line
			String[] a = co2In.nextLine().split(",");
			CCData.Date date = new CCData.Date(a[2]);
			CCData data = new CCData(date.getDate(), Double.parseDouble(a[3]));
			co2highlow.put(data.getNumber(), data.getDate());
			co2date.put(data.getDate(), data.getNumber());	
		}
		
		//reading temperature file
		temperatureIn.nextLine();  //skips first line with titles
		while(temperatureIn.hasNextLine()) { //going through the file line by line
			String[] a = temperatureIn.nextLine().split(",");
			if(a[0].equals("World")) { //goes through only if World is detected ignoring Northern and Southern hemisphere
				CCData.Date date = new CCData.Date(a[2]);
				CCData data = new CCData(date.getDate(), Double.parseDouble(a[3]));
				temphighlow.put(data.getNumber()*1.8, data.getDate());
				tempdate.put(data.getDate(), data.getNumber()*1.8);
			}	
		}
		
		//closing files
		seaIn.close();
		co2In.close();
		temperatureIn.close();
		
		FileOut fout = new FileOut("output.txt");
		//printing minimum and maximum keys with values
		//also prints the data from the same date
		DecimalFormat round = new DecimalFormat("##.00");  //for the temperatures that try to extend to multiple decimal places
		//prints the minimum key and the value associated with it
		//starts by printing for sea level
		//**********************************************************************
		fout.writer("Lowest Sea Level Rise: " + seaLevelhighlow.min() + " on " + seaLevelhighlow.get(seaLevelhighlow.min()));
		//the comments for this for statement will be the same for all the other for statements (just have different red black trees they are looking through)
		for(String s : co2date.keys()) { //goes through the for statement searching if the same date (key) is in the next red black tree
			if(s.equals(seaLevelhighlow.get(seaLevelhighlow.min()))) {  //if there is a match print the next line
				fout.writer("On that same date, the Average Co2 concentration was " + co2date.get(s));
			}
		}
		for(String s : tempdate.keys()) {
			if(s.equals(seaLevelhighlow.get(seaLevelhighlow.min()))) {
				fout.writer("On that same date, the Temperature Anomaly (F) was " + round.format(tempdate.get(s)));
			}
		}
		fout.writer("\n");
		
		//prints the maximum key and the value associated with it
		fout.writer("Highest Sea Level Rise: " + seaLevelhighlow.max() + " on " + seaLevelhighlow.get(seaLevelhighlow.max()));
		for(String s : co2date.keys()) {
			if(s.equals(seaLevelhighlow.get(seaLevelhighlow.max()))) {
				fout.writer("On that same date, the Average Co2 concentration was " + co2date.get(s));
			}
		}
		for(String s : tempdate.keys()) {
			if(s.equals(seaLevelhighlow.get(seaLevelhighlow.max()))) {
				fout.writer("On that same date, the Temperature Anomaly (F) was " + tempdate.get(s));
			}
		}
		fout.writer("\n");
		//**********************************************************************
		//then prints for average co2
		//prints the minimum key and the value associated with it
		fout.writer("Lowest Average Co2 concentration: " + co2highlow.min() + " on " + co2highlow.get(co2highlow.min()));
		for(String s : tempdate.keys()) {
			if(s.equals(co2highlow.get(co2highlow.min()))) {
				fout.writer("On that same date, the Temperature Anomaly (F) was " + round.format(tempdate.get(s)));
			}
		}
		fout.writer("\n");
		
		//prints the maximum key and the value associated with it
		fout.writer("Highest Average Co2 concentration: " + co2highlow.max() + " on " + co2highlow.get(co2highlow.max()));
		for(String s : tempdate.keys()) {
			if(s.equals(co2highlow.get(co2highlow.max()))) {
				fout.writer("On that same date, the Temperature Anomaly (F) was " + round.format(tempdate.get(s)));
			}
		}
		fout.writer("\n");
		//**********************************************************************
		//finally prints for temperature
		//prints the minimum key and the value associated with it
		fout.writer("Lowest Temperature anomaly (F): " + temphighlow.min() + " on " + temphighlow.get(temphighlow.min()));
		for(String s : co2date.keys()) {
			if(s.equals(temphighlow.get(temphighlow.min()))) {
				fout.writer("On that same date, the Average Co2 concentration was " + co2date.get(s));
			}
		}
		fout.writer("\n");
		
		//prints the maximum key and the value associated with it
		fout.writer("Highest Temperature anomaly (F): " + temphighlow.max() + " on " + temphighlow.get(temphighlow.max()));
		for(String s : co2date.keys()) {
			if(s.equals(temphighlow.get(temphighlow.max()))) {
				fout.writer("On that same date, the Average Co2 concentration was " + co2date.get(s));
			}
		}
	}
	public static void main(String[]args) throws FileNotFoundException {
		start();
	}
}
