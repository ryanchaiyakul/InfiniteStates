package com.team2568.frc2020.subsystems;

import com.team2568.frc2020.loops.Loop;

import edu.wpi.first.wpilibj.Timer; 

/**
 * A Subsystem is a loop that controls all operations of a singular robot subsystem (DriveTrain).
 * Only one instance of each class can exist (Only one DriveTrain exists on the robot).
 * In addition to the explicitly listed functions, onStart() and onStop() must be implemented.
 */

public abstract class Subsystem implements Loop {

	public abstract static Subsystem getInstance();
	
	public abstract void readInputs();
	
	public abstract void writeOutputs();

	public abstract void writeDashboard();
	
	public void onLoop() {
		readInputs();
		writeOutputs();
		writeDashboard();
	}
}
