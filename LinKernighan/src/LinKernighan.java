/*
    LinKernighan.java
    Author: M00851520
    Created: 10/11/2023
    Updated: 06/12/2023
*/


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Stack;

public class LinKernighan 
{	
    // Class variables
    private ArrayList<City> cities;    //list of all cities involved in the tour
    private double[][] distanceMatrix;	//matrix storing the distances between each pair of cities
    private ArrayList<City> currentTour; //current tour being optimized
    private double currentTourLength;	//total length of current tour
    private int maxOptLevels;			//maximum number of optimization levels
    private int maxLKHAttempts;			//maximum number of Lin-Kernighan Heuristic attempts
    private int currentOptLevel;		//current optimization level being processed in the algorithm
    private int currentLKHAttempt;		//current number of LKH attempts that have been made

    /**
     * Constructor: Initializes the Lin-Kernighan algorithm
     * 
     * @param cities          
     * @param maxOptLevels    
     * @param maxLKHAttempts 
     */
    public LinKernighan(ArrayList<City> cities, int maxOptLevels, int maxLKHAttempts) 
    {
        this.cities = cities;
        this.currentTour = new ArrayList<>(cities);
        this.distanceMatrix = createDistanceMatrix(cities);
        this.currentTourLength = calculateTourLength(currentTour);
        this.maxOptLevels = maxOptLevels;
        this.maxLKHAttempts = maxLKHAttempts;
    }
    
    /**
     * Sets current tour 
     * @param tour  
     */
    public void setCurrentTour(ArrayList<City> tour) 
    {
        this.currentTour = new ArrayList<>(tour);
        this.currentTourLength = calculateTourLength(currentTour);
    }
    
    /**
     * Optimizes current tour using the Lin-Kernighan heuristic
     */
    public void optimizeTour() 
    {
        generateInitialTour(); 
        performLinKernighanOptimization();
    }
    
    /**
     * Executes the main Lin-Kernighan optimization process
     */
    private void performLinKernighanOptimization() 
    {
        boolean improvement = true;
        currentLKHAttempt = 0;

        while (improvement && currentLKHAttempt < maxLKHAttempts) 
        {
            improvement = false;
            for (int i = 0; i < currentTour.size(); i++) 
            {
                currentOptLevel = 0; 
                if (performKOptMoves(i)) 
                {
                    improvement = true;
                    break;
                }
            }
            currentLKHAttempt++;
        }
    }
    
    /**
     * Attempts k-opt moves starting from a specific index in the tour
     *
     * @param startIndex  
     * @return True if improvement was made
     */
    private boolean performKOptMoves(int startIndex) 
    {
        for (int i = 0; i < currentTour.size(); i++) 
        {
            for (int j = i + 2; j < currentTour.size(); j++) 
            {
                if (i == startIndex && j == startIndex + 1) continue; 

                double delta = calculate2OptGain(i, j);
                if (delta < 0) 
                {
                    apply2OptMove(i, j);
                    currentOptLevel++;
                    if (currentOptLevel >= maxOptLevels) return true; 
                    return true;
                }
            }
        }
        return false; 
    }
    
    /**
     * Calculates the gain/loss in distance if a 2-opt move is applied
     *
     * @param i  
     * @param j  
     * @return calculated gain/or loss in distance
     */
    private double calculate2OptGain(int i, int j) 
    {
        City a = currentTour.get(i);
        City b = currentTour.get((i + 1) % currentTour.size());
        City c = currentTour.get(j);
        City d = currentTour.get((j + 1) % currentTour.size());

        double currentDistance = distanceBetween(a, b) + distanceBetween(c, d);
        double newDistance = distanceBetween(a, c) + distanceBetween(b, d);

        return newDistance - currentDistance;
    }
    
    /**
     * Applies a 2-opt move between two points in the tour
     *
     * @param i  start index of the 2-opt move
     * @param j  end index of the 2-opt move
     */
    private void apply2OptMove(int i, int j) 
    {
        int start = i + 1;
        int end = j;

        while (start < end) 
        {
            Collections.swap(currentTour, start, end);
            start++;
            end--;
        }
        currentTourLength = calculateTourLength(currentTour); 
    }
    
    /**
     * Generates the initial tour using Kruskal's algorithm
     */
    private void generateInitialTour() 
    {
        ArrayList<City> normalTour = generateKruskalTour(false);
        ArrayList<City> reverseTour = generateKruskalTour(true);

        double normalTourLength = calculateTourLength(normalTour);
        double reverseTourLength = calculateTourLength(reverseTour);

        this.currentTour = (normalTourLength < reverseTourLength) ? normalTour : reverseTour;
        this.currentTourLength = calculateTourLength(this.currentTour);
    }


    /**
     * Generates a tour based on a Minimum Spanning Tree created by Kruskal's algorithm
     * @param reverse  
     * @return generated tour
     */
    private ArrayList<City> generateKruskalTour(boolean reverse) 
    {
        ArrayList<Edge> edges = createEdges(reverse);
        Collections.sort(edges);  

        UnionFind unionFind = new UnionFind(cities.size());
        ArrayList<Edge> mstEdges = new ArrayList<>();


        for (Edge edge : edges) 
        {
            int city1Index = cities.indexOf(edge.getCity1());
            int city2Index = cities.indexOf(edge.getCity2());
            if (unionFind.find(city1Index) != unionFind.find(city2Index)) 
            {
                unionFind.union(city1Index, city2Index);
                mstEdges.add(edge);
            }
        }

        return constructTourFromMST(mstEdges);
    }	
    
    
    /**
     * Creates a list of edges between all pairs of cities
     *
     * @param reverse  
     * @return list of created edges
     */
    private ArrayList<Edge> createEdges(boolean reverse) 
    {
        ArrayList<Edge> edges = new ArrayList<>();
        double maxDistance = calculateMaxDistance(); 

        for (int i = 0; i < cities.size(); i++) 
        {
            for (int j = i + 1; j < cities.size(); j++) {
                Edge edge = new Edge(cities.get(i), cities.get(j));
                if (reverse) 
                {

                    edge.setWeight(maxDistance - edge.getWeight());
                }
                edges.add(edge);
            }
        }
        return edges;
    }
    
    /**
     * Calculates the maximum distance between any two cities
     * @return  maximum distance found
     */
    private double calculateMaxDistance() 
    {
        double maxDistance = 0.0;
        for (int i = 0; i < cities.size(); i++) 
        {
            for (int j = i + 1; j < cities.size(); j++) 
            {
                double distance = cities.get(i).distanceTo(cities.get(j));
                if (distance > maxDistance) 
                {
                    maxDistance = distance;
                }
            }
        }
        return maxDistance;
    }
    
    /**
     * Constructs a tour from the edges of a Minimum Spanning Tree
     * @param mstEdges  
     * @return constructed tour.
     */
    private ArrayList<City> constructTourFromMST(ArrayList<Edge> mstEdges) 
    {
        HashMap<City, ArrayList<City>> adjacencyList = new HashMap<>();
        for (Edge edge : mstEdges) 
        {
            adjacencyList.computeIfAbsent(edge.getCity1(), k -> new ArrayList<>()).add(edge.getCity2());
            adjacencyList.computeIfAbsent(edge.getCity2(), k -> new ArrayList<>()).add(edge.getCity1());
        }

        ArrayList<City> tour = new ArrayList<>();
        HashSet<City> visited = new HashSet<>();
        Stack<City> stack = new Stack<>();


        int randomIndex = new Random().nextInt(mstEdges.size());
        City startCity = mstEdges.get(randomIndex).getCity1();
        stack.push(startCity);

        while (!stack.isEmpty()) 
        {
            City currentCity = stack.pop();
            if (!visited.contains(currentCity)) 
            {
                visited.add(currentCity);
                tour.add(currentCity);


                for (City neighbor : adjacencyList.get(currentCity)) 
                {
                    if (!visited.contains(neighbor)) 
                    {
                        stack.push(neighbor);
                    }
                }
            }
        }

        return tour;
    }
    
    /**
     * Creates a distance matrix representing the distances between each pair of cities
     * @param cities  list of cities
     * @return distance matrix
     */
    private double[][] createDistanceMatrix(ArrayList<City> cities) 
    {
        double[][] matrix = new double[cities.size()][cities.size()];
        for (int i = 0; i < cities.size(); i++) 
        {
            for (int j = 0; j < cities.size(); j++) 
            {
                matrix[i][j] = cities.get(i).distanceTo(cities.get(j));
            }
        }
        return matrix;
    }
    
    /**
     * Calculates total length of a given tour
     * @param tour  
     * @return total length of tour
     */
    private double calculateTourLength(ArrayList<City> tour) 
    {
        double length = 0.0;
        for (int i = 0, n = tour.size(); i < n; i++) 
        {
            City currentCity = tour.get(i);
            City nextCity = tour.get((i + 1) % n);
            length += distanceBetween(currentCity, nextCity);
        }
        return length;
    }

    /**
     * Calculates distance between two cities using the distance matrix
     * @param city1  
     * @param city2  
     * @return distance between city1 and city2
     */
    private double distanceBetween(City city1, City city2) 
    {
        int index1 = cities.indexOf(city1);
        int index2 = cities.indexOf(city2);
        return distanceMatrix[index1][index2];
    }
    
    
    /**
     * Returns the current optimized tour
     * @return  current optimized tour
     */
    public ArrayList<City> getCurrentTour() 
    {
        return currentTour;
    }
    
    /**
     * Returns length of the current optimized tour
     * @return  length of the current optimized tour
     */
    public double getCurrentTourLength() 
    {
        return currentTourLength;
    }

}
