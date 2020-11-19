package com.team2568.frc2020.subsystems;

public class DriveTrain extends Subsystem {
    private static DriveTrain mInstance;


    public static DriveTrain getInstance() {
        if (mInstance == null) {
            mInstance = new DriveTrain();
        }

        return mInstance;
    }

    public DriveTrain() {

    }

    public void onStart() {

    }

    public void onStop() {

    }

    public void evaluateState() {

    }

    public void setState() {

    }

    public void writeDashboard() {

    }
}
