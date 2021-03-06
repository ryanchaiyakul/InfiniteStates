package com.team2568.frc2020.fsm.auto;

import com.team2568.frc2020.Constants;
import com.team2568.frc2020.Registers;
import com.team2568.frc2020.fsm.FSM;
import com.team2568.lib.limelight.LimeLight;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpiutil.math.MathUtil;

public class DriveTrain extends FSM {
    private static DriveTrain mInstance;

    private LimeLight mLimeLight;

    private double tx;
    private double driveZ;

    private PIDController mController;

    private double mStart;

    private Trajectory mTrajectory;
    private double driveLV, driveRV;

    private RamseteController mRamController;

    public enum DriveAutoMode {
        kOff, kTarget, kTrajectory;
    }

    public static DriveTrain getInstance() {
        if (mInstance == null) {
            mInstance = new DriveTrain();
        }
        return mInstance;
    }

    private DriveTrain() {
        mLimeLight = new LimeLight("limelight");
        mController = new PIDController(Constants.kDrivekP, Constants.kDrivekI, Constants.kDrivekD);
        mRamController = new RamseteController(Constants.kDrivekB, Constants.kDrivekZeta);
    };

    public void compute() {
        update();

        switch (Registers.kDriveAutoMode.get()) {
        case kOff:
            driveZ = 0;
            driveLV = 0;
            driveRV = 0;

            // Do not reset controller while in operations
            mController.reset();
            mStart = 0;
            break;
        case kTarget:
            driveLV = 0;
            driveRV = 0;
            driveZ = getDriveZ();
            break;
        case kTrajectory:
            mTrajectory = Registers.kDriveAutoTrajectory.get();
            driveZ = 0;

            if (mStart == 0) {
                mStart = Timer.getFPGATimestamp();
                Constants.kDriveHelper.reset(mTrajectory.getInitialPose());
            }

            DifferentialDriveWheelSpeeds wheelSpeeds = getSpeed();
            driveLV = wheelSpeeds.leftMetersPerSecond;
            driveRV = wheelSpeeds.rightMetersPerSecond;
            break;
        }
        Registers.kDriveAutoDriveZ.set(driveZ);
        Registers.kDriveAutoLV.set(driveLV);
        Registers.kDriveAutoRV.set(driveRV);
    }

    private double getDriveZ() {
        return MathUtil.clamp(mController.calculate(tx, 0), -Constants.kDriveMaxRotationSpeed,
                Constants.kDriveMaxRotationSpeed);
    }

    private DifferentialDriveWheelSpeeds getSpeed() {
        if (Timer.getFPGATimestamp() - mStart > mTrajectory.getTotalTimeSeconds()) {
            return new DifferentialDriveWheelSpeeds();
        }

        // System.out.println(Registers.kDrivePose2d.get());
        ChassisSpeeds chassisSpeed = mRamController.calculate(Registers.kDrivePose2d.get(),
                mTrajectory.sample(Timer.getFPGATimestamp() - mStart));
        return Constants.kDriveKinematics.toWheelSpeeds(chassisSpeed);
    }

    public void writeDashboard() {
        SmartDashboard.putString("DriveAutoMode", Registers.kDriveAutoMode.get().toString());
    }

    private void update() {
        mLimeLight.setPipeline(Constants.kUpperPort);

        tx = mLimeLight.getTx();
    }
}
