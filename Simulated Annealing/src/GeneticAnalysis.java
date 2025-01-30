import java.util.ArrayList;
import java.util.Collections;

public class GeneticAnalysis {

    private static final int TOURNAMENT_SIZE = 5;
    private static final boolean ELITISM = true;
    
    private static int elitismCount = 5;  
    private static double mutationRate = 0.025; 
    private static double crossoverRate = 0.8; 

    public static void setElitismCount(int count) {
        elitismCount = count;
    }

    public static void setMutationRate(double rate) {
        mutationRate = rate;
    }

    public static void setCrossoverRate(double rate) {
        crossoverRate = rate;
    }

    public static Population evolvePopulation(Population pop) {
        Population newPopulation = new Population(pop.getRoutes().length, pop.getInitialCities(), false);
        int elitismOffset = 0;
        if (ELITISM) {
            newPopulation.setRoute(0, pop.getRoute(0));
            elitismOffset = 1;
        }

        for (int i = elitismOffset; i < newPopulation.getRoutes().length; i++) {
            Route parent1 = tournamentSelection(pop);
            Route parent2 = tournamentSelection(pop);
            Route child = crossover(parent1, parent2);
            newPopulation.setRoute(i, child);
        }

        for (int i = elitismOffset; i < newPopulation.getRoutes().length; i++) {
            mutate(newPopulation.getRoute(i));
        }

        return newPopulation;
    }

    private static Route crossover(Route parent1, Route parent2) {
        Route child = new Route(new ArrayList<>(Collections.nCopies(parent1.getCitiesInRoute().size(), null)));

        int startPos = (int) (Math.random() * parent1.getCitiesInRoute().size());
        int endPos = (int) (Math.random() * parent1.getCitiesInRoute().size());

        for (int i = 0; i < child.getCitiesInRoute().size(); i++) {
            if (startPos < endPos && i > startPos && i < endPos) {
                child.getCitiesInRoute().set(i, parent1.getCitiesInRoute().get(i));
            } else if (startPos > endPos) {
                if (!(i < startPos && i > endPos)) {
                    child.getCitiesInRoute().set(i, parent1.getCitiesInRoute().get(i));
                }
            }
        }

        for (City city : parent2.getCitiesInRoute()) {
            if (!child.getCitiesInRoute().contains(city)) {
                for (int j = 0; j < child.getCitiesInRoute().size(); j++) {
                    if (child.getCitiesInRoute().get(j) == null) {
                        child.getCitiesInRoute().set(j, city);
                        break;
                    }
                }
            }
        }
        return child;
    }

    private static void mutate(Route route) {
        for (int routePos1 = 0; routePos1 < route.getCitiesInRoute().size(); routePos1++) {
            if (Math.random() < mutationRate) {
                int routePos2 = (int) (route.getCitiesInRoute().size() * Math.random());

                City city1 = route.getCitiesInRoute().get(routePos1);
                City city2 = route.getCitiesInRoute().get(routePos2);

                route.getCitiesInRoute().set(routePos2, city1);
                route.getCitiesInRoute().set(routePos1, city2);
            }
        }
    }

    private static Route tournamentSelection(Population pop) {
        Population tournament = new Population(TOURNAMENT_SIZE);
        for (int i = 0; i < TOURNAMENT_SIZE; i++) {
            int randomId = (int) (Math.random() * pop.getRoutes().length);
            tournament.setRoute(i, pop.getRoute(randomId));
        }
        return tournament.getFittestRoute(); 
    }
}
