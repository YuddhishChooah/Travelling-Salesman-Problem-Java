import java.util.ArrayList;
import java.util.Collections;

public class SimulatedAnnealing {

    private static double temperature = 25000;
    private static double coolingRate = 0.01;

    public static Route simulatedAnnealing(Route currentRoute) {
        if (currentRoute.getCitiesInRoute().isEmpty()) {
            throw new IllegalArgumentException("Route contains no cities.");
        }

        Route bestRoute = new Route(new ArrayList<>(currentRoute.getCitiesInRoute()));

        while (temperature > 1) {
            Route newRoute = new Route(new ArrayList<>(currentRoute.getCitiesInRoute()));
            int size = newRoute.getCitiesInRoute().size();

            int cityIndex1 = (int) (Math.random() * size);
            int cityIndex2 = (int) (Math.random() * size);

            Collections.swap(newRoute.getCitiesInRoute(), cityIndex1, cityIndex2);

            double currentEnergy = currentRoute.getTotalDistance();
            double neighbourEnergy = newRoute.getTotalDistance();

            if (acceptanceProbability(currentEnergy, neighbourEnergy, temperature) > Math.random()) {
                currentRoute = new Route(new ArrayList<>(newRoute.getCitiesInRoute()));
            }

            if (currentRoute.getTotalDistance() < bestRoute.getTotalDistance()) {
                bestRoute = new Route(new ArrayList<>(currentRoute.getCitiesInRoute()));
            }

            temperature *= 1 - coolingRate;
        }

        temperature = 25000;

        return bestRoute;
    }

    private static double acceptanceProbability(double currentEnergy, double neighbourEnergy, double temperature) {
        if (neighbourEnergy < currentEnergy) {
            return 1.0;
        }
        return Math.exp((currentEnergy - neighbourEnergy) / temperature);
    }

    public static void setTemperature(double temp) {
        temperature = temp;
    }

    public static void setCoolingRate(double rate) {
        coolingRate = rate;
    }
}
