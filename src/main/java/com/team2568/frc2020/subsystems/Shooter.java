package com.team2568.frc2020.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.team2568.frc2020.Constants;
import com.team2568.frc2020.Registers;
import com.team2568.lib.drivers.SparkMaxFactory;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter extends Subsystem {
    private static Shooter mInstance;

    private CANSparkMax lMotor, rMotor;
    private DoubleSolenoid mLock;

    private SparkMaxFactory.Config mConfig = new SparkMaxFactory.Config();

    public enum ShooterValue {
        kOff, kShoot, kTurn;
    }

    public static Shooter getInstance() {
        if (mInstance == null) {
            mInstance = new Shooter();
        }
        return mInstance;
    }

    private Shooter() {
        mConfig.setIdleMode(IdleMode.kCoast);

        if (Registers.kReal.get()) {
            lMotor = SparkMaxFactory.getDefault(Constants.kShooterLMotor, mConfig);
            SparkMaxFactory.setPIDF(lMotor, Constants.kShooterkP, Constants.kShooterkI, Constants.kShooterkD,
                    Constants.kShooterkF);

            rMotor = SparkMaxFactory.getInvertedFollower(lMotor, Constants.kShooterRMotor, mConfig);
        }

        mLock = new DoubleSolenoid(Constants.kShooterF, Constants.kShooterR);
    }

    public void setOutputs() {
        if (Registers.kReal.get()) {
            switch (Registers.kShooterValue.get()) {
                case kOff:
                    lMotor.set(0);
                    break;
                case kShoot:
                    lMotor.getPIDController().setReference(Constants.kShooterRPM, ControlType.kVelocity);
                case kTurn:
                    lMotor.set(Constants.kShooterTurnSpeed);
            }
        }

        if (Registers.kShooterClosed.get()) {
            mLock.set(Value.kForward);
        } else {
            mLock.set(Value.kReverse);
        }
    }

    public void writeStatus() {
        Registers.kShooterRPM.set(getRPM());
    }

    private double getRPM() {
        if (Registers.kReal.get()) {
            return lMotor.getEncoder().getVelocity();
        } else {
            return 0;
        }
    }

    public void writeDashboard() {
        SmartDashboard.putNumber("ShooterRPM", getRPM());
    }

    public void outputTelemetry() {
        SmartDashboard.putBoolean("ShooterClosed", Registers.kShooterClosed.get());
        SmartDashboard.putString("ShooterValue", Registers.kShooterValue.get().toString());
    }
}
