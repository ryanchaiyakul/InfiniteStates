package com.team2568.frc2020.loops;

/**
 * Loops are persistent systems that will be executed periodically.
 * They can be disabled, but will exist throughout the robot's lifetime.
 */

public interface Loop {
	public abstract void onLoop();
}