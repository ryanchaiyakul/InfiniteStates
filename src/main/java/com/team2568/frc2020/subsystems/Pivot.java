package com.team2568.frc2020.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.team2568.frc2020.Constants;
import com.team2568.frc2020.Registers;
import com.team2568.frc2020.states.PivotState;
import com.team2568.lib.drivers.SparkMaxFactory;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Pivot extends Subsystem {
    private static Pivot mInstance;

    private PivotState nState;
    private boolean isAuto;
    private double joystick;
    private double targetRev;

    private CANSparkMax motor;
    private DigitalInput lowerLimit;

    public static Pivot getInstance() {
        if (mInstance == null) {
            mInstance = new Pivot();
        }
        return mInstance;
    }

    private Pivot() {
        if (!Registers.kSimulate.get()) {
            motor = SparkMaxFactory.getDefault(Constants.kPivotMotor);
            SparkMaxFactory.setPIDF(motor, Constants.kPivotkP, Constants.kPivotkI, Constants.kPivotkD,
                    Constants.kPivotkF);
            SparkMaxFactory.setOutput(motor, Constants.kPivotAutoMinSpeed, Constants.kPivotAutoMaxSpeed);
        }

        lowerLimit = new DigitalInput(Constants.kPivotLimit);
    }

    public void compute() {
        nState = Registers.kPivotState.get();

        switch (Registers.kPivotState.get()) {
            case TELEOP:
                isAuto = false;
                joystick = Constants.kOperatorController.getRightDeadzoneY();

                if (joystick == 0) {
                    switch (Constants.kOperatorController.getPOV()) {
                        case 0:
                            // Up
                            nState = PivotState.AGAINST;
                            break;
                        case 90:
                            // Right
                            nState = PivotState.WHEEL;
                            break;
                        case 180:
                            // Down
                            nState = PivotState.TRENCH;
                            break;
                        case 270:
                            // Left
                            nState = PivotState.LINE;
                            break;
                        default:
                            // Nothing is pressed
                            break;
                    }
                }
                break;
            case ZERO:
                isAuto = true;

                if (Constants.kOperatorController.getRightDeadzoneY() != 0) {
                    nState = PivotState.TELEOP;
                }
                break;
            case AGAINST:
                isAuto = true;
                targetRev = 0;

                if (Constants.kOperatorController.getRightDeadzoneY() != 0
                        || Constants.kOperatorController.getPOV() != 0) {
                    nState = PivotState.TELEOP;
                }
                break;
            case WHEEL:
                isAuto = true;
                targetRev = 0;

                if (Constants.kOperatorController.getRightDeadzoneY() != 0
                        || Constants.kOperatorController.getPOV() != 90) {
                    nState = PivotState.TELEOP;
                }
                break;
            case TRENCH:
                isAuto = true;
                targetRev = 0;

                if (Constants.kOperatorController.getRightDeadzoneY() != 0
                        || Constants.kOperatorController.getPOV() != 180) {
                    nState = PivotState.TELEOP;
                }
                break;
            case LINE:
                isAuto = true;
                targetRev = 0;

                if (Constants.kOperatorController.getRightDeadzoneY() != 0
                        || Constants.kOperatorController.getPOV() != 270) {
                    nState = PivotState.TELEOP;
                }
                break;
            case STOP:
                isAuto = false;
                joystick = 0;
                targetRev = 0;
                break;
        }
        Registers.kPivotState.set(nState);
    }

    public void setOutputs() {
        if (!Registers.kSimulate.get()) {
            if (isAuto) {
                // Cannot use PID to go to a revolution below the zeroing value or above the max
                // value
                double refrence = targetRev;
                if (targetRev < Constants.kPivotZeroThreshold) {
                    refrence = Constants.kPivotZeroThreshold;
                } else if (targetRev > Constants.kPivotHighestThreshold) {
                    refrence = Constants.kPivotHighestThreshold;
                }

                // If revolution requested is less than zeroing value, zeroing will occur
                if (atRev(refrence) && targetRev < Constants.kPivotZeroThreshold) {
                    zero();
                } else {
                    motor.getPIDController().setReference(refrence, ControlType.kPosition);
                }
            } else {
                if (joystick < 0 && !lowerLimit.get()) {
                    callibrate();
                } else if (joystick < 0 && getRev() < Constants.kPivotZeroThreshold) {
                    motor.set(-Constants.kPivotZeroSpeed * joystick);
                } else {
                    motor.set(-Constants.kPivotTeleopSpeed * joystick);
                }
            }
        }
    }

    public void zero() {
        if (!lowerLimit.get()) {
            callibrate();
        } else {
            motor.set(-Constants.kPivotZeroSpeed);
        }
    }

    private void callibrate() {
        motor.getEncoder().setPosition(0);
        motor.set(0);
    }

    private boolean atRev(double rev) {
        return Math.abs(rev - getRev()) < Constants.kPivotRevThreshold;
    }

    private double getRev() {
        return motor.getEncoder().getPosition();
    }

    public void writeDashboard() {
        if (!Registers.kSimulate.get()) {
            SmartDashboard.putNumber("PivotRevolution", getRev());
        }
    }

    public void outputTelemetry() {
        SmartDashboard.putBoolean("PivotLimit", lowerLimit.get());
        SmartDashboard.putString("PivotState", nState.toString());
        if (!Registers.kSimulate.get()) {
            SmartDashboard.putNumber("PivotRPM", motor.getEncoder().getVelocity());
        }
    }
}
