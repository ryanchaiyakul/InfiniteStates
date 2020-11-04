package frc.robot;

import com.team2568.frc2020.loops.Loop;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LoopTest implements Loop {
	public void onStart(double timestamp) {
		SmartDashboard.putBoolean("Loop started", true);
	}

	public void onLoop(double timestamp) {
		SmartDashboard.putNumber("Loop execution", timestamp);
	}

	public void onStop(double timestamp) {
		SmartDashboard.putBoolean("Loop started", false);
	}
}
