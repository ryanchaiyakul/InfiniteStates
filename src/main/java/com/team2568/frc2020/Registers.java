package com.team2568.frc2020;

import com.team2568.frc2020.registers.Register;
import com.team2568.frc2020.registers.StoppableRegister;
import com.team2568.frc2020.states.ShooterState;

public class Registers {
	public static final Register<Boolean> kTelemetry = new Register<Boolean>();

	public static final StoppableRegister<ShooterState> kShooterState = new StoppableRegister<ShooterState>(ShooterState.OFF, ShooterState.STOP);
}
