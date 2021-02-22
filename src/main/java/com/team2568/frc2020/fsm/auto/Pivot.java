package com.team2568.frc2020.fsm.auto;

import com.team2568.frc2020.Constants;
import com.team2568.frc2020.Registers;
import com.team2568.frc2020.fsm.FSM;
import com.team2568.lib.limelight.LimeLight;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Outputs revolution to ensure that all three balls enter the upper port.
 * Change the register kPivotAutoMode to kTarget in order to start the
 * calculations.
 * 
 * @author Ryan Chaiyakul
 */
public class Pivot extends FSM {
    private static Pivot mInstance;

    private double targetRev;

    private final LimeLight mLimeLight;

    private double ty;
    private double distance;

    public enum PivotAutoMode {
        kOff, kTarget;
    }

    public enum PivotAlgorithmMode {
        kIterate;
    }

    public static Pivot getInstance() {
        if (mInstance == null) {
            mInstance = new Pivot();
        }
        return mInstance;
    }

    private Pivot() {
        mLimeLight = new LimeLight();
    }

    public void compute() {
        reset();
        update();

        switch (Registers.kPivotAutoMode.get()) {
            case kOff:
                targetRev = 0;
                break;
            case kTarget:
                targetRev = getTargetRev();
                break;
        }

        Registers.kPivotAutoTargetRev.set(targetRev);
    }

    /**
     * Wraps the calls to calculate the required revolution. Converts from theta to
     * revolution.S
     */
    public double getTargetRev() {
        double theta;
        switch (Constants.kPivotAlgorithmMode) {
            case kIterate:
                theta = iterative();
                break;
            default:
                theta = 0;
        }
        return toRev(theta);
    }

    /**
     * An easy way to store the minimum theta without an ArrayList to save memory
     * and computing time
     */
    private class MinValue {
        private Double value;
        private Double error;

        public void setValue(double theta, double error) {
            if (error < this.error || value == null) {
                this.value = theta;
            }
        }

        public double getValue() {
            return value;
        }
    }

    /**
     * Iterate over a variety of different angles in the domain [26, 50]. Returns
     * the theta which is closest to the correct answer.
     * 
     * @return
     */
    private double iterative() {
        MinValue minTheta = new MinValue();
        // Iterate over every 0.1 in the domain [26, 50] degrees
        for (int i = 0; i < 240; i++) {
            // All trigonometry is performed with radians
            double theta = Math.toRadians(0.1 * i + 26);

            // Cache results for trigonometry before solving each side
            double cos = Math.cos(theta);
            double sin = Math.sin(theta);

            double time = (distance + Constants.kPivotToLimeLight - Constants.kTubeLength * cos)
                    / (Constants.kShooterVelocity * cos);

            double leftside = Constants.kUpperPortHeight - (Constants.kTubeLength * sin) - Constants.kPivotHeight;
            double rightside = (Constants.kShooterVelocity * sin * time) + (0.5 * Constants.kG * Math.pow(time, 2));

            double difference = Math.abs(leftside - rightside);

            minTheta.setValue(theta, difference);
        }

        return minTheta.getValue();
    }

    /**
     * Update distance and ty from LimeLight
     */
    public void update() {
        mLimeLight.setPipeline(Constants.kUpperPort);

        ty = mLimeLight.getTy();
        distance = Constants.kForwardDistance.getDistance(ty);
    }

    /**
     * Set local variables to zero
     */
    public void reset() {
        ty = distance = 0;

    }

    public double toRev(double angle) {
        return 1.98 * angle - 51.7;
    }

    public double toAngle(double rev) {
        return 0.506 * rev + 26.2;
    }

    public void writeDashboard() {
        SmartDashboard.putString("PivotAutoMode", Registers.kPivotAutoMode.get().toString());
        SmartDashboard.putNumber("DistanceFromUpperPort", distance);
    }
}
