package com.team2568.frc2020.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.team2568.frc2020.Constants;
import com.team2568.frc2020.Registers;
import com.team2568.frc2020.states.ClimbState;
import com.team2568.lib.drivers.TalonSRXFactory;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Climb extends Subsystem {
    private static Climb mInstance;

    private ClimbState nState;
    private boolean isExtended;
    private double joystick;

    private WPI_TalonSRX lMotor, rMotor;
    private SpeedControllerGroup mMotors;
    private DoubleSolenoid solenoid;
    private DigitalInput lLimit, uLimit;

    public static Climb getInstance() {
        if (mInstance == null) {
            mInstance = new Climb();
        }
        return mInstance;
    }

    private Climb() {
        if (Registers.kSimulate.get()) {
            lMotor = TalonSRXFactory.getDefault(Constants.kClimbLMotor);
            rMotor = TalonSRXFactory.getDefault(Constants.kClimbRMotor);

            mMotors = new SpeedControllerGroup(lMotor, rMotor);
        }

        solenoid = new DoubleSolenoid(Constants.kClimbF, Constants.kClimbR);

        lLimit = new DigitalInput(Constants.kClimbLLimit);
        uLimit = new DigitalInput(Constants.kClimbULimit);
    }

    public void compute() {
        nState = Registers.kClimbState.get();

        switch (Registers.kClimbState.get()) {
            case OFF:
                isExtended = false;
                joystick = 0;

                // Screw cannot move until the climber is engaged at least once
                if (Constants.kOperatorController.getYButtonPressed()) {
                    nState = ClimbState.EXTENDED;
                }
                break;
            case EXTENDED:
                isExtended = true;
                joystick = Constants.kOperatorController.getRightY();

                // Y toggles mode
                if (Constants.kOperatorController.getYButtonPressed()) {
                    nState = ClimbState.CLOSED;
                }
                break;
            case CLOSED:
                isExtended = false;
                joystick = Constants.kOperatorController.getRightY();

                // Y toggles mode
                if (Constants.kOperatorController.getYButtonPressed()) {
                    nState = ClimbState.EXTENDED;
                }
                break;
            case STOP:
                // Do not change extended state because it might be climbed
                joystick = 0;
                break;
        }

        Registers.kClimbState.set(nState);
    }

    public void setOutputs() {
        if (isExtended) {
            solenoid.set(Value.kForward);
        } else {
            solenoid.set(Value.kReverse);
        }

        if (!Registers.kSimulate.get()) {
            // Check limits and direction before setting
            if ((!lLimit.get() && joystick < 0) || (!uLimit.get() && joystick > 0)) {
                mMotors.set(0);
            } else {
                mMotors.set(joystick * Constants.kClimbSpeed);
            }
        }
    }

    public void writeDashboard() {

    }

    public void outputTelemetry() {
        SmartDashboard.putString("ClimbState", nState.toString());
        SmartDashboard.putBoolean("ClimbExtended", isExtended);
        SmartDashboard.putBoolean("ClimbLowerLimit", lLimit.get());
        SmartDashboard.putBoolean("ClimbUpperLimit", uLimit.get());

        if (!Registers.kSimulate.get()) {
            SmartDashboard.putNumber("ClimbLCurrent", lMotor.getStatorCurrent());
            SmartDashboard.putNumber("ClimbRCurrent", rMotor.getStatorCurrent());
        }
    }
}
