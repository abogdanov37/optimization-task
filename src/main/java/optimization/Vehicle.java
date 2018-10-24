package optimization;

public class Vehicle {

    private final int id;
    private final double avgSpeed;
    private final double carrying;
    private final double avgRate;

    public Vehicle(int id, double avgSpeed, double carrying, double avgRate) {
        this.id = id;
        this.avgSpeed = avgSpeed;
        this.carrying = carrying;
        this.avgRate = avgRate;
    }

    public int getId() {
        return id;
    }

    public double getAvgSpeed() {
        return avgSpeed;
    }

    public double getCarrying() {
        return carrying;
    }

    public double getAvgRate() {
        return avgRate;
    }
}
