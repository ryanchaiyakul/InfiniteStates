package com.team2568.frc2020.fsm.teleop;

import com.team2568.frc2020.Constants;
import com.team2568.frc2020.Registers;
import com.team2568.frc2020.fsm.FSM;
import com.team2568.frc2020.states.DriveState;
import com.team2568.frc2020.subsystems.DriveTrain.DriveMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain extends FSM {
    private static DriveTrain mInstance;

    private DriveState nState;

    private DriveMode nMode;

    private double lJoystick, rJoystick;
    private int constL, constR;
    private double drivePower;

    private double forward, rotation;

    public static DriveTrain getInstance() {
        if (mInstance == null) {
            mInstance = new DriveTrain();
        }
        return mInstance;
    }

    private DriveTrain() {
    };

    public void compute() {
        nState = Registers.kDriveState.get();

        switch (Registers.kDriveState.get()) {
            case STANDARD:
                nMode = DriveMode.kTank;
                lJoystick = Constants.kDriveController.getLeftY();
                rJoystick = Constants.kDriveController.getRightY();
                forward = 0;
                rotation = 0;

                // A will invert the controls
                if (Constants.kDriveController.getAButtonPressed()) {
                    nState = DriveState.INVERTED;
                }
                break;
            case INVERTED:
                nMode = DriveMode.kTank;
                // Has to be flipped both in polarity and sign
                lJoystick = -Constants.kDriveController.getRightY();
                rJoystick = -Constants.kDriveController.getLeftY();
                forward = 0;
                rotation = 0;

                // A will invert the controls
                if (Constants.kDriveController.getAButtonPressed()) {
                    nState = DriveState.STANDARD;
                }
                break;
            case STOP:
                nMode = DriveMode.kOff;
                lJoystick = 0;
                rJoystick = 0;
                forward = 0;
                rotation = 0;
                break;
        }

        setDirection();

        Registers.kDriveL.set(constL * toDriveSpeed(lJoystick));
        Registers.kDriveR.set(constR * toDriveSpeed(rJoystick));
        Registers.kDriveF.set(forward);
        Registers.kDriveZ.set(rotation);

        Registers.kDriveMode.set(nMode);
        Registers.kDriveState.set(nState);
    }

    private double toDriveSpeed(double joystick) {
        // However driveExponent should be constant (Changeable by SmartDashboard)
        double driveExponent = SmartDashboard.getNumber("Drive Exponent", 1.8);

        // Use an exponential curve to provide fine control at low speeds but with a
        // high maximum speed
        return getDrivePower() * Math.pow(Math.abs(joystick), driveExponent);
    }

    private double getDrivePower() {
        // LB/RB can change the drivePower during the match
        drivePower = Constants.kDriveRegularPower;
        if (Constants.kDriveController.getLeftBumper()) {
            drivePower = Constants.kDriveSlowPower;
        } else if (Constants.kDriveController.getRightBumper()) {
            drivePower = Constants.kDriveTurboPower;
        }

        return drivePower;
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

    public void writeDashboard() {
        SmartDashboard.putString("DriveState", nState.toString());
    }
}
