import java.util.ArrayList;
import java.util.Collections;

public class Route {
    
    private ArrayList<City> route = new ArrayList<>();
    private double distance = 0;

    public Route(ArrayList<City> route) {
        this.route = route;
    }
    
    public Route() {
        for (int i = 0; i < CityManager.numberOfCities(); i++) {
            route.add(CityManager.getCity(i));
        }
    }

    /**
     * Generates a random route (permutation of cities)
     */
    public void generateIndividual() {
        Collections.shuffle(this.route);
    }

    /**
     * Gets a city from the route
     * @param routePosition: The position of the city in the route
     * @return City at position routePosition
     */
    public City getCity(int routePosition) {
        return route.get(routePosition);
    }

    /**
     * Sets a city in a particular position in the route
     * @param routePosition: The position to insert the city
     * @param city: The city to be inserted
     */
    public void setCity(int routePosition, City city) {
        route.set(routePosition, city);
        distance = 0;
    }

    /**
     * Returns the cities in this route.
     * @return ArrayList of cities in the route
     */
    public ArrayList<City> getCitiesInRoute() {
        return this.route;
    }

    /**
     * Gets the total distance of the route
     * @return total distance of the route
     */
    public double getTotalDistance() {
        if (distance == 0) {
            double routeDistance = 0;
            for (int i=0; i < routeSize(); i++) {
                City fromCity = getCity(i);
                City destinationCity;
                if (i+1 < routeSize()) {
                    destinationCity = getCity(i+1);
                } else {
                    destinationCity = getCity(0); // return to starting city
                }
                routeDistance += fromCity.distanceTo(destinationCity);
            }
            distance = routeDistance;
        }
        return distance;
    }

    /**
     * Gets the number of cities on our route
     * @return size of the route
     */
    public int routeSize() {
        return route.size();
    }

    @Override
    public String toString() {
        StringBuilder geneString = new StringBuilder("|");
        for (int i = 0; i < routeSize(); i++) {
            geneString.append(getCity(i)).append("|");
        }
        return geneString.toString();
    }
    
    public void print() {
        for (int i = 0; i < getCitiesInRoute().size(); i++) {
            System.out.print(getCitiesInRoute().get(i).getId() + " -> ");
        }
        System.out.println(getCitiesInRoute().get(0).getId()); 
        System.out.println("Total Distance: " + getTotalDistance());
    }
}
