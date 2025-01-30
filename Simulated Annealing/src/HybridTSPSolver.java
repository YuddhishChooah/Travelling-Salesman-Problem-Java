import java.util.ArrayList;

public class HybridTSPSolver {

    private final static int POP_SIZE = 1000;

    private final static double INITIAL_TEMP = 25000;
    private final static double COOLING_RATE = 0.01;

    private ArrayList<City> initialCities;

    public HybridTSPSolver(ArrayList<City> cities) {
        this.initialCities = cities;
    }

    public Route solve() {
        Population initialPopulation = new Population(POP_SIZE, initialCities, true);
        Route bestGeneticAnalysisPath = GeneticAnalysis.evolvePopulation(initialPopulation).getFittest();

        SimulatedAnnealing.setTemperature(INITIAL_TEMP);
        SimulatedAnnealing.setCoolingRate(COOLING_RATE);

        return SimulatedAnnealing.simulatedAnnealing(bestGeneticAnalysisPath);
    }


    public static void main(String[] args) {
        DataManager dataManager = new DataManager();
        ArrayList<City> cities = dataManager.loadCitiesFromFile("sample1-22.txt");
        
        HybridTSPSolver solver = new HybridTSPSolver(cities);
        long startTime = System.nanoTime();
        Route bestRoute = solver.solve();
        long endTime = System.nanoTime();

        bestRoute.print(); 
        System.out.println("Execution Time: " + (endTime - startTime)/ 1e6 + " ms");
        System.out.println("Performance Measure: " + (endTime - startTime) * Math.pow(bestRoute.getTotalDistance(), 2));
    }
}
