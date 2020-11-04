package frc.robot;

import com.team2568.frc2020.loops.Loop;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LoopTest implements Loop {
	public void onStart() {
		SmartDashboard.putBoolean("Loop started", true);
	}

	public void onLoop() {
		double timestamp = Timer.getFPGATimestamp();
		SmartDashboard.putNumber("Loop execution" timestamp);
	}

	public void onStop() {
		SmartDashboard.putBoolean("Loop started", false);
	}
}
