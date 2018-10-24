package optimization;

import org.apache.commons.math3.optim.MaxIter;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.*;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class TransportOptimization {

    private final Vehicle[] vehicles;
    private final Application[] applications;
    private final ProductivityCalculator productivityCalculator;
    private final TimeCalculator timeCalculator;
    private final CostCalculator costCalculator;
    private final int hoursForWork;

    public TransportOptimization(Vehicle[] vehicles, Application[] applications, ProductivityCalculator productivityCalculator, TimeCalculator timeCalculator, CostCalculator costCalculator, int hoursForWork) {
        this.vehicles = vehicles;
        this.applications = applications;
        this.productivityCalculator = productivityCalculator;
        this.timeCalculator = timeCalculator;
        this.costCalculator = costCalculator;
        this.hoursForWork = hoursForWork;
    }

    public Vehicle[] getVehicles() {
        return vehicles;
    }

    public Application[] getApplications() {
        return applications;
    }

    public double[] distribute() {
        LinearObjectiveFunction func = prepareFunction(getVehicles(), getApplications());
        LinearConstraintSet constraints = prepareConstraints(getVehicles(), getApplications());
        SimplexSolver solver = new SimplexSolver();
        PointValuePair optSolution = solver.optimize(new MaxIter(100)
                , func
                , constraints
                , GoalType.MINIMIZE
                , new NonNegativeConstraint(true));
        return optSolution.getPoint();
    }

    private LinearConstraintSet prepareConstraints(Vehicle[] vehicles, Application[] applications) {
        Collection<LinearConstraint> constraints = new ArrayList<>();
        int constraintLength = vehicles.length * applications.length;
//        for (int groupIdx = 0; groupIdx < vehicles.length; groupIdx++) {
//            double[] lapConstraintCoefficients = new double[constraintLength];
//            for (int appIdx = 0; appIdx < applications.length; appIdx++) {
//                int idx = groupIdx * applications.length + appIdx;
//                lapConstraintCoefficients[idx] = 1;
//            }
//            constraints.add(new LinearConstraint(lapConstraintCoefficients
//                    , Relationship.LEQ
//                    , 100));
//        }

        for (int appIdx = 0; appIdx < applications.length; appIdx++) {
            double[] carryingConstraintCoefficients = new double[constraintLength];
            for (int groupIdx = 0; groupIdx < vehicles.length; groupIdx++)  {
                int idx = groupIdx * applications.length + appIdx;
                carryingConstraintCoefficients[idx] = productivityCalculator.getProductivity(vehicles[groupIdx], applications[appIdx]);;
            }
            constraints.add(new LinearConstraint(carryingConstraintCoefficients
                    , Relationship.EQ
                    , applications[appIdx].getCargoWeight()));
        }

        for (int appIdx = 0; appIdx < applications.length; appIdx++) {
            for (int groupIdx = 0; groupIdx < vehicles.length; groupIdx++) {
                double[] timeForLapConstraintCoefficients = new double[constraintLength];
                Arrays.fill(timeForLapConstraintCoefficients, 0.0d);
                int idx = groupIdx * applications.length + appIdx;
                timeForLapConstraintCoefficients[idx] = timeCalculator.getTime(vehicles[groupIdx], applications[appIdx]);
                constraints.add(new LinearConstraint(timeForLapConstraintCoefficients
                        , Relationship.LEQ
                        , hoursForWork));
            }
        }

        return new LinearConstraintSet(constraints);
    }

    private LinearObjectiveFunction prepareFunction(Vehicle[] vehicles, Application[] applications) {
        int functionLength = vehicles.length * applications.length;
        double[] coefficients = new double[functionLength];
        int idx = 0;
        for (int vehicleIdx = 0; vehicleIdx < vehicles.length; vehicleIdx++) {
            for (int appIdx = 0; appIdx < applications.length; appIdx++) {
                coefficients[idx++] = costCalculator.getCost(vehicles[vehicleIdx], applications[appIdx]);
            }
        }

        return new LinearObjectiveFunction(coefficients, 0);
    }

}
