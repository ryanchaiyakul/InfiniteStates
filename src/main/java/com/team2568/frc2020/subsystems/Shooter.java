package com.team2568.frc2020.subsystems;

import edu.wpi.first.wpilibj.Timer;

import com.team2568.frc2020.Constants;
import com.team2568.frc2020.Controller;
import com.team2568.frc2020.states.ShooterState;

public class Shooter extends Subsystem {
	private Shooter mShooter;
	private ShooterState mDesiredState;

	private Controller operatorController;

	// Period Inputs
	private double mTimestamp;
	
	public Shooter() {
		operatorController = new Controller(Constants.kOperatorPort);
	}

	public static Subsystem getInstance() {
		if (mShooter == null) {
			mShooter = new Shooter();
		}

		return mShooter;
	}

	public void readInputs() {
		mTimestamp = Timer.getFPGATimestamp();
	}

	public void writeOutputs() {
	}
	
	public void onStart() {
		mDesiredState = ShooterState.kOff;
	}

	public void onStop() {
		mDesiredState = ShooterState.kOff;
	}

	public void getState() {
		switch (mDesiredState) {
			case kOff:
				if (operatorController.getRightTrigger) {
					mDesiredState = ShooterState.kSpin;
					mStart = mTimeStamp;
				}
				break;
			case shooterState.kSpin:
				if (operatorController.getRightTrigger) {
					if (mStart - mTimeStamp > Constants.kShooterShootTime) {
						mDesiredState = ShooterState.kSpin;
					}
					// Remains kSpin if condition not satisfied
				} else {
					mDesiredState = ShooterState.kOff;
				}
				break;
			case ShooterState.kShoot:
				if (!operatorController.getRightTrigger) {
					mDesiredState = ShooterState.kOff;
				}
				break;
		}
	}
}
