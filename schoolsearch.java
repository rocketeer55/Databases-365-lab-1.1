// Spencer Schurk & Jack Goyette

import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Collections;

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

	// Traceability: implements requirements R1, R2, E1

	public static void main(String[] args) {
		quit = false;
		// Init rows
		ArrayList<Row> students;
		ArrayList<Row> teachers;

		// Read students file
		try {
			students = initStudents();
			System.out.println("File \'list.txt\' successfully loaded");
		}
		catch (Exception e) {
			System.out.println("Error reading file \'list.txt\'");
			return;
		}

		// Read teachers file
		try {
			teachers = initTeachers();
			System.out.println("File \'teachers.txt\' successfully loaded");
		}
		catch (Exception e) {
			System.out.println("Error reading file \'teachers.txt\'");
			return;
		}

		// Merge the two into rows

		mergeStudentsAndTeachers(students, teachers);

		while (!quit) {
			// Ask for input until user quits
			System.out.println("Please enter a search instruction, or enter \'Q\' to quit");
			handleInput();
		}
	}

	// Traceability: implements requirements R13, E1

	private static ArrayList<Row> initStudents() throws IOException, FileNotFoundException, NumberFormatException, Exception {
		ArrayList<Row> students = new ArrayList<Row>();
		BufferedReader bufferedReader;
		String line;

		bufferedReader = new BufferedReader(new FileReader("list.txt"));

		while ((line = bufferedReader.readLine()) != null) {
			String[] strings = line.split(",", 0);
			Row temp = new Row();

			if (strings.length != 6) {
				bufferedReader.close();
				throw new Exception();
			}

			temp.StLastName = strings[0].trim();
			temp.StFirstName = strings[1].trim();
			temp.Grade = Integer.parseInt(strings[2]);
			temp.Classroom = Integer.parseInt(strings[3]);
			temp.Bus = Integer.parseInt(strings[4]);
			temp.GPA = Float.parseFloat(strings[5]);

			students.add(temp);
		}

		bufferedReader.close();

		return students;
	}

	// Traceability: implements requirements R13, E1

	private static ArrayList<Row> initTeachers() throws IOException, FileNotFoundException, NumberFormatException, Exception {
		ArrayList<Row> teachers = new ArrayList<Row>();
		BufferedReader bufferedReader;
		String line;

		bufferedReader = new BufferedReader(new FileReader("teachers.txt"));

		while ((line = bufferedReader.readLine()) != null) {
			String[] strings = line.split(",", 0);
			Row temp = new Row();

			if (strings.length != 3) {
				bufferedReader.close();
				throw new Exception();
			}

			temp.TLastName = strings[0].trim();
			temp.TFirstName = strings[1].trim();
			temp.Classroom = Integer.parseInt(strings[2].trim());

			teachers.add(temp);
		}

		bufferedReader.close();

		return teachers;
	}

	private static void mergeStudentsAndTeachers(ArrayList<Row> students, ArrayList<Row> teachers) {
		for (int i = 0; i < students.size(); i++) {
			// Loop through list of teachers, find matching classroom
			for (int j = 0; j < teachers.size(); j++) {
				if (students.get(i).Classroom == teachers.get(j).Classroom) {
					// Matches! teacher must be student's teacher
					students.get(i).TLastName = teachers.get(j).TLastName;
					students.get(i).TFirstName = teachers.get(j).TFirstName;
					continue;
				}
			}
		}

		rows = students;
	}

	//Traceability: implements requirement R3

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
		else if (strings[0].equals("CS:") || strings[0].equals("ClassroomStudents:")) {
			if (strings.length != 2) {
				// Incorrect input
				return;
			}
			// Run Classroom Students search
			printClassStudent(strings[1]);
		}
		else if (strings[0].equals("CT:") || strings[0].equals("ClassroomTeachers:")) {
			if (strings.length != 2) {
				// Incorrect input
				return;
			}
			// Run Classroom Teachers search
			printClassTeacher(strings[1]);
		}
		else if (strings[0].equals("GT:") || strings[0].equals("GradeTeachers:")) {
			if (strings.length != 2) {
				// Incorrect input
				return;
			}
			// Run Grade Teachers search
			printGradeTeacher(strings[1]);
		}
		else if (strings[0].equals("E") || strings[0].equals("Enrollment")) {
			// Run Enrollment search
			printEnrollment();
		}
	}

	//Traceability: implements requirements R3, R11

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

	//Traceability: implements requirements R3, R10

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

	//Traceability: implements requirements R3, R7, R9

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

	//Traceability: implements requirements R3, R8

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

	//Traceability: implements requirements R3, R6

	private static void printTeacher(String lastname) {
		for (int i = 0; i < rows.size(); i++) {
			if (rows.get(i).TLastName.equals(lastname)) {
				// Provided lastname matches student's teacher's lastname
				System.out.println(rows.get(i).StLastName + ", " + rows.get(i).StFirstName);
			}
		}
	}

	//Traceability: implements requirements R3, R4, R5

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

	//Traceability: implements requirements NR1
	
	private static void printClassStudent(String classString) {
		int number;
		try {
			number = Integer.parseInt(classString);
		}
		catch (Exception e) {
			// Incorrect input
			return;
		}
		
		for (int i = 0; i < rows.size(); i++) {
			if (rows.get(i).Classroom == number) {
				// Provided number matches teacher's classroom
				System.out.println(rows.get(i).StLastName + ", " + rows.get(i).StFirstName);
			}
		}
	
	}

	//Traceability: implements requirements NR2

	private static void printClassTeacher(String classString) {
		int number;
		ArrayList<String> teachers = new ArrayList<String>();
		try {
			number = Integer.parseInt(classString);
		}
		catch (Exception e) {
			// Incorrect input
			return;
		}

		for (int i = 0; i < rows.size(); i++) {
			if (rows.get(i).Classroom == number && !teachers.contains(rows.get(i).TLastName)) {
				teachers.add(rows.get(i).TLastName);
				System.out.println(rows.get(i).TLastName + ", " + rows.get(i).TFirstName);
			}
		}
	}
	
	//Traceability: implements requirements NR3

	private static void printGradeTeacher(String gradeString) {
		int number;
		ArrayList<String> teachList = new ArrayList<String>();
		try {
			number = Integer.parseInt(gradeString);
		}
		catch (Exception e) {
			// Incorrect input
			return;
		}
		
		for (int i = 0; i < rows.size(); i++) {
			if (rows.get(i).Grade == number && !teachList.contains(rows.get(i).TLastName)) {
				// Provided number matches teacher's grade
				teachList.add(rows.get(i).TLastName);
				System.out.println(rows.get(i).TLastName + ", " + rows.get(i).TFirstName);
			}
		}
	}

	//Traceability: implements requirements NR4
	
	private static void printEnrollment() {
		class Classroom implements Comparable<Classroom> {
			int classroomNumber;
			int numberStudents;

			public int compareTo(Classroom other) {
				if (this.classroomNumber < other.classroomNumber) {
					return -1;
				}
				return 1;
			}
		}
		ArrayList<Classroom> enrollment = new ArrayList<Classroom>();

		for (int i = 0; i < rows.size(); i++) {
			//Check if classroom in enrollment
			boolean classroomAlreadyExists = false;
			for (int j = 0; j < enrollment.size(); j++) {
				if (enrollment.get(j).classroomNumber == rows.get(i).Classroom) {
					//Student's classroom already in list. Increment numberStudents
					enrollment.get(j).numberStudents++;
					classroomAlreadyExists = true;
				}
			}
			if (!classroomAlreadyExists) {
				Classroom temp = new Classroom();
				temp.classroomNumber = rows.get(i).Classroom;
				temp.numberStudents = 1;
				enrollment.add(temp);
			}
		}

		Collections.sort(enrollment);

		for (int i = 0; i < enrollment.size(); i++) {
			System.out.println(enrollment.get(i).classroomNumber + ": " + enrollment.get(i).numberStudents);
		}
	}
}
