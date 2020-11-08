package com.team2568.frc2020;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class Controller extends XboxController {
	
	public boolean getLeftTrigger() {
		return Constants.kControllerTriggerThreshold < getTriggerAxis(Hand.kLeft);
	}

	public boolean getRightTrigger() {
		return Constants.kControllerTriggerThreshold < getTriggerAxis(Hand.kRight);
	}
}
