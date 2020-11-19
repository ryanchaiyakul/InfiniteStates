package com.team2568.frc2020.states;

import com.team2568.frc2020.Constants;

public enum ShooterState {
    OFF(0, true), SPIN(Constants.kShooterRPM, true), SHOOT(Constants.kShooterRPM, false),
    TURN(Constants.kTurnRPM, true), ROTATE(Constants.kTurnRPM, true);

    private final double mRPM;
    private final boolean mLocked;

    private ShooterState(double rpm, boolean locked) {
        this.mRPM = rpm;
        this.mLocked = locked;
    }

    public double getRPM() {
        return mRPM;
    }

    public boolean isLocked() {
        return mLocked;
    }

    public boolean atRPM(double rpm) {
        return Constants.kShooterThreshold < Math.abs(mRPM - rpm);
    }
}
