/*
    Map.class
    Author: M00851520
    Created: 10/11/2023
    Updated: 06/12/2023
*/


import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Represents a map consisting of multiple cities
 */
public class Map {
	// List to store the cities
    private ArrayList<City> cities; 

    /**
     * Constructor for Map
     */
    public Map(String filename) {
        this.cities = new ArrayList<>();
        readFile(filename);
    }

    /**
     * Reads city data from a file and adds them to the cities list
     */
    private void readFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) { 
                line = line.trim(); 

                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split("\\s+");

                if (parts.length < 3) {
                    System.out.println("Skipping incomplete line: " + line);
                    continue;
                }

                try {
                    // Parsing  parts and creating a new City
                    int index = Integer.parseInt(parts[0]);
                    double x = Double.parseDouble(parts[1]);
                    double y = Double.parseDouble(parts[2]);
                    
                    // Adding city to list
                    cities.add(new City(index, x, y));
                } catch (NumberFormatException e) {
                    //print an error message and re throw the exception
                    System.out.println("Error parsing line: " + line);
                    throw e;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the list of cities
     * @return ArrayList containing cities 
     */
    public ArrayList<City> getCities() {
        return this.cities;
    }
}
