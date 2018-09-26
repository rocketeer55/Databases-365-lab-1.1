// Spencer Schurk & Jack Goyette

import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.io.IOException;
import java.io.FileNotFoundException;

public class schoolsearch {
	private static ArrayList<Row> rows;
	private static boolean quit;
	
	private static class Row {
		public String StLastName;
		public String StFirstName;
		public int Grade;
		public int Classroom;
		public int Bus;
		public float GPA;
		public String TLastName;
		public String TFirstName;
	}

	public static void main(String[] args) {
		quit = false;
		// Init rows
		rows = new ArrayList<Row>();

		// Read file
		try {
			initRows();
			System.out.println("File \'students.txt\' successfully loaded");
		}
		catch (Exception e) {
			System.out.println("Error reading file");
			return;
		}

		while (!quit) {
			// Ask for input until user quits
			System.out.println("Please enter a search instruction, or enter \'Q\' to quit");
			handleInput();
		}

		//
	}

	private static void initRows() throws IOException, FileNotFoundException, NumberFormatException, Exception {
		BufferedReader bufferedReader;
		String line;

		FileReader fileReader = new FileReader("students.txt");
		bufferedReader = new BufferedReader(fileReader);

		while ((line = bufferedReader.readLine()) != null) {
			String[] strings = line.split(",", 0);
			Row temp = new Row();

			if (strings.length != 8) {
				bufferedReader.close();
				throw new Exception();
			}

			temp.StLastName = strings[0];
			temp.StFirstName = strings[1];
			temp.Grade = Integer.parseInt(strings[2]);
			temp.Classroom = Integer.parseInt(strings[3]);
			temp.Bus = Integer.parseInt(strings[4]);
			temp.GPA = Float.parseFloat(strings[5]);
			temp.TLastName = strings[6];
			temp.TFirstName = strings[7];

			rows.add(temp);
		}

		bufferedReader.close();
	}

	private static void handleInput() {
		// Read in the whole user's input line and split it by spaces
		String line = System.console().readLine();
		String[] strings = line.split(" ", 0);

		if (strings[0].equals("Q") || strings[0].equals("Quit")) {
			// Quit the program
			quit = true;
		}
		else if (strings[0].equals("I") || strings[0].equals("Info")) {
			// Print info
			printInfo();
		}
		else if (strings[0].equals("A:") || strings[0].equals("Average:")) {
			if (strings.length != 2) {
				// Incorrect input
				return;
			}
			// Run Average search
			printAverage(strings[1]);
		}
		else if (strings[0].equals("G:") || strings[0].equals("Grade:")) {
			if (strings.length == 2) {
				// Run Grade search without high / low flag
				printGrade(strings[1], 0);
			}
			else if (strings.length == 3 && (strings[2].equals("H") || strings[2].equals("High"))) {
				// Run Grade search with high flag
				printGrade(strings[1], 1);
			}
			else if (strings.length == 3 && (strings[2].equals("L") || strings[2].equals("Low"))) {
				// Run Grade search with low flag
				printGrade(strings[1], 2);
			}
			else {
				// Incorrect input
				return;
			}
		}
		else if (strings[0].equals("B:") || strings[0].equals("Bus:")) {
			if (strings.length != 2) {
				// Incorrect input
				return;
			}
			// Run Bus search
			printBus(strings[1]);
		}
		else if (strings[0].equals("T:") || strings[0].equals("Teacher:")) {
			if (strings.length != 2) {
				// Incorrect input
				return;
			}
			// Run Teacher search
			printTeacher(strings[1]);
		}
		else if (strings[0].equals("S:") || strings[0].equals("Student:")) {
			if (strings.length == 2) {
				// Run Student search without Bus flag
				printStudent(strings[1], false);
			}
			else if (strings.length == 3 && (strings[2].equals("B") || strings[2].equals("Bus"))) {
				// Run Student search with Bus flag
				printStudent(strings[1], true);
			}
			else {
				// Incorrect input
				return;
			}
		}
	}

	private static void printInfo() {
		int[] grades = new int[7];

		for (int i = 0; i < rows.size(); i++) {
			// Gets the grade of the student index i, then adds 1 to the entry for that grade in the grades array
			// This increments the number of students in that grade by 1
			grades[rows.get(i).Grade]++;
		}

		for (int i = 0; i < grades.length; i++) {
			// For each grade, print out number of students in that grade
			System.out.println(i + ": " + grades[i]);
		}
	}

	private static void printAverage(String numberString) {
		int number;
		try {
			number = Integer.parseInt(numberString);
		}
		catch (Exception e) {
			// Incorrect input
			return;
		}

		if (number > 10) {
			return;
		}

		float gpaSum = 0.f;
		int students = 0;

		for (int i = 0; i < rows.size(); i++) {
			if (rows.get(i).Grade == number) {
				gpaSum += rows.get(i).GPA;
				students++;
			}
		}
		if (students == 0) {
			System.out.println(number + ": 0");
		}
		else {
			System.out.println(number + ": " + (gpaSum / (float)students));
		}
	}

	private static void printGrade(String numberString, int type) {
		int number;
		try {
			number = Integer.parseInt(numberString);
		}
		catch (Exception e) {
			// Incorrect input
			return;
		}

		if (type == 0) {
			// No high / low flag
			for (int i = 0; i < rows.size(); i++) {
				if (rows.get(i).Grade == number) {
					// Provided number matches student's grade
					System.out.println(rows.get(i).StLastName + ", " + rows.get(i).StFirstName);
				}
			}
		}
		else if (type == 1) {
			// high flag
			Row temp = new Row();
			for (int i = 0; i < rows.size(); i++) {
				if (rows.get(i).Grade == number) {
					// Provided number matches student's grade
					if (rows.get(i).GPA > temp.GPA) {
						temp = rows.get(i);
					}
				}
			}
			if (temp.StFirstName != null) {
				// If temp isn't blank
				System.out.println(temp.StLastName + ", " + temp.StFirstName + ", " + 
					temp.GPA + ", " + temp.TLastName + ", " + temp.TFirstName + ", " + temp.Bus);
			}
		}
		else {
			// low flag
			Row temp = new Row();
			temp.GPA = Float.MAX_VALUE;
			for (int i = 0; i < rows.size(); i++) {
				if (rows.get(i).Grade == number) {
					// Provided number matches student's grade
					if (rows.get(i).GPA < temp.GPA) {
						temp = rows.get(i);
					}
				}
			}
			if (temp.StFirstName != null) {
				// If temp isn't blank
				System.out.println(temp.StLastName + ", " + temp.StFirstName + ", " + 
					temp.GPA + ", " + temp.TLastName + ", " + temp.TFirstName + ", " + temp.Bus);
			}
		}
	}

	private static void printBus(String numberString) {
		int number;
		try {
			number = Integer.parseInt(numberString);
		}
		catch (Exception e) {
			// Incorrect input
			return;
		}
		
		for (int i = 0; i < rows.size(); i++) {
			if (rows.get(i).Bus == number) {
				// Provided number matches student's bus
				System.out.println(rows.get(i).StLastName + ", " + rows.get(i).StFirstName + ", " + 
					rows.get(i).Grade + ", " + rows.get(i).Classroom);
			}
		}
	}

	private static void printTeacher(String lastname) {
		for (int i = 0; i < rows.size(); i++) {
			if (rows.get(i).TLastName.equals(lastname)) {
				// Provided lastname matches student's teacher's lastname
				System.out.println(rows.get(i).StLastName + ", " + rows.get(i).StFirstName);
			}
		}
	}

	private static void printStudent(String lastname, boolean busFlag) {
		if (busFlag) {
			// bus flag present
			for (int i = 0; i < rows.size(); i++) {
				if (rows.get(i).StLastName.equals(lastname)) {
					// Provided lastname matches student's lastname
					System.out.println(rows.get(i).StLastName + ", " + rows.get(i).StFirstName + ", " + rows.get(i).Bus);
				}
			}
		}
		else {
			// bus flag not present
			for (int i = 0; i < rows.size(); i++) {
				if (rows.get(i).StLastName.equals(lastname)) {
					// Provided lastname matches student's lastname
					System.out.println(rows.get(i).StLastName + ", " + rows.get(i).StFirstName + ", " + 
						rows.get(i).Grade + ", " + rows.get(i).Classroom + ", " + 
						rows.get(i).TLastName + ", " + rows.get(i).TFirstName);
				}
			}
		}
	}
}
