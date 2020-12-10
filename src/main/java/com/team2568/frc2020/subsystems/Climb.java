package com.team2568.frc2020.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.team2568.frc2020.Constants;
import com.team2568.frc2020.Registers;
import com.team2568.lib.drivers.TalonSRXFactory;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * <p>
 * Two motors that work in unison move the screw mechanism up and down to raise
 * the robot off the group. This screw is stowed in the horizontal position
 * during normal operations and is raised by a pneumatic piston.
 * </p>
 * 
 * <p>
 * ClimbExtend: True/False
 * </p>
 * 
 * <p>
 * ClimbSpeed: [-1, 1]
 * </p>
 * 
 * @author Ryan Chaiyakul
 */
public class Climb extends Subsystem {
    private static Climb mInstance;

    private double speed;

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
        if (Registers.kReal.get()) {
            lMotor = TalonSRXFactory.getDefault(Constants.kClimbLMotor);
            rMotor = TalonSRXFactory.getDefault(Constants.kClimbRMotor);

            mMotors = new SpeedControllerGroup(lMotor, rMotor);
        }

        solenoid = new DoubleSolenoid(Constants.kClimbF, Constants.kClimbR);

        lLimit = new DigitalInput(Constants.kClimbLLimit);
        uLimit = new DigitalInput(Constants.kClimbULimit);
    }

    public void setOutputs() {
        if (Registers.kClimbExtend.get()) {
            solenoid.set(Value.kForward);
        } else {
            solenoid.set(Value.kReverse);
        }

        speed = applyLimit(Registers.kClimbSpeed.get()) * Constants.kClimbSpeed;

        if (Registers.kReal.get()) {
            // Check limits and direction before setting
            if ((!lLimit.get() && speed < 0) || (!uLimit.get() && speed > 0)) {
                mMotors.set(0);
            } else {
                mMotors.set(speed);
            }
        }
    }

    private double getLCurrent() {
        if (Registers.kReal.get()) {
            return lMotor.getStatorCurrent();
        } else {
            return 0;
        }
    }

    private double getRCurrent() {
        if (Registers.kReal.get()) {
            return rMotor.getStatorCurrent();
        } else {
            return 0;
        }
    }

    public void writeStatus() {

    }

    public void writeDashboard() {
        SmartDashboard.putNumber("ClimbLCurrent", getLCurrent());
        SmartDashboard.putNumber("ClimbRCurrent", getRCurrent());
    }

    public void outputTelemetry() {
        SmartDashboard.putBoolean("ClimbExtended", Registers.kClimbExtend.get());
        SmartDashboard.putNumber("ClimbSpeed", speed);
    }
}
