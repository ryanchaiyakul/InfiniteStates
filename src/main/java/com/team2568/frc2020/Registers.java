package com.team2568.frc2020;

import com.team2568.frc2020.registers.Register;
import com.team2568.frc2020.registers.StoppableRegister;
import com.team2568.frc2020.states.ClimbState;
import com.team2568.frc2020.states.DriveState;
import com.team2568.frc2020.states.IntakeState;
import com.team2568.frc2020.states.ShooterState;
import com.team2568.frc2020.states.TubeState;

public class Registers {
	public static final Register<Boolean> kTelemetry = new Register<Boolean>();
	public static final Register<Boolean> kSimulate = new Register<Boolean>();

	public static final StoppableRegister<ShooterState> kShooterState = new StoppableRegister<ShooterState>(
			ShooterState.OFF, ShooterState.STOP);
	public static final StoppableRegister<IntakeState> kIntakeState = new StoppableRegister<IntakeState>(IntakeState.UP,
			IntakeState.STOP);
	public static final StoppableRegister<TubeState> kTubeState = new StoppableRegister<TubeState>(TubeState.OFF,
			TubeState.STOP);
	public static final StoppableRegister<ClimbState> kClimbState = new StoppableRegister<ClimbState>(ClimbState.OFF,
			ClimbState.STOP);
	public static final StoppableRegister<DriveState> kDriveState = new StoppableRegister<DriveState>(DriveState.STANDARD,
			DriveState.STOP);
}
