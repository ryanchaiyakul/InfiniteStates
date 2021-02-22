package com.team2568.frc2020.fsm.auto;

import com.team2568.frc2020.Constants;
import com.team2568.frc2020.Registers;
import com.team2568.frc2020.fsm.FSM;
import com.team2568.lib.limelight.LimeLight;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpiutil.math.MathUtil;

public class DriveTrain extends FSM {
    private static DriveTrain mInstance;

    private LimeLight mLimeLight;

    private double tx;
    private double driveZ;

    private PIDController mController;

    public enum DriveAutoMode {
        kOff, kTarget;
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
    };

    public void compute() {
        reset();
        update();

        switch (Registers.kDriveAutoMode.get()) {
            case kOff:
                driveZ = 0;

                // Do not reset controller while in operations
                mController.reset();
            case kTarget:
                driveZ = MathUtil.clamp(mController.calculate(tx, 0), -Constants.kDriveMaxRotationSpeed,
                        Constants.kDriveMaxRotationSpeed);
        }
        Registers.kDriveAutoDriveZ.set(driveZ);
    }

    public void writeDashboard() {
        SmartDashboard.putString("DriveAutoMode", Registers.kDriveAutoMode.get().toString());
    }

    public void update() {
        mLimeLight.setPipeline(Constants.kUpperPort);

        tx = mLimeLight.getTx();
    }

    public void reset() {
        tx = 0;
    }
}
