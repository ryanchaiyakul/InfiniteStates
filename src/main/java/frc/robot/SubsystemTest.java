package frc.robot;

import com.team2568.frc2020.subsystems.Subsystem;

import wpilib.edu.first.wpilibj.smartdashboard.SmartDashboard;

class SubsystemTest extends Subsystem {
	private SubsystemTest mInstance;

	public SubsystemTest getInstance() {
		if (mInstance == null) {
			mInstance = SubsystemTest();
		}
		return mInstance;
	}

	public void onStart(double timestamp) {
		SmartDashboard.putNumber("onStart", timestamp);
	}

	public void onStop(double timestamp) {
		SmartDashboard.putNumber("onStop", timestamp);
	}

	public void readInputs() {
	}

	public void writeOutputs() {
	}

	public void writeSmartDashboard() {
		SmartDashboard.putNumber("onLoop", mStart);
	}
}