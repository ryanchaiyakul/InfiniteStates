package com.team2568.frc2020;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class LimeLight {
    private static NetworkTable mNT = NetworkTableInstance.getDefault().getTable("limelight");

    public static boolean getTv() {
        return mNT.getEntry("tv").getDouble(0) == 1 ? true : false;
    }

    public static double getTx() {
        return mNT.getEntry("tx").getDouble(0);
    }

    public static double getTy() {
        return mNT.getEntry("tx").getDouble(0);
    }

    public static double getTa() {
        return mNT.getEntry("ta").getDouble(0);
    }

    public static double getTs() {
        return mNT.getEntry("ts").getDouble(0);
    }

    public static double getTl() {
        // add 11 for image capture latency
        return mNT.getEntry("tl").getDouble(0) + 11;
    }

    public static double getTshort() {
        return mNT.getEntry("tshort").getDouble(0);
    }

    public static double getTlong() {
        return mNT.getEntry("tlong").getDouble(0);
    }

    public static double getThor() {
        return mNT.getEntry("thor").getDouble(0);
    }

    public static double getPipeline() {
        return mNT.getEntry("getpipe").getDouble(0);
    }

    public static void setLedPipe() {
        mNT.getEntry("ledMode").setNumber(0);
    }

    public static void setLedOff() {
        mNT.getEntry("ledMode").setNumber(1);
    }

    public static void setLedBlink() {
        mNT.getEntry("ledMode").setNumber(2);
    }

    public static void setLedOn() {
        mNT.getEntry("ledMode").setNumber(3);
    }

    public static void setVision() {
        mNT.getEntry("camMode").setNumber(0);
    }

    public static void setDriver() {
        mNT.getEntry("camMode").setNumber(1);
    }

    public static void setStandard() {
        mNT.getEntry("stream").setNumber(0);
    }

    public static void setPrimary() {
        mNT.getEntry("stream").setNumber(1);
    }

    public static void setSecondary() {
        mNT.getEntry("stream").setNumber(2);
    }

    public static void setPipeline(int pipeline) {
        mNT.getEntry("pipeline").setNumber(pipeline);
    }

    public static void setPipeline(int pipeline, Boolean ledFromPipe) {
        mNT.getEntry("pipeline").setNumber(pipeline);
        if (ledFromPipe) {
            setLedPipe();
        }
    }
}
