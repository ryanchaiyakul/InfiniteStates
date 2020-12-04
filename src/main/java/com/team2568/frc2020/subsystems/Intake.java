package com.team2568.frc2020.subsystems;

import com.revrobotics.CANSparkMax;
import com.team2568.frc2020.Constants;
import com.team2568.frc2020.Registers;
import com.team2568.frc2020.states.IntakeState;
import com.team2568.lib.drivers.SparkMaxFactory;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake extends Subsystem {
    private static Intake mInstance;

    private IntakeState nState;
    private boolean isDown;
    private IntakeValue isOn;

    private CANSparkMax lMotor, rMotor;
    private DoubleSolenoid solenoid;

    private enum IntakeValue {
        kForward, kOff, kReverse;
    }

    public static Intake getInstance() {
        if (mInstance == null) {
            mInstance = new Intake();
        }
        return mInstance;
    }

    private Intake() {
        if (!Registers.kSimulate.get()) {
            lMotor = SparkMaxFactory.getDefault(Constants.kIntakeLMotor);
            rMotor = SparkMaxFactory.getInvertedFollower(lMotor, Constants.kIntakeRMotor);
        }

        solenoid = new DoubleSolenoid(Constants.kIntakeF, Constants.kIntakeR);
    }

    public void compute() {
        nState = Registers.kIntakeState.get();

        switch (Registers.kIntakeState.get()) {
            case UP:
                isDown = false;
                isOn = IntakeValue.kOff;

                if (Constants.kOperatorController.getAButtonPressed()) {
                    nState = IntakeState.DOWN;
                }
                break;
            case DOWN:
                isDown = true;
                isOn = IntakeValue.kOff;

                if (Constants.kOperatorController.getAButtonPressed()) {
                    nState = IntakeState.UP;
                } else if (Constants.kOperatorController.getLeftTrigger()) {
                    nState = IntakeState.FORWARD;
                } else if (Constants.kOperatorController.getRightBumper()) {
                    nState = IntakeState.REVERSE;
                }
                break;
            case FORWARD:
                isDown = true;
                isOn = IntakeValue.kForward;

                if (!Constants.kOperatorController.getLeftTrigger()) {
                    nState = IntakeState.DOWN;
                }
                break;
            case REVERSE:
                isDown = true;
                isOn = IntakeValue.kReverse;

                if (!Constants.kOperatorController.getRightBumper()) {
                    nState = IntakeState.DOWN;
                }
                break;
            case STOP:
                isOn = IntakeValue.kOff;
                isDown = false;
                break;
        }

        Registers.kIntakeState.set(nState);
    }

    public void setOutputs() {
        if (isDown) {
            solenoid.set(Value.kForward);
        } else {
            solenoid.set(Value.kReverse);
        }

        if (!Registers.kSimulate.get()) {
            switch (isOn) {
                case kOff:
                    lMotor.set(0);
                    break;
                case kForward:
                    lMotor.set(Constants.kIntakeSpeed);
                    break;
                case kReverse:
                    lMotor.set(-Constants.kIntakeSpeed);
                    break;
            }
        }
    }

    public void writeDashboard() {

    }

    public void outputTelemetry() {
        SmartDashboard.putString("IntakeState", nState.toString());
        SmartDashboard.putBoolean("IntakeDown", isDown);
        SmartDashboard.putString("IntakeSpin", isOn.toString());

        if (!Registers.kSimulate.get()) {
            SmartDashboard.putNumber("IntakeRPM", lMotor.getEncoder().getVelocity());
        }
    }
}
