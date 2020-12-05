package com.team2568.frc2020.subsystems;

import com.revrobotics.CANSparkMax;
import com.team2568.frc2020.Constants;
import com.team2568.frc2020.Registers;
import com.team2568.frc2020.states.DriveState;
import com.team2568.lib.drivers.SparkMaxFactory;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain extends Subsystem {
    private static DriveTrain mInstance;

    private DriveState nState;

    private double lJoystick, rJoystick;
    private double drivePower;

    private int constL, constR;
    private double driveL, driveR;

    private CANSparkMax laMotor, lbMotor, lcMotor, raMotor, rbMotor, rcMotor;
    private SpeedControllerGroup lGroup, rGroup;

    private DifferentialDrive drive;

    public static DriveTrain getInstance() {
        if (mInstance == null) {
            mInstance = new DriveTrain();
        }
        return mInstance;
    }

    private DriveTrain() {
        if (!Registers.kSimulate.get()) {
            laMotor = SparkMaxFactory.getDefault(Constants.kDriveTrainLAMotor);
            lbMotor = SparkMaxFactory.getDefault(Constants.kDriveTrainLBMotor);
            lcMotor = SparkMaxFactory.getDefault(Constants.kDriveTrainLCMotor);

            lGroup = new SpeedControllerGroup(laMotor, lbMotor, lcMotor);

            raMotor = SparkMaxFactory.getDefault(Constants.kDriveTrainRAMotor);
            rbMotor = SparkMaxFactory.getDefault(Constants.kDriveTrainRBMotor);
            rcMotor = SparkMaxFactory.getDefault(Constants.kDriveTrainRCMotor);

            rGroup = new SpeedControllerGroup(raMotor, rbMotor, rcMotor);

            drive = new DifferentialDrive(lGroup, rGroup);
        }
    }

    public void compute() {
        nState = Registers.kDriveState.get();

        switch (Registers.kDriveState.get()) {
            case STANDARD:
                lJoystick = Constants.kDriveController.getLeftY();
                rJoystick = Constants.kDriveController.getRightY();

                // A will invert the controls
                if (Constants.kDriveController.getAButtonPressed()) {
                    nState = DriveState.INVERTED;
                }

                setDrivePower();
                setDirection();
                break;
            case INVERTED:
                // Has to be flipped both in polarity and sign
                lJoystick = -Constants.kDriveController.getRightY();
                rJoystick = -Constants.kDriveController.getLeftY();

                // A will invert the controls
                if (Constants.kDriveController.getAButtonPressed()) {
                    nState = DriveState.STANDARD;
                }

                setDrivePower();
                setDirection();
                break;
            case STOP:
                lJoystick = 0;
                rJoystick = 0;
                break;
        }

        Registers.kDriveState.set(nState);
    }

    private void setDrivePower() {
        // LB/RB can change the drivePower during the match
        drivePower = Constants.kDriveRegularPower;
        if (Constants.kDriveController.getLeftBumper()) {
            drivePower = Constants.kDriveSlowPower;
        } else if (Constants.kDriveController.getRightBumper()) {
            drivePower = Constants.kDriveTurboPower;
        }
    }

    private void setDirection() {
        // Easily inverted if wired strangely
        constL = 1;
        constR = 1;

        // Use a constant multiplier for +/- direction as the driveExponent could be
        // even and negate the sign
        if (rJoystick < 0) {
            constR *= 1;
        } else if (rJoystick > 0) {
            constR *= -1;
        }

        if (lJoystick < 0) {
            constL *= 1;
        } else if (lJoystick > 0) {
            constL *= -1;
        }
    }

    public void setOutputs() {
        // Stop the robot when joysticks are not touched
        if (lJoystick == 0 && rJoystick == 0) {
            driveL = driveR = 0;
        } else {
            // However driveExponent should be constant (Changeable by SmartDashboard)
            double driveExponent = SmartDashboard.getNumber("Drive Exponent", 1.8);

            // Use an exponential curve to provide fine control at low speeds but with a
            // high maximum speed
            driveL = constL * drivePower * Math.pow(Math.abs(lJoystick), driveExponent);
            driveR = constR * drivePower * Math.pow(Math.abs(rJoystick), driveExponent);
        }

        if (!Registers.kSimulate.get()) {
            // Invert is handled by compute()
            drive.tankDrive(driveL, driveR);
        }
    }

    public void writeDashboard() {

    }

    public void outputTelemetry() {
        SmartDashboard.putString("DriveState", nState.toString());

        SmartDashboard.putNumber("LeftSpeed", driveL);
        SmartDashboard.putNumber("RightSpeed", driveR);
    }
}
