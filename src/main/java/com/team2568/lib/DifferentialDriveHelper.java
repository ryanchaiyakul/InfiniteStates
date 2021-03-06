package com.team2568.lib;

import com.revrobotics.CANEncoder;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;

public class DifferentialDriveHelper {
    private CANEncoder lEncoder, rEncoder;
    private ADXRS450_Gyro mGyro;
    private DifferentialDriveOdometry mOdometry;

    private double tickToMeter;

    public DifferentialDriveHelper(double tickToMeter) {
        this.mGyro = new ADXRS450_Gyro();
        mOdometry = new DifferentialDriveOdometry(mGyro.getRotation2d());
    }

    public void registerEncoders(CANEncoder lEncoder, CANEncoder rEncoder) {
        this.lEncoder = lEncoder;
        this.rEncoder = rEncoder;
    }

    /**
     * Encoder values should be encoder ticks
     * 
     * @param lEncoder
     * @param rEncoder
     */
    public void update(Double lEncoder, double rEncoder) {
        mOdometry.update(mGyro.getRotation2d(), encoderToMeter(lEncoder), encoderToMeter(rEncoder));
    }

    public void reset(Pose2d pose) {
        if (lEncoder != null) {
            lEncoder.setPosition(0);
            rEncoder.setPosition(0);
        }
        mOdometry.resetPosition(pose, mGyro.getRotation2d());
    }

    /**
     * 
     * @param lEncoder
     * @param rEncoder
     * @return
     */
    public DifferentialDriveWheelSpeeds getWheelSpeeds(Double lEncoder, Double rEncoder) {
        return new DifferentialDriveWheelSpeeds(encoderToMeter(lEncoder), encoderToMeter(rEncoder)); // Ticks/Second to
                                                                                                     // Meters/Second
    }

    public Pose2d getPose() {
        return mOdometry.getPoseMeters();
    }

    public double encoderToMeter(double encoder) {
        return encoder * tickToMeter;
    }

    public double meterToEncoder(double meter) {
        return meter / tickToMeter;
    }
}