package com.team2568.lib.limelight;

public class Distance {
    private double mMountingHeight, mReferenceHeight;
    private double mMountingAngle;

    public Distance(double mountingHeight, double mountingAngle, double refrenceHeight) {
        this.mMountingHeight = mountingHeight;
        this.mReferenceHeight = refrenceHeight;
        this.mMountingAngle = mountingAngle;
    }

    public double getDistance(double ty) {
        return (getHeight()) / Math.tan(getAngle(ty));
    }

    private double getHeight() {
        return mReferenceHeight - mMountingHeight;
    }

    private double getAngle(double ty) {
        return ty + mMountingAngle;
    }
}
