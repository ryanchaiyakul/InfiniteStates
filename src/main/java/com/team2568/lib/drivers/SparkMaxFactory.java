package com.team2568.lib.drivers;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.team2568.frc2020.Constants;

public class SparkMaxFactory {

    public static class Config {
        public int currentLimit = Constants.kSparkCurrentLimit;
        public  IdleMode idleMode = IdleMode.kBrake;
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
    
    public static void setPIDF(CANSparkMax motor, double kP) {
        setPIDF(motor, kP, 0);
    }
    
    public static void setPIDF(CANSparkMax motor, double kP, double kI) {
        setPIDF(motor, kP, kI, 0);
    }

    public static void setPIDF(CANSparkMax motor, double kP, double kI, double kD) {
        setPIDF(motor, kP, kI, kD, 0);
    }

    public static void setPIDF(CANSparkMax motor, double kP, double kI, double kD, double kF) {
        CANPIDController pidController = motor.getPIDController();
        pidController.setP(kP);
        pidController.setI(kI);
        pidController.setD(kD);
        pidController.setFF(kF);
    }

    public static void setOutput(CANSparkMax motor, double min, double max) {
        CANPIDController pidController = motor.getPIDController();
        pidController.setOutputRange(min, max);
    }
}
