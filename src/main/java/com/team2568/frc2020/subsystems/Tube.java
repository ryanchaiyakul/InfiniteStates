package com.team2568.frc2020.subsystems;

import com.revrobotics.CANSparkMax;
import com.team2568.frc2020.Constants;
import com.team2568.frc2020.Registers;
import com.team2568.frc2020.states.TubeState;
import com.team2568.lib.drivers.SparkMaxFactory;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Tube extends Subsystem {
    private static Tube mInstance;

    private TubeState nState;
    private TubeValue isOn;

    private CANSparkMax lMotor, rMotor;

    private enum TubeValue {
        kOff, kIntake, kShoot, kReverse;
    }

    public static Tube getInstance() {
        if (mInstance == null) {
            mInstance = new Tube();
        }
        return mInstance;
    }

    private Tube() {
        if (!Registers.kSimulate.get()) {
            lMotor = SparkMaxFactory.getDefault(Constants.kTubeLMotor);
            rMotor = SparkMaxFactory.getInvertedFollower(lMotor, Constants.kTubeLMotor);
        }
    }

    public void compute() {
        nState = Registers.kTubeState.get();

        switch (Registers.kTubeState.get()) {
            case OFF:
                isOn = TubeValue.kOff;

                if (Constants.kOperatorController.getLeftBumper()) {
                    nState = TubeState.INTAKE;
                } else if (Constants.kOperatorController.getRightTrigger()
                        || Constants.kOperatorController.getLeftTrigger()) {
                    nState = TubeState.SHOOT;
                } else if (Constants.kOperatorController.getBButton()) {
                    nState = TubeState.REVERSE;
                }
                break;
            case INTAKE:
                isOn = TubeValue.kIntake;

                if (!Constants.kOperatorController.getLeftBumper()) {
                    nState = TubeState.OFF;
                }
                break;
            case SHOOT:
                isOn = TubeValue.kShoot;

                if (!Constants.kOperatorController.getRightTrigger()
                        && !Constants.kOperatorController.getLeftTrigger()) {
                    nState = TubeState.OFF;
                }
                break;
            case REVERSE:
                isOn = TubeValue.kReverse;

                if (!Constants.kOperatorController.getBButton()) {
                    nState = TubeState.OFF;
                }
                break;
            case STOP:
                isOn = TubeValue.kOff;
                break;
        }

        Registers.kTubeState.set(nState);
    }

    public void setOutputs() {
        if (!Registers.kSimulate.get()) {
            switch (isOn) {
                case kOff:
                    lMotor.set(0);
                    break;
                case kIntake:
                    lMotor.set(Constants.kTubeIntakeSpeed);
                    break;
                case kShoot:
                    lMotor.set(Constants.kTubeShootSpeed);
                    break;
                case kReverse:
                    lMotor.set(-Constants.kTubeIntakeSpeed);
                    break;
            }
        }

    }

    public void writeDashboard() {

    }

    public void outputTelemetry() {
        SmartDashboard.putString("TubeState", nState.toString());
        SmartDashboard.putString("TubeSpin", isOn.toString());

        if (!Registers.kSimulate.get()) {
            SmartDashboard.putNumber("TubeRPM", lMotor.getEncoder().getVelocity());
        }
    }
}
