package com.team2568.frc2020.subsystems;

import com.team2568.frc2020.Constants;
import com.team2568.frc2020.states.ShooterState;

import edu.wpi.first.wpilibj.Timer;

public class Shooter extends Subsystem {
    private static Shooter mInstance;
    private ShooterState mState;

    private double mSpinStart;

    public static Shooter getInstance() {
        if (mInstance == null) {
            mInstance = new Shooter();
        }

        return mInstance;
    }

    public Shooter() {
        
    }

    public void onStart() {
        mState = ShooterState.OFF;
        mSpinStart = 0;
    }

    public void evaluateState() {
        switch (mState) {
            case OFF:
                if (Constants.kOperatorController.getRightTrigger()) {
                    mState = ShooterState.SPIN;
                } else if (Constants.kOperatorController.getStartButton()) {
                    mState = ShooterState.TURN;
                } else if (Constants.kOperatorController.getBackButton()) {
                    mState = ShooterState.ROTATE;
                }
                break;
            case SPIN:
                if (Constants.kOperatorController.getRightTrigger()) {
                    if (mSpinStart == 0) {
                        mSpinStart = Timer.getFPGATimestamp();
                    }

                    if (Timer.getFPGATimestamp() - mSpinStart > Constants.kShooterSpinTime) {
                        mState = ShooterState.SHOOT;
                        mSpinStart = 0;
                    }
                } else {
                    mState = ShooterState.OFF;
                    mSpinStart = 0;
                }
                break;
            case SHOOT:
                if (!Constants.kOperatorController.getRightTrigger()) {
                    mState = ShooterState.OFF;
                }
                break;
            case TURN:
                if (!Constants.kOperatorController.getStartButton()) {
                    mState = ShooterState.OFF;
                }
                break;
            case ROTATE:
                if (!Constants.kOperatorController.getBackButton()) {
                    mState = ShooterState.OFF;
                }
                break;
        }
    }

    public void setState() {

    }

    public void onStop() {
        mState = ShooterState.OFF;
    }

    public void writeDashboard() {

    }
}
