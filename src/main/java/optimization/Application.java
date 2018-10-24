package optimization;

public class Application {

    private final int id;
    private final double cargoWeight;
    private final double distance;

    public Application(int id, double cargoWeight, double distance) {
        this.id = id;
        this.cargoWeight = cargoWeight;
        this.distance = distance;
    }

    public double getCargoWeight() {
        return cargoWeight;
    }

    public int getId() {
        return id;
    }

    public double getDistance() {
        return distance;
    }
}
