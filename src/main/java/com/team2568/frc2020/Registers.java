package com.team2568.frc2020;

import com.team2568.frc2020.registers.SetOnceRegister;
import com.team2568.frc2020.registers.StoppableRegister;
import com.team2568.frc2020.registers.UpdateRegister;
import com.team2568.frc2020.states.ClimbState;
import com.team2568.frc2020.states.DriveState;
import com.team2568.frc2020.states.IntakeState;
import com.team2568.frc2020.states.PivotState;
import com.team2568.frc2020.states.ShooterState;
import com.team2568.frc2020.states.TubeState;
import com.team2568.frc2020.subsystems.DriveTrain.DriveMode;
import com.team2568.frc2020.subsystems.Intake.IntakeValue;
import com.team2568.frc2020.subsystems.Pivot.PivotMode;
import com.team2568.frc2020.subsystems.Shooter.ShooterValue;
import com.team2568.frc2020.subsystems.Tube.TubeValue;;

public class Registers {
	// Global Registers

	public static final SetOnceRegister<Boolean> kTelemetry = new SetOnceRegister<Boolean>();
	public static final SetOnceRegister<Boolean> kReal = new SetOnceRegister<Boolean>();

	// Subsystem Teleop State Registers

	public static final StoppableRegister<ShooterState> kShooterState = new StoppableRegister<ShooterState>(
			ShooterState.OFF, ShooterState.STOP);
	public static final StoppableRegister<IntakeState> kIntakeState = new StoppableRegister<IntakeState>(IntakeState.UP,
			IntakeState.STOP);
	public static final StoppableRegister<TubeState> kTubeState = new StoppableRegister<TubeState>(TubeState.OFF,
			TubeState.STOP);
	public static final StoppableRegister<PivotState> kPivotState = new StoppableRegister<PivotState>(PivotState.TELEOP,
			PivotState.STOP);
	public static final StoppableRegister<ClimbState> kClimbState = new StoppableRegister<ClimbState>(ClimbState.OFF,
			ClimbState.STOP);
	public static final StoppableRegister<DriveState> kDriveState = new StoppableRegister<DriveState>(
			DriveState.STANDARD, DriveState.STOP);

	// Shooter Output Registers

	public static final StoppableRegister<ShooterValue> kShooterValue = new StoppableRegister<ShooterValue>(
			ShooterValue.kOff, ShooterValue.kOff);
	public static final StoppableRegister<Boolean> kShooterClosed = new StoppableRegister<Boolean>(true, true);

	// Intake Output Registers

	public static final StoppableRegister<Boolean> kIntakeDown = new StoppableRegister<Boolean>(false, false);
	public static final StoppableRegister<IntakeValue> kIntakeValue = new StoppableRegister<IntakeValue>(
			IntakeValue.kOff, IntakeValue.kOff);

	// Tube Output Registers

	public static final StoppableRegister<TubeValue> kTubeValue = new StoppableRegister<TubeValue>(TubeValue.kOff,
			TubeValue.kOff);

	// Pivot Output Registers

	public static final StoppableRegister<PivotMode> kPivotMode = new StoppableRegister<PivotMode>(PivotMode.kOff,
			PivotMode.kOff);
	public static final StoppableRegister<Double> kPivotTargetRev = new StoppableRegister<Double>(0.0, 0.0);
	public static final StoppableRegister<Double> kPivotSpeed = new StoppableRegister<Double>(0.0, 0.0);

	// Climb Output Registers

	public static final StoppableRegister<Boolean> kClimbExtend = new StoppableRegister<Boolean>(false, false);
	public static final StoppableRegister<Double> kClimbSpeed = new StoppableRegister<Double>(0.0, 0.0);

	// DriveTrain Output Registers

	public static final StoppableRegister<DriveMode> kDriveMode = new StoppableRegister<DriveMode>(DriveMode.kOff,
			DriveMode.kOff);
	public static final StoppableRegister<Double> kDriveL = new StoppableRegister<Double>(0.0, 0.0);
	public static final StoppableRegister<Double> kDriveR = new StoppableRegister<Double>(0.0, 0.0);
	public static final StoppableRegister<Double> kDriveF = new StoppableRegister<Double>(0.0, 0.0);
	public static final StoppableRegister<Double> kDriveZ = new StoppableRegister<Double>(0.0, 0.0);

	// Shooter Status Registers

	public static final UpdateRegister<Double> kShooterRPM = new UpdateRegister<Double>();

	// Pivot Status Registers

	public static final UpdateRegister<Double> kPivotRev = new UpdateRegister<Double>();
}

/**
 * registers = Collections.unmodifiableList(Arrays.asList(new
 * SetOnceRegister<Boolean>(), new SetOnceRegister<Boolean>(), new
 * StoppableRegister<ShooterState>(ShooterState.OFF, ShooterState.STOP), new
 * StoppableRegister<IntakeState>(IntakeState.UP, IntakeState.STOP), new
 * StoppableRegister<TubeState>(TubeState.OFF, TubeState.STOP), new
 * StoppableRegister<PivotState>(PivotState.TELEOP, PivotState.STOP), new
 * StoppableRegister<ClimbState>(ClimbState.OFF, ClimbState.STOP), new
 * StoppableRegister<DriveState>(DriveState.STANDARD, DriveState.STOP), new
 * StoppableRegister<ShooterValue>(ShooterValue.kOff, ShooterValue.kOff), new
 * StoppableRegister<Boolean>(true, true), new StoppableRegister<Boolean>(false,
 * false), new StoppableRegister<IntakeValue>(IntakeValue.kOff,
 * IntakeValue.kOff), new StoppableRegister<TubeValue>(TubeValue.kOff,
 * TubeValue.kOff), new StoppableRegister<PivotMode>(PivotMode.kOff,
 * PivotMode.kOff), new StoppableRegister<Double>(0.0, 0.0), new
 * StoppableRegister<Double>(0.0, 0.0), new StoppableRegister<Boolean>(false,
 * false), new StoppableRegister<Double>(0.0, 0.0), new
 * StoppableRegister<DriveMode>(DriveMode.kOff, DriveMode.kOff), new
 * StoppableRegister<Double>(0.0, 0.0), new StoppableRegister<Double>(0.0, 0.0),
 * new StoppableRegister<Double>(0.0, 0.0), new StoppableRegister<Double>(0.0,
 * 0.0),
 * 
 * ));
 */