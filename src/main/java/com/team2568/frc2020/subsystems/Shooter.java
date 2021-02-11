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

/**
 * <p>
 * Two motors that work inverted are accelerated and held at a constant RPM.
 * Once the RPM is stabilized, the lock is openned so that the tube mechanism
 * can push the balls into the fly wheels.
 * </p>
 * 
 * <p>
 * ShooterRPM: kOff, kShoot, kTurn
 * </p>
 * 
 * <p>
 * kShoot will set the motors to a constant RPM
 * </p>
 * 
 * <p>
 * kTurn will set the motors to a constant speed (duty cycle)
 * </p>
 * 
 * <p>
 * ShooterClosed: True, False
 * </p>
 * 
 * @author Ryan Chaiyakul
 */
public class Shooter extends Subsystem {
    private static Shooter mInstance;

    // rMotor is a follower so it will not be set explicitly
    @SuppressWarnings("unused")
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
                    break;
                case kTurn:
                    lMotor.set(Constants.kShooterTurnSpeed);
                    break;
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
