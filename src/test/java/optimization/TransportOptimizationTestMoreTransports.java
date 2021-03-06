package optimization;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class TransportOptimizationTestMoreTransports {

    private final static int WORK_HOURS = 8;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void distributeTransport_5Cars_Success() throws Exception {
        //Arrange
        double[] expectedSolution = new double[]{0, 0, 0, 0, 0, 0, 0, 0};

        Vehicle[] vehicles = new Vehicle[]{
                new Vehicle(1, 60, 10, 1),
                new Vehicle(1, 80, 30, 1),
                new Vehicle(1, 40, 5, 1),
                new Vehicle(1, 90, 20, 1)

        };

        Application[] applications = new Application[]{
                new Application(1, 15, 17),
                new Application(2, 50, 100)
        };
        ProductivityCalculator calculator = (vehicle, application) -> {
            if (vehicle != null) {
                double timeForOneLap = (application.getDistance() * 2) / vehicle.getAvgSpeed();
                return vehicle.getCarrying() / timeForOneLap;
            }
            return 0;
        };

        TimeCalculator timeCalculator = (vehicle, application) -> {
            if (vehicle != null) {
                return (application.getDistance() * 2) / vehicle.getAvgSpeed();
            }
            return 0;
        };

        CostCalculator costCalculator = (vehicle, application) -> {
            if (vehicle != null) {
                return ((vehicle.getAvgRate() * application.getDistance()) / 100) * 30;
            }
            return 0;
        };

        TransportOptimization distributor = new TransportOptimization(vehicles, applications, calculator, timeCalculator, costCalculator, WORK_HOURS);

        //Act
        double[] solution = distributor.distribute();

        //Assert
        System.out.println(Arrays.toString(solution));
        Assert.assertEquals(expectedSolution.length, solution.length);
    }
}