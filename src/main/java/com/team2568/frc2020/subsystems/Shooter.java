package com.team2568.frc2020.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.team2568.frc2020.Constants;
import com.team2568.frc2020.Registers;
import com.team2568.frc2020.states.ShooterState;
import com.team2568.lib.drivers.SparkMaxFactory;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter extends Subsystem {
    private static Shooter mInstance;

    private ShooterState nState;
    private double mRPM;
    private boolean isLocked;

    private double mStart;

    private CANSparkMax lMotor, rMotor;
    private DoubleSolenoid mLock;

    public static Shooter getInstance() {
        if (mInstance == null) {
            mInstance = new Shooter();
        }
        return mInstance;
    }

    private void configureMotor(CANSparkMax motor) {
        motor.setIdleMode(IdleMode.kCoast);
    }

    private Shooter() {
        lMotor = SparkMaxFactory.getDefault(Constants.kShooterLMotor);
        SparkMaxFactory.setPIDF(lMotor, Constants.kShooterkP, Constants.kShooterkI, Constants.kShooterkD,
                Constants.kShooterkF);

        rMotor = SparkMaxFactory.getInvertedFollower(lMotor, Constants.kShooterRMotor);

        configureMotor(lMotor);
        configureMotor(rMotor);

        mLock = new DoubleSolenoid(Constants.kShooterF, Constants.kShooterR);
    }

    public void compute() {
        nState = Registers.kShooterState.get();

        switch (Registers.kShooterState.get()) {
            case OFF:
                mRPM = 0;
                isLocked = true;

                if (Constants.kOperatorController.getRightTrigger()) {
                    nState = ShooterState.SPIN;
                    mStart = 0;
                }
                break;
            case SHOOT:
                mRPM = Constants.kShooterRPM;
                isLocked = false;

                if (!Constants.kOperatorController.getRightTrigger()) {
                    nState = ShooterState.OFF;
                }
                break;
            case SPIN:
                mRPM = Constants.kShooterRPM;
                isLocked = true;

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
                mRPM = 0;
                isLocked = true;
                break;
        }

        Registers.kShooterState.set(nState);
    }

    public void setOutputs() {
        if (mRPM != 0) {
            lMotor.getPIDController().setReference(mRPM, ControlType.kVelocity);
        } else {
            lMotor.set(0);
        }

        if (isLocked) {
            mLock.set(Value.kForward);
        } else {
            mLock.set(Value.kReverse);
        }
    }

    public void writeDashboard() {

    }

    public void outputTelemetry() {
        SmartDashboard.putString("ShooterState", nState.toString());
        SmartDashboard.putNumber("ShooterRPM", lMotor.getEncoder().getVelocity());
        SmartDashboard.putBoolean("ShooterLocked", isLocked);
    }
}
