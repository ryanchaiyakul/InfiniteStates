package com.team2568.frc2020.subsystems;

import com.revrobotics.CANSparkMax;
import com.team2568.frc2020.Constants;
import com.team2568.frc2020.Registers;
import com.team2568.lib.drivers.SparkMaxFactory;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain extends Subsystem {
    private static DriveTrain mInstance;

    private double driveL, driveR, driveF, driveZ;

    private CANSparkMax laMotor, lbMotor, lcMotor, raMotor, rbMotor, rcMotor;
    private SpeedControllerGroup lGroup, rGroup;

    private DifferentialDrive drive;

    public static enum DriveMode {
        kTank, kArcade, kOff;
    }

    public static DriveTrain getInstance() {
        if (mInstance == null) {
            mInstance = new DriveTrain();
        }
        return mInstance;
    }

    private DriveTrain() {
        if (Registers.kReal.get()) {
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

    public void setOutputs() {
        reset();

        driveL = applyLimit(Registers.kDriveL.get());
        driveR = applyLimit(Registers.kDriveR.get());
        driveF = applyLimit(Registers.kDriveF.get());
        driveZ = applyLimit(Registers.kDriveZ.get());

        if (Registers.kReal.get()) {
            switch (Registers.kDriveMode.get()) {
                case kTank:
                    drive.tankDrive(driveL, driveR);
                    break;
                case kArcade:
                    drive.arcadeDrive(driveF, driveZ);
                    break;
                case kOff:
                    drive.tankDrive(0, 0);
            }
        }
    }

    public void writeStatus() {

    }

    private void reset() {
        driveL = driveR = driveF = driveZ = 0;
    }

    public void writeDashboard() {
    }

    public void outputTelemetry() {
        SmartDashboard.putString("DriveMode", Registers.kDriveMode.get().toString());
        SmartDashboard.putNumber("DriveL", driveL);
        SmartDashboard.putNumber("DriveR", driveR);
        SmartDashboard.putNumber("DriveF", driveF);
        SmartDashboard.putNumber("DriveZ", driveZ);
    }
}
