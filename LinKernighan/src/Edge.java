/*
    Edge.class
    Author: M00851520
    Created: 10/11/2023
    Updated: 06/12/2023
*/


/**
 * Represents an edge in a graph, connecting two cities 
 */
public class Edge implements Comparable<Edge> {
    private City city1; 
    private City city2; 
    private double weight;
    
    /**
     * Constructor
     * @param city1 
     * @param city2 
     */
    public Edge(City city1, City city2) {
        this.city1 = city1;
        this.city2 = city2;
        this.weight = city1.distanceTo(city2);
    }

    /**
     * Gets the first city connected by the edge
     * @return first city
     */
    public City getCity1() {
        return city1;
    }

    /**
     * Gets the second city connected by the edge
     * @return second city
     */
    public City getCity2() {
        return city2;
    }

    /**
     * Gets the weight of the edge, which is the distance between the two cities
     * @return weight of egde
     */
    public double getWeight() {
        return weight;
    }
    
    /**
     * Given one city, returns the other city connected by the edge
     * @param city 
     * @return other city connected by edge
     */
    public City getOtherCity(City city) {
        if(city.equals(city1)) {
            return city2;
        } else if(city.equals(city2)) {
            return city1;
        }
        return null;
    }

    /**
     * Sets the weight of the edge 
     * @param weight 
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * Compares this edge with another edge based on their weights.
     * @param other edge
     * @return negative integer, zero, or a positive integer 
     */
    @Override
    public int compareTo(Edge other) {
        return Double.compare(this.weight, other.weight);
    }
}
