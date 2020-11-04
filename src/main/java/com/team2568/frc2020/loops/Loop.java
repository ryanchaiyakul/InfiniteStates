package com.team2568.frc2020.loops;

/**
 * Loops are persistent systems that will be executed periodically.
 * They can be disabled, but will exist throughout the robot's lifetime.
 * Timestamp of execution is the only provided parameter.
 */

public interface Loop {
	public void onStart(double timestamp);
	public void onLoop(double timestamp);
	public void onStop(double timestamp);
}

