package optimization;

import org.apache.commons.math3.optim.MaxIter;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.*;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class TransportOptimizationTest {

    private final static int WORK_HOURS = 8;
    private final static int FUEL_COST = 30;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void distributeTransport_5Cars_Success() throws Exception {
        //Arrange
        double[] expectedSolution = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        Vehicle[] vehicles = new Vehicle[] {
                        new Vehicle(1, 60, 5, 15),
                        new Vehicle(2, 80, 10, 17),
                        new Vehicle(3, 90, 20, 16),
                        new Vehicle(4, 1000, 20000, Integer.MAX_VALUE)
                };

        Application[] applications = new Application[]{
                new Application(1, 1080, 17)/*,
                new Application(2, 120, 32),
                new Application(3, 170, 40),
                new Application(4, 150, 8),
                new Application(5, 100, 11)*/
        };

        // 7.45, 0.0,   2.54, 0.0,  0.0,   машина 1
        // 0.0,  10.0,  0.0,  0.0,  0.0,   машина 2
        // 10.0, 0.0,   0.0,  0.0,  0.0    машина 3

        // 0.0,  0.0,   0.0,  0.0,   0.0,
        // 17.0, 0.0,   0.0,  0.0,   0.0,
        // 2.0,  11.25, 9.0,  45.0,  32.72

        // 0.0,  0.0,   0.0,  37.5,  0.0,
        // 0.0,  0.0,   0.0,  50.0,  0.0,
        // 0.0,  0.0,   0.0,  56.25, 0.0

        // 0.0,  0.0,   0.0,  0.0,   0.0,
        // 0.0,  0.0,   0.0,  0.0,   0.0,
        // 0.5,  1.15,  0.85, 0.6,   1.05

        // 0.0,  7.5,   0.0,  0.0,   0.0,
        // 0.0,  10.0,  0.0,  0.0,   0.0,
        // 5.0,  3.48,  0.0,  6.0,   10.5

        // 0.0,  0.0,   0.0,  20.0,  7.27,
        // 0.0,  4.0,   0.0,  0.0,   17.36,
        // 5.0,  7.98,  0.48, 0.0,   0.0

        // 0.0, 0.0, 0.0, 30.0, 0.0, 0.0, 6.562499999999999, 0.0, 0.0, 10.0, 5.0, 1.7187500000000004, 4.999999999999999, 2.5000000000000004, 0.0

        // 0.0, 0.0, 2.0, 0.0, 0.0,
        // 0.0, 0.0, 8.0, 0.0, 0.0,
        // 5.0, 5.0, 0.49999999999999956, 5.0, 5.0

        // 0.0, 0.0, 6.0, 0.0, 0.0, 0.0, 0.0, 8.0, 0.0, 0.0, 9.0, 2.7187499999999987, 2.9999999999999996, 0.0, 0.0, 0.0, 0.0032812500000000003, 0.0, 0.0075, 0.005 -> avgSpeed 100

        // 0.0, 0.0, 6.0, 0.0, 0.0,
        // 0.0, 0.0, 8.0, 0.0, 0.0,
        // 9.0, 2.71, 2.99, 0.0, 0.0,
        // 0.0, 0.0, 0.0, 0.0075, 0.005 -> avgSpeed 1000

        // 10    23     17    12     21

        ProductivityCalculator calculator = (vehicle, application) -> {
            if (vehicle != null) {
                return  vehicle.getCarrying();
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
                return ((vehicle.getAvgRate() * application.getDistance()) / 100) * FUEL_COST;
            }
            return 0;
        };

        TransportOptimization distributor = new TransportOptimization(vehicles, applications, calculator, timeCalculator, costCalculator, WORK_HOURS);

        //Act
        double[] solution = distributor.distribute();

        //Assert
        System.out.println(Arrays.toString(solution));

        //Assert.assertEquals(expectedSolution.length, solution.length);
    }

    @Test
    public void distributeTransport_external_Success() throws Exception {
        //Arrange

        //Act
        LinearObjectiveFunction func = new LinearObjectiveFunction(new double[] {180.19162920000005,406.77090000000004
                ,478.2806112,140.46065040000002,661261000000.0001}//,820.4123398,379.862535,570.9655700000001,495.41274880000003,495.6101664
                //,478.7504232,495.45615999999995,152.78121000000002}//,3517.0348544}//}
                , 0);
        List<LinearConstraint> cnstrnts = new ArrayList<>();

        cnstrnts.add(new LinearConstraint(new double[] { 0.8,5.6,4.8,3.2,10000}//,4.8,1.6,2.4,2.4,4,4,3.2,2.4}//,0}//}
                , Relationship.EQ
                , 40));
        cnstrnts.add(new LinearConstraint(new double[] {1.263025,1.263025,1.263025,1.263025,0.0050521}//,1.263025,1.263025,1.263025
                //,1.263025,1.263025,1.263025,1.263025,1.263025}//,1.263025}//}
                , Relationship.LEQ
                , 9));
        cnstrnts.add(new LinearConstraint(new double[] {1,1,1,1,1}//,1.263025,1.263025,1.263025
                //,1.263025,1.263025,1.263025,1.263025,1.263025}//,1.263025}//,0.0050521}
                , Relationship.GEQ
                , 1));
        //3967568898,23625
        SolutionCallback callback = new SolutionCallback();
        LinearConstraintSet constraints = new LinearConstraintSet(cnstrnts);

        SimplexSolver solver = new SimplexSolver(0.1);
        PointValuePair optSolution = solver.optimize(new MaxIter(1000)
                , func
                , constraints
                , GoalType.MINIMIZE
                , new NonNegativeConstraint(true)
                , PivotSelectionRule.DANTZIG
                , callback);

        //Assert
        System.out.println(Arrays.toString(optSolution.getPoint()));

        System.out.println(Arrays.toString(callback.getSolution().getPoint()));
        System.out.println(callback.isSolutionOptimal());
    }

    @Test
    public void distributeTransport_OjAlgo_Success() throws Exception {
        //Arrange




        //Act
       /* LinearObjectiveFunction func = new LinearObjectiveFunction(new double[] {180.19162920000005,406.77090000000004
                ,478.2806112,140.46065040000002}//,820.4123398,379.862535,570.9655700000001,495.41274880000003,495.6101664
                //,478.7504232,495.45615999999995,152.78121000000002}//,3517.0348544}//,661261000000.0001}
                , 0);
        List<LinearConstraint> cnstrnts = new ArrayList<>();

        cnstrnts.add(new LinearConstraint(new double[] { 0.8,5.6,4.8,3.2}//,4.8,1.6,2.4,2.4,4,4,3.2,2.4}//,0}//,10000}
                , Relationship.EQ
                , 60));
        cnstrnts.add(new LinearConstraint(new double[] {1.263025,1.263025,1.263025,1.263025}//,1.263025,1.263025,1.263025
                //,1.263025,1.263025,1.263025,1.263025,1.263025}//,1.263025}//,0.0050521}
                , Relationship.LEQ
                , 9));
        //3967568898,23625
        SolutionCallback callback = new SolutionCallback();
        LinearConstraintSet constraints = new LinearConstraintSet(cnstrnts);

        SimplexSolver solver = new SimplexSolver(0.1);
        PointValuePair optSolution = solver.optimize(new MaxIter(1000)
                , func
                , constraints
                , GoalType.MAXIMIZE
                , new NonNegativeConstraint(true)
                , PivotSelectionRule.DANTZIG
                , callback);

        //Assert
        System.out.println(Arrays.toString(optSolution.getPoint()));

        System.out.println(Arrays.toString(callback.getSolution().getPoint()));
        System.out.println(callback.isSolutionOptimal());*/
    }
}