import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DataManager {

	public ArrayList<City> loadCitiesFromFile(String filename) {
	    ArrayList<City> cities = new ArrayList<>();

	    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
	        String line;
	        while ((line = br.readLine()) != null) {
	            String[] parts = line.split("[\t ]+"); // <-- regex to split based on tabs or spaces
	            if (parts.length != 3) {
	                throw new IllegalArgumentException("Invalid line format: " + line);
	            }
	            int id = Integer.parseInt(parts[0]);
	            double x = Double.parseDouble(parts[1]);
	            double y = Double.parseDouble(parts[2]);
	            cities.add(new City(id, x, y));
	        }
	    } catch (IOException e) {
	        System.err.println("Error reading from the file. Please check the file path and try again.");
	        e.printStackTrace();
	        System.exit(1);
	    }

	    return cities;
	}


}
