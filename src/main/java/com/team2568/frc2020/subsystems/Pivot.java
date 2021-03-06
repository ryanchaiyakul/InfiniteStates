package com.team2568.frc2020.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.team2568.frc2020.Constants;
import com.team2568.frc2020.Registers;
import com.team2568.lib.drivers.SparkMaxFactory;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * <p>
 * A single motor controls the pivot subsystem. The revolutions of this motor is
 * bound by a lower limit switch and a max revolution count relative to the
 * limit switch.
 * </p>
 * 
 * <p>
 * PivotMode: kAuto, kTeleop, kOff
 * </p>
 * 
 * <p>
 * Will read PivotTargetRev in kAuto and PivotSpeed in kTeleop.
 * </p>
 * 
 * <p>
 * PivotTargetRev: [0, 65]
 * </p>
 * 
 * <p>
 * If a revolution value less than 3 is passed, the pivot will zero.
 * </p>
 * 
 * <p>
 * PivotSpeed: [-1, 1]
 * </p>
 * 
 * @author Ryan Chaiyakul
 */
public class Pivot extends Subsystem {
    private static Pivot mInstance;

    private double speed, refrence;

    private CANSparkMax motor;
    private DigitalInput lowerLimit;

    public static enum PivotMode {
        kAuto, kTeleop, kOff;
    }

    public static Pivot getInstance() {
        if (mInstance == null) {
            mInstance = new Pivot();
        }
        return mInstance;
    }

    private Pivot() {
        if (Registers.kReal.get()) {
            motor = SparkMaxFactory.getDefault(Constants.kPivotMotor);
            SparkMaxFactory.setPIDF(motor, Constants.kPivotkP, Constants.kPivotkI, Constants.kPivotkD,
                    Constants.kPivotkF);
            // Outputs are switched b/c - is up and + is down
            SparkMaxFactory.setOutput(motor, -Constants.kPivotAutoMaxSpeed, -Constants.kPivotAutoMinSpeed);
        }

        lowerLimit = new DigitalInput(Constants.kPivotLimit);
    }

    public void setOutputs() {
        // Reset private variables
        speed = 0;

        switch (Registers.kPivotMode.get()) {
        case kAuto:
            goToRev(Registers.kPivotTargetRev.get());
            break;
        case kTeleop:
            teleopRun();
            break;
        case kOff:
            stop();
            break;
        }
    }

    /**
     * Bounds the passed revolution to the range [3, 65] and sets the PID controller
     * to this value
     * 
     * @param rev
     */
    private void goToRev(double rev) {
        // Cannot use PID to go to a revolution below the zeroing value or above the max
        // value

        if (rev < Constants.kPivotZeroThreshold) {
            refrence = Constants.kPivotZeroThreshold;
        } else if (rev > Constants.kPivotHighestThreshold) {
            refrence = Constants.kPivotHighestThreshold;
        } else {
            refrence = rev;
        }

        if (Registers.kReal.get()) {
            // If revolution requested is less than zeroing value, zeroing will occur
            // referene will be set to the zero threshold if the requested rev is lower
            if (atRev(Constants.kPivotZeroThreshold) && rev < Constants.kPivotZeroThreshold) {
                zero();
            } else {
                motor.getPIDController().setReference(-refrence, ControlType.kPosition);
            }
        }
    }

    /**
     * Gets speed from PivotSpeed and calculates actual speed depending on distance
     * from zero point
     */
    private void teleopRun() {
        double rawSpeed = applyLimit(Registers.kPivotSpeed.get());

        // Reset encoder and stop if lowerLimit reached
        if (rawSpeed < 0 && !lowerLimit.get()) {
            callibrate();
        } else {
            // Determine what speed constant to multiply
            if (rawSpeed < 0 && getRev() < Constants.kPivotZeroThreshold) {
                speed = rawSpeed * Constants.kPivotZeroSpeed;
            } else {
                speed = rawSpeed * Constants.kPivotTeleopSpeed;
            }

            if (Registers.kReal.get()) {
                motor.set(-speed);
            }
        }
    }

    /**
     * Moves at the fastest zeroing speed that is safe until the lowerLimit is hit
     */
    private void zero() {
        if (Registers.kReal.get()) {
            if (!lowerLimit.get()) {
                callibrate();
            } else {
                motor.set(-Constants.kPivotZeroSpeed);
            }
        }
    }

    /**
     * Stops the motor and resets the encoder
     */
    private void callibrate() {
        if (Registers.kReal.get()) {
            motor.getEncoder().setPosition(0);
            stop();
        }
    }

    /**
     * Stops the motor if the robot is not in simulation
     */
    private void stop() {
        if (Registers.kReal.get()) {
            motor.set(0);
        }
    }

    /**
     * Returns whether the current revolution is within a threshold around the
     * passed revolution.
     * 
     * @param rev
     * @return
     */
    private boolean atRev(double rev) {
        return Math.abs(rev - getRev()) < Constants.kPivotTargetRevThreshold;
    }

    /**
     * Gets the current revolution if not in simulation
     * 
     * @return
     */
    private double getRev() {
        if (Registers.kReal.get()) {
            return -motor.getEncoder().getPosition();
        } else {
            return 0;
        }
    }

    public void writeStatus() {
        Registers.kPivotRev.set(getRev());
    }

    public void writeDashboard() {
        if (Registers.kReal.get()) {
            SmartDashboard.putNumber("PivotRevolution", getRev());
        }
    }

    public void outputTelemetry() {
        if (!Registers.kReal.get()) {
            SmartDashboard.putNumber("PivotSpeed", speed);
            SmartDashboard.putNumber("PivotTargetRev", refrence);
        } else {
            SmartDashboard.putBoolean("PivotLimit", lowerLimit.get());
        }
    }
}
