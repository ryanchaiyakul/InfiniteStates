package com.team2568.frc2020.states;

import com.team2568.frc2020.Constants;

public enum ShooterState {
	kOff(0, false),
	kSpin(Constants.kShooterSpinRPM, false),
	kShoot(Constants.kShooterSpinRPM, true);
	
	private final double mRPM;
	private final boolean mLocked;

	public ShooterState(double rpm, double locked) {
		this.mRPM = rpm;
		this.mLocked = locked;
	}

	public double getRPM() {
		return mRPM;
	}

	public boolean getLocked() {
		return mLocked;
	}
}
