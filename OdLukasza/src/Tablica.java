
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Tablica {

	private File textFileOne;
	private File textFileTwo;
	private static ArrayList<ArrayList<Double>> table;
	static Scanner scannerOne;
	static Scanner scannerTwo;

	public Tablica(String pathOne, String pathTwo) {
		textFileOne = new File(pathOne);
		textFileTwo = new File(pathTwo);
		table = new ArrayList<ArrayList<Double>>();

		try {
			scannerOne = new Scanner(textFileOne);
			scannerTwo = new Scanner(textFileTwo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		lineLoader(scannerOne);
		lineLoader(scannerTwo);
		tableZeroFiller();
		System.out.println(toString());
		
		
	}
	
	public String toString() {
		String print = "";
		for (int i=0; i<table.size(); i++) {
			for (int j=0; j<table.get(i).size(); j++) {
				print += table.get(i).get(j) +"\t";
			}
			print+="\n";
		}
		return print;
	}
	
	private void tableZeroFiller() {
		int maxSize = 0;
		
		// gets biggest table size
		for(int i = 0; i<table.size(); i++) {
			if (table.get(i).size() > maxSize) maxSize = table.get(i).size();
		}

		// fills remaining table spaces with 0
		for (int i=0; i<table.size(); i++) {
			while (table.get(i).size() < maxSize) {
				table.get(i).add(0.0);
			}
		}
	}


	private static void lineLoader(Scanner scanner) {

		while (scanner.hasNextLine()) {
			table.add(mainArrayFiller(scanner.nextLine()));			
		}
	}
	
	private static ArrayList<Double> mainArrayFiller(String line) {
		ArrayList<Double> ArrayRow = new ArrayList<>();
		if(line!=null) {
			int index = 0;
			int newIndex;
			while(true) {
				if (line.indexOf(" ", index)!=-1) {
					newIndex = line.indexOf(" ", index);
				} else newIndex = line.length();
				if (index >= newIndex) break;
				ArrayRow.add(Double.parseDouble(line.substring(index, newIndex)));
				index = newIndex+1;
				if (index>line.length()) {
					break;
				}
			}
		}
		return ArrayRow;
	}
}