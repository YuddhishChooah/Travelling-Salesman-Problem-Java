import java.util.ArrayList;

public class Population {

    private Route[] routes;
    private ArrayList<City> initialCities;

    public Population(int populationSize, ArrayList<City> cities, boolean initialize) {
        this.initialCities = cities;
        routes = new Route[populationSize];

        if (initialize) {
            if (initialCities == null || initialCities.isEmpty()) {
                throw new IllegalArgumentException("No cities provided to initialize the population.");
            }
            for (int i = 0; i < populationSize; i++) {
                Route newRoute = new Route(initialCities);
                newRoute.generateIndividual();
                saveRoute(i, newRoute);
            }
        }
    }

    public Population(int populationSize) {
        routes = new Route[populationSize];
        for (int i = 0; i < populationSize; i++) {
            routes[i] = null;
        }
    }

    public void saveRoute(int index, Route route) {
        routes[index] = route;
    }

    public Route getRoute(int index) {
        return routes[index];
    }

    public Route getFittest() {
        Route fittest = routes[0];
        for (int i = 1; i < populationSize(); i++) {
            if (fittest.getTotalDistance() > getRoute(i).getTotalDistance()) {
                fittest = getRoute(i);
            }
        }
        return fittest;
    }

    public int populationSize() {
        return routes.length;
    }

    public Route[] getRoutes() {
        return this.routes;
    }

    public void setRoute(int index, Route route) {
        this.routes[index] = route;
    }

    public ArrayList<City> getInitialCities() {
        return this.initialCities;
    }
    
    public Route getFittestRoute() {
        Route fittest = routes[0];
        for (int i = 1; i < routes.length; i++) {
            if (fittest.getTotalDistance() > routes[i].getTotalDistance()) {
                fittest = routes[i];
            }
        }
        return fittest;
    }

}
