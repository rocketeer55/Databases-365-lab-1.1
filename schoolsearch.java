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
			// TODO
		}
		else if (strings[0].equals("A:") || strings[0].equals("Average:")) {
			if (strings.length != 2) {
				// Incorrect input
				return;
			}
			// Run Average search
			// TODO
		}
		else if (strings[0].equals("G:") || strings[0].equals("Grade:")) {
			if (strings.length == 2) {
				// Run Grade search without high / low flag
				// TODO
			}
			else if (strings.length == 3 && (strings[2].equals("H") || strings[2].equals("High"))) {
				// Run Grade search with high flag
				// TODO
			}
			else if (strings.length == 3 && (strings[2].equals("L") || strings[2].equals("Low"))) {
				// Run Grade search with low flag
				// TODO
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
			// TODO
		}
		else if (strings[0].equals("T:") || strings[0].equals("Teacher:")) {
			if (strings.length != 2) {
				// Incorrect input
				return;
			}
			// Run Teacher search
			// TODO
		}
		else if (strings[0].equals("S:") || strings[0].equals("Student:")) {
			if (strings.length == 2) {
				// Run Student search without Bus flag
				// TODO
			}
			else if (strings.length == 3 && (strings[2].equals("B") || strings[2].equals("Bus"))) {
				// Run Student search with Bus flag
				// TODO
			}
			else {
				// Incorrect input
				return;
			}
		}
	}
}
