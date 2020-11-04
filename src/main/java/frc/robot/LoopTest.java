package frc.robot;

import com.team2568.frc2020.loops.Loop;

import edu.wpi.first.wpilibj.Timer;

public class LoopTest implements Loop {
	public void onStart() {
		System.out.println("starting my loop");
	}

	public void onLoop() {
		double timestamp = Timer.getFPGATimestamp();
		System.out.println("executing loop at " + timestamp);
	}

	public void onStop() {
		System.out.println("stopping my loop");
	}
}
