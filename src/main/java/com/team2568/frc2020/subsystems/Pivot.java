package com.team2568.frc2020.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.team2568.frc2020.Constants;
import com.team2568.frc2020.Registers;
import com.team2568.lib.drivers.SparkMaxFactory;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Pivot extends Subsystem {
    private static Pivot mInstance;

    private double speed;

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
            SparkMaxFactory.setOutput(motor, Constants.kPivotAutoMinSpeed, Constants.kPivotAutoMaxSpeed);
        }

        lowerLimit = new DigitalInput(Constants.kPivotLimit);
    }

    public void setOutputs() {
        switch (Registers.kPivotMode.get()) {
            case kAuto:
                goToRev(Registers.kPivotTargetRev.get());
                break;
            case kTeleop:
                double rawSpeed = applyLimit(Registers.kPivotSpeed.get());

                // Reset encoder and stop if lowerLimit reached
                if (rawSpeed < 0 && !lowerLimit.get()) {
                    callibrate();
                } else {
                    // Determine what speed constant to multiply
                    if (rawSpeed < 0 && getRev() < Constants.kPivotZeroThreshold) {
                        speed = -rawSpeed * Constants.kPivotZeroSpeed;
                    } else {
                        speed = -rawSpeed * Constants.kPivotTeleopSpeed;
                    }

                    if (Registers.kReal.get()) {
                        motor.set(speed);
                    }
                }
                break;
            case kOff:
                if (Registers.kReal.get()) {
                    motor.set(0);
                }
                break;
        }
    }

    public void writeStatus() {
        Registers.kPivotRev.set(getRev());
    }

    private void goToRev(double rev) {
        // Cannot use PID to go to a revolution below the zeroing value or above the max
        // value
        double refrence = rev;

        if (rev < Constants.kPivotZeroThreshold) {
            refrence = Constants.kPivotZeroThreshold;
        } else if (rev > Constants.kPivotHighestThreshold) {
            refrence = Constants.kPivotHighestThreshold;
        }

        if (Registers.kReal.get()) {
            // If revolution requested is less than zeroing value, zeroing will occur
            // referene will be set to the Zero Threshold if the requested rev is lower
            if (atRev(Constants.kPivotZeroThreshold) && rev < Constants.kPivotZeroThreshold) {
                zero();
            } else {
                motor.getPIDController().setReference(-refrence, ControlType.kPosition);
            }
        }
    }

    private void zero() {
        if (Registers.kReal.get()) {
            if (!lowerLimit.get()) {
                callibrate();
            } else {
                motor.set(-Constants.kPivotZeroSpeed);
            }
        }
    }

    private void callibrate() {
        if (Registers.kReal.get()) {
            motor.getEncoder().setPosition(0);
            motor.set(0);
        }
    }

    private boolean atRev(double rev) {
        return Math.abs(rev - getRev()) < Constants.kPivotTargetRevThreshold;
    }

    private double getRev() {
        if (Registers.kReal.get()) {
            return -motor.getEncoder().getPosition();
        } else {
            return 0;
        }

    }

    public void writeDashboard() {
        SmartDashboard.putNumber("PivotRevolution", getRev());
    }

    public void outputTelemetry() {
        SmartDashboard.putBoolean("PivotLimit", lowerLimit.get());
        SmartDashboard.putNumber("PivotSpeed", speed);
    }
}
