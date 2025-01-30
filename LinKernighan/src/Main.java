/*
    Main.java
    Author: M00851520
    Created: 10/11/2023
    Updated: 06/12/2023
*/


import java.util.Scanner;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;

/**
 * main class for file handling and optimization
 */
public class Main {
    public static void main(String[] args) {
        try {
            // Lists all text files in the current directory
            List<String> files = listTextFiles();
            if (files.isEmpty()) {
                System.out.println("No data files found in the current directory.");
                return;
            }

            // Displays the list of available text files
            displayFiles(files);

            try (Scanner scanner = new Scanner(System.in)) {
                //user's choice 
                int fileIndex = getUserFileChoice(scanner, files.size());
                if (fileIndex == -1) return;

                // Loads cities 
                ArrayList<City> cities = loadCities(files.get(fileIndex - 1));
                optimizeAndDisplayTour(cities);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Lists text files in current directory
    private static List<String> listTextFiles() throws IOException {
        return Files.walk(Paths.get("."))
                    .filter(Files::isRegularFile)
                    .map(f -> f.getFileName().toString())
                    .filter(f -> f.endsWith(".txt")) 
                    .collect(Collectors.toList());
    }

    // Displays list of text files
    private static void displayFiles(List<String> files) {
        System.out.println("Available data files:");
        for (int i = 0; i < files.size(); i++) {
            System.out.println((i + 1) + ". " + files.get(i));
        }
    }

    //user's choice
    private static int getUserFileChoice(Scanner scanner, int fileSize) {
        System.out.print("Enter the number of the file you want to use: ");
        int fileIndex = scanner.nextInt();
        if (fileIndex < 1 || fileIndex > fileSize) {
            System.out.println("Invalid file number.");
            return -1;
        }
        return fileIndex;
    }

    // Loads cities
    private static ArrayList<City> loadCities(String filename) {
        Map map = new Map(filename);
        return map.getCities();
    }

    // Optimizes tour and displays results
    private static void optimizeAndDisplayTour(ArrayList<City> cities) {
        int defaultMaxOptLevels = 100; 
        int defaultMaxLKHAttempts = 200; 
        long startTime = System.currentTimeMillis();

        LinKernighan lk = new LinKernighan(cities, defaultMaxOptLevels, defaultMaxLKHAttempts);
        lk.optimizeTour();

        long endTime = System.currentTimeMillis();
        displayOptimizedTour(lk, endTime - startTime);
    }

    // Displays tour, distance and time takne
    private static void displayOptimizedTour(LinKernighan lk, double timeTaken) {
        ArrayList<City> optimizedTour = lk.getCurrentTour();
        double totalDistance = lk.getCurrentTourLength();

        System.out.println("Optimized Tour Path:");
        StringBuilder tourPath = new StringBuilder();
        for (City city : optimizedTour) {
            tourPath.append(city.getIndex()); 
            tourPath.append(" -> ");
        }

        if (!optimizedTour.isEmpty()) {
            tourPath.append(optimizedTour.get(0).getIndex());
        }

        System.out.println(tourPath.toString());
        System.out.println("Best Total Distance: " + totalDistance);
        System.out.println("Time Taken: " + timeTaken + " milliseconds");
    }
}
