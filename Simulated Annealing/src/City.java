public class City {
    private int id;
    private double x, y;

    /**
     * Constructor for City.
     * 
     * @param id - Identifier for the city.
     * @param x - X coordinate of the city.
     * @param y - Y coordinate of the city.
     */
    public City(int id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    /**
     * Computes the Euclidean distance between this city and another city.
     * 
     * @param city - The city to compute the distance to.
     * @return - The distance between this city and the provided city.
     */
    public double distanceTo(City city) {
        double deltaX = this.x - city.x;
        double deltaY = this.y - city.y;
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    // Getters
    public int getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "City{" + "id=" + id + ", x=" + x + ", y=" + y + '}';
    }
}
