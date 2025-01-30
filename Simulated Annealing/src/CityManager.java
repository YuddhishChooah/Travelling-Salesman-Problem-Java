import java.util.ArrayList;

public class CityManager {

    private static ArrayList<City> destinationCities = new ArrayList<City>();

    public static void addCity(City city) {
        destinationCities.add(city);
    }

    public static City getCity(int index) {
        return destinationCities.get(index);
    }

    public static int numberOfCities() {
        return destinationCities.size();
    }

    public static ArrayList<City> getAllCities() {
        return destinationCities;
    }
}
