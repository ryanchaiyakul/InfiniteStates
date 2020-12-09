package com.team2568.frc2020.fsm.teleop;

import com.team2568.frc2020.Constants;
import com.team2568.frc2020.Registers;

import com.team2568.frc2020.fsm.FSM;
import com.team2568.frc2020.states.ShooterState;
import com.team2568.frc2020.subsystems.Shooter.ShooterValue;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter extends FSM {
    private static Shooter mInstance;

    private ShooterState nState;

    private ShooterValue rpm;
    private boolean close;
    private double mStart;

    public static Shooter getInstance() {
        if (mInstance == null) {
            mInstance = new Shooter();
        }
        return mInstance;
    }

    private Shooter() {
    };

    public void compute() {
        nState = Registers.kShooterState.get();

        switch (Registers.kShooterState.get()) {
            case OFF:
                rpm = ShooterValue.kOff;
                close = true;

                if (Constants.kOperatorController.getRightTrigger()) {
                    nState = ShooterState.SPIN;
                    mStart = 0;
                }
                break;
            case SHOOT:
                rpm = ShooterValue.kShoot;
                close = false;

                if (!Constants.kOperatorController.getRightTrigger()) {
                    nState = ShooterState.OFF;
                }
                break;
            case SPIN:
                rpm = ShooterValue.kShoot;
                close = true;

                if (Constants.kOperatorController.getRightTrigger()) {
                    if (mStart == 0) {
                        mStart = Timer.getFPGATimestamp();
                    }
                    if (Timer.getFPGATimestamp() - mStart > Constants.kShooterSpinTime) {
                        nState = ShooterState.SHOOT;
                        mStart = 0;
                    }
                } else {
                    nState = ShooterState.OFF;
                    mStart = 0;
                }
                break;
            case STOP:
                rpm = ShooterValue.kOff;
                close = true;
                break;
        }

        Registers.kShooterValue.set(rpm);
        Registers.kShooterClosed.set(close);
        Registers.kShooterState.set(nState);
    }

    public void writeDashboard() {
        SmartDashboard.putString("ShooterState", nState.toString());
    }
}
