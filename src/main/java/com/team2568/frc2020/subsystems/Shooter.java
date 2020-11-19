package com.team2568.frc2020.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.team2568.frc2020.Constants;
import com.team2568.frc2020.states.ShooterState;
import com.team2568.lib.drivers.SparkMaxFactory;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter extends Subsystem {
    private static Shooter mInstance;
    private ShooterState mState;

    private double mSpinStart;

    private CANSparkMax lMotor, rMotor;
    private DoubleSolenoid lock;

    public static Shooter getInstance() {
        if (mInstance == null) {
            mInstance = new Shooter();
        }

        return mInstance;
    }

    private void configureSpark(CANSparkMax motor) {
        motor.setIdleMode(IdleMode.kCoast);
    }

    public Shooter() {
        lMotor = SparkMaxFactory.getDefault(Constants.kShooterLMotor);
        SparkMaxFactory.setPIDF(lMotor, Constants.kShooterkP, Constants.kShooterkI, Constants.kShooterkD,
                Constants.kShooterkF);
        configureSpark(lMotor);

        rMotor = SparkMaxFactory.getInvertedFollower(lMotor, Constants.kShooterRMotor);
        configureSpark(rMotor);

        lock = new DoubleSolenoid(Constants.kShooterF, Constants.kShooterR);
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
        setRPM(mState.getRPM());
        setLock(mState.isLocked());
    }

    public void setRPM(double rpm) {
        if (rpm != 0) {
            lMotor.getPIDController().setReference(rpm, ControlType.kVelocity);
        } else {
            lMotor.set(0);
        }
    }

    public void setLock(boolean locked) {
        if (locked) {
            lock.set(Value.kReverse);
        } else {
            lock.set(Value.kForward);
        }
    }

    public void onStop() {
        lMotor.set(0);
        lock.set(Value.kOff);
    }

    public void writeDashboard() {
        SmartDashboard.putNumber("ShooterRPM", lMotor.getEncoder().getVelocity());
        SmartDashboard.putBoolean("AtRPM", mState.atRPM(lMotor.getEncoder().getVelocity()));
        SmartDashboard.putBoolean("isLocked", mState.isLocked());
        SmartDashboard.putString("ShooterState", mState.toString());
    }
}
