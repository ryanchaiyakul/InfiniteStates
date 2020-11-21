package com.team2568.frc2020.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.team2568.frc2020.Constants;
import com.team2568.frc2020.Registers;
import com.team2568.frc2020.registers.UpdateRegister;
import com.team2568.frc2020.states.ShooterState;
import com.team2568.lib.drivers.SparkMaxFactory;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter extends Subsystem {
    private static Shooter mInstance;

    private double mRPM;
    private boolean mLocked;

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

    private Shooter() {
        lMotor = SparkMaxFactory.getDefault(Constants.kShooterLMotor);
        SparkMaxFactory.setPIDF(lMotor, Constants.kShooterkP, Constants.kShooterkI, Constants.kShooterkD,
                Constants.kShooterkF);
        configureSpark(lMotor);

        rMotor = SparkMaxFactory.getInvertedFollower(lMotor, Constants.kShooterRMotor);
        configureSpark(rMotor);

        lock = new DoubleSolenoid(Constants.kShooterF, Constants.kShooterR);
    }

    public UpdateRegister<?> getRegister() {
        return Registers.kShooterState;
    }

    public void onStart() {
        Registers.kShooterState.set(ShooterState.OFF);
        mSpinStart = 0;
    }

    public void evaluateState() {
        switch (Registers.kShooterState.get()) {
            case OFF:
                mRPM = 0;
                mLocked = true;

                if (Constants.kOperatorController.getRightTrigger()) {
                    Registers.kShooterState.set(ShooterState.SPIN);
                    mRPM = Constants.kShooterRPM;
                }
                break;
            case SPIN:
                mRPM = Constants.kShooterRPM;
                mLocked = true;

                if (Constants.kOperatorController.getRightTrigger()) {
                    if (mSpinStart == 0) {
                        mSpinStart = Timer.getFPGATimestamp();
                    }

                    if (Timer.getFPGATimestamp() - mSpinStart > Constants.kShooterSpinTime) {
                        Registers.kShooterState.set(ShooterState.SHOOT);
                        mLocked = false;
                        mSpinStart = 0;
                    }
                } else {
                    Registers.kShooterState.set(ShooterState.OFF);
                    mRPM = 0;
                    mSpinStart = 0;
                }
                break;
            case SHOOT:
                mRPM = Constants.kShooterRPM;
                mLocked = false;

                if (!Constants.kOperatorController.getRightTrigger()) {
                    Registers.kShooterState.set(ShooterState.OFF);
                    mRPM = 0;
                    mLocked = true;
                }
                break;
        }
    }

    public void setState() {
        setRPM();
        setLock();
    }

    /**
     * sets PID refrence if rpm != 0. If rpm == 0, the motors will be disabled
     */
    public void setRPM() {
        if (mRPM != 0) {
            lMotor.getPIDController().setReference(mRPM, ControlType.kVelocity);
        } else {
            lMotor.set(0);
        }
    }

    public void setLock() {
        if (mLocked) {
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
        SmartDashboard.putBoolean("AtRPM", atRPM(lMotor.getEncoder().getVelocity()));
        SmartDashboard.putBoolean("isLocked", mLocked);
        SmartDashboard.putString("ShooterState", Registers.kShooterState.toString());
    }

    public boolean atRPM(double rpm) {
        return Constants.kShooterThreshold < Math.abs(mRPM - rpm);
    }
}
