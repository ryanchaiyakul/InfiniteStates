package com.team2568.frc2020.subsystems;

public class Pivot extends Subsystem {
    private static Pivot mInstance;

    public static Pivot getInstance() {
        if (mInstance == null) {
            mInstance = new Pivot();
        }

        return mInstance;
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
