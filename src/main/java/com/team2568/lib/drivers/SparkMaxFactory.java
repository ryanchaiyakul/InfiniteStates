package com.team2568.lib.drivers;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.team2568.frc2020.Constants;

/**
 * Get CANSparkMaxes from this factory without having to do a lot of
 * configuration.
 */
public class SparkMaxFactory {

    public static class Config {
        public int currentLimit = Constants.kSparkCurrentLimit;
        public IdleMode idleMode = IdleMode.kBrake;

        public void setCurrentLimit(int limit) {
            currentLimit = limit;
        }

        public void setIdleMode(IdleMode idleMode) {
            this.idleMode = idleMode;
        }
    }

    public static CANSparkMax getDefault(int port, Config config) {
        CANSparkMax ret = new CANSparkMax(port, MotorType.kBrushless);
        ret.setSmartCurrentLimit(config.currentLimit);
        ret.setSecondaryCurrentLimit(config.currentLimit);
        ret.setIdleMode(config.idleMode);
        return ret;
    }

    public static CANSparkMax getDefault(int port) {
        return getDefault(port, new Config());
    }

    public static CANSparkMax getFollower(CANSparkMax leader, int port) {
        CANSparkMax ret = getDefault(port);
        ret.follow(leader);
        return ret;
    }

    public static CANSparkMax getFollower(CANSparkMax leader, int port, Config config) {
        CANSparkMax ret = getDefault(port, config);
        ret.follow(leader);
        return ret;
    }

    public static CANSparkMax getInvertedFollower(CANSparkMax leader, int port) {
        CANSparkMax ret = getDefault(port);
        ret.follow(leader, true);
        return ret;
    }

    public static CANSparkMax getInvertedFollower(CANSparkMax leader, int port, Config config) {
        CANSparkMax ret = getDefault(port, config);
        ret.follow(leader, true);
        return ret;
    }

    /**
     * Set P constants of PID Controller
     * 
     * @param motor
     * @param kP
     */
    public static void setPIDF(CANSparkMax motor, double kP) {
        setPIDF(motor, kP, 0);
    }

    /**
     * Set PI constants of PID Controller
     * 
     * @param motor
     * @param kP
     * @param kI
     */
    public static void setPIDF(CANSparkMax motor, double kP, double kI) {
        setPIDF(motor, kP, kI, 0);
    }

    /**
     * Set PID constants of PID Controller
     * 
     * @param motor
     * @param kP
     * @param kI
     * @param kD
     */
    public static void setPIDF(CANSparkMax motor, double kP, double kI, double kD) {
        setPIDF(motor, kP, kI, kD, 0);
    }

    /**
     * Set PIDF constants of PID Controller
     * 
     * @param motor
     * @param kP
     * @param kI
     * @param kD
     * @param kF
     */
    public static void setPIDF(CANSparkMax motor, double kP, double kI, double kD, double kF) {
        CANPIDController pidController = motor.getPIDController();
        pidController.setP(kP);
        pidController.setI(kI);
        pidController.setD(kD);
        pidController.setFF(kF);
    }

    /**
     * Set max and min of PID Controller
     * 
     * @param motor
     * @param min
     * @param max
     */
    public static void setOutput(CANSparkMax motor, double min, double max) {
        CANPIDController pidController = motor.getPIDController();
        pidController.setOutputRange(min, max);
    }
}
