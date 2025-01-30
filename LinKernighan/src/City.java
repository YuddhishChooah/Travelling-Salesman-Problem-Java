/*
    City.class
    Author: M00851520
    Created: 10/11/2023
    Updated: 06/12/2023
*/

import java.util.ArrayList;

/**
 * Represents a city with a unique index and coordinates (x, y)
 */
public class City 
{
    private double x, y; 
    private int index;   
    private ArrayList<City> neighbors; 

    /**
     * Constructor to initialize a city
     * @param index 
     * @param x 
     * @param y 
     */
    public City(int index, double x, double y) 
    {
        this.index = index;
        this.x = x;
        this.y = y;
        this.neighbors = new ArrayList<>();
    }

    // Getters

    /**
     * Gets x coordinate of the city
     * @return x coordinate
     */
    public double getX() 
    {
        return x;
    }

    /**
     * Gets y coordinate of the city
     * @return y coordinate
     */
    public double getY() 
    {
        return y;
    }

    /**
     * Gets index of the city
     * @return index of the city.
     */
    public int getIndex() 
    {
        return index;
    }

    /**
     * Checks if a specified city is a neighbor of this city
     * @param city 
     * @return True 
     */
    public boolean isNeighbor(City city) 
    {
        return neighbors.contains(city);
    }

    /**
     * Retrieves a list of neighboring cities
     * @return new list containing the neighboring cities
     */
    public ArrayList<City> getNeighbors() 
    {
        return new ArrayList<>(neighbors);
    }

    /**
     * Calculates the distance to another city
     * @param city 
     * @return Euclidean distance 
     * @throws IllegalArgumentException 
     */
    public double distanceTo(City city) 
    {
        if (city == null) 
        {
            throw new IllegalArgumentException("City cannot be null");
        }
        double xDistance = x - city.getX();
        double yDistance = y - city.getY();
        return Math.sqrt((xDistance * xDistance) + (yDistance * yDistance));
    }

    /**
     * Calculates the squared distance to another city
     * @param city 
     * @return squared distance
     * @throws IllegalArgumentException
     */
    public double squaredDistanceTo(City city) 
    {
        if (city == null) 
        {
            throw new IllegalArgumentException("City cannot be null");
        }
        double xDistance = x - city.getX();
        double yDistance = y - city.getY();
        return (xDistance * xDistance) + (yDistance * yDistance);
    }

    /**
     * Adds a city to the list of neighbors if it's not already a neighbor
     * @param neighbor 
     */
    public void addNeighbor(City neighbor) 
    {
        if (neighbor != null && !neighbors.contains(neighbor)) 
        {
            neighbors.add(neighbor);
        }
    }

    /**
     * Removes a city from the list of neighbors
     * @param neighbor 
     */
    public void removeNeighbor(City neighbor) 
    {
        neighbors.remove(neighbor);
    }

    /**
     * Retrieves the next city in the neighbor list 
     * @param prevCity 
     * @return next city 
     */
    public City getNextCity(City prevCity) 
    {
        for (City neighbor : neighbors) 
        {
            if (!neighbor.equals(prevCity)) 
            {
                return neighbor;
            }
        }
        return null;
    }

    /**
     * Replaces an old neighbor with a new neighbor in the list of neighboring cities
     * @param oldNeighbour 
     * @param newNeighbour 
     */
    public void replaceNeighbour(City oldNeighbour, City newNeighbour) 
    {
        removeNeighbor(oldNeighbour);
        addNeighbor(newNeighbour);
    }

    /**
     * Provides a string representation of the city with its index and coordinates
     * @return string to represent cities
     */
    @Override
    public String toString() 
    {
        return "City{" +
               "index=" + index +
               ", x=" + x +
               ", y=" + y +
               '}';
    }
}
