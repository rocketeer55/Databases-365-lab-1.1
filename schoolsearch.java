import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.io.IOException;
import java.io.FileNotFoundException;

public class schoolsearch {
	private static ArrayList<Row> rows;
	
	private static class Row {
		public String StLastName;
		public String StFirstName;
		public int Grade;
		public int Classroom;
		public int Bus;
		public float GPA;
		public String TLastName;
		public String TFirstName;

		public void Row() {}
	}

	public static void main(String[] args) {
		// Read file
		try {
			if (!initRows()) {
				// initRows failed
				return;
			}
		}
		catch (IOException e) {
			System.out.println("Error reading file");
			return;
		}

		

	}

	private static boolean initRows() throws IOException {
		BufferedReader bufferedReader;
		String line;
		
		try {
			FileReader fileReader = new FileReader("students.txt");
			bufferedReader = new BufferedReader(fileReader);
		}
		catch (FileNotFoundException e) {
			System.out.println("File does not exist in this directory");
			return false;
		}

		while ((line = bufferedReader.readLine()) != null) {
			String[] strings = line.split(",", 0);
			Row temp = new Row();

			if (strings.length != 8) {
				System.out.println("Error with number of entries in row - " + strings.length);
				return false;
			}

			temp.StLastName = strings[0];
			temp.StFirstName = strings[1];
			
			try {
				temp.Grade = Integer.parseInt(strings[2]);
				temp.Classroom = Integer.parseInt(strings[3]);
				temp.Bus = Integer.parseInt(strings[4]);
				temp.GPA = Float.parseFloat(strings[5]);
			}
			catch (NumberFormatException e) {
				System.out.println("Error reading number values from input file");
				System.out.println("Line: " + line);
				return false;
			}

			temp.TLastName = strings[6];
			temp.TFirstName = strings[7];

			rows.add(temp);
		}

		bufferedReader.close();
		return true;
	}
}
