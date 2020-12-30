package com.team2568.frc2020;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.team2568.frc2020.registers.Register;
import com.team2568.frc2020.registers.SetOnceRegister;
import com.team2568.frc2020.registers.StoppableRegister;
import com.team2568.frc2020.registers.StringToValue;
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

/**
 * Declare global registers within this class as final constants. This class may
 * be generated later.
 */
public class Registers {
	// Global Registers

	public static final SetOnceRegister<Boolean> kTelemetry = new SetOnceRegister<Boolean>(StringToValue.kBoolean);
	public static final SetOnceRegister<Boolean> kReal = new SetOnceRegister<Boolean>(StringToValue.kBoolean);

	// Subsystem Teleop State Registers

	public static final StoppableRegister<ShooterState> kShooterState = new StoppableRegister<ShooterState>(
			StringToValue.kShooterState, ShooterState.OFF, ShooterState.STOP);
	public static final StoppableRegister<IntakeState> kIntakeState = new StoppableRegister<IntakeState>(
			StringToValue.kIntakeState, IntakeState.UP, IntakeState.STOP);
	public static final StoppableRegister<TubeState> kTubeState = new StoppableRegister<TubeState>(
			StringToValue.kTubeState, TubeState.OFF, TubeState.STOP);
	public static final StoppableRegister<PivotState> kPivotState = new StoppableRegister<PivotState>(
			StringToValue.kPivotState, PivotState.TELEOP, PivotState.STOP);
	public static final StoppableRegister<ClimbState> kClimbState = new StoppableRegister<ClimbState>(
			StringToValue.kClimbState, ClimbState.OFF, ClimbState.STOP);
	public static final StoppableRegister<DriveState> kDriveState = new StoppableRegister<DriveState>(
			StringToValue.kDriveState, DriveState.STANDARD, DriveState.STOP);

	// Shooter Output Registers

	public static final StoppableRegister<ShooterValue> kShooterValue = new StoppableRegister<ShooterValue>(
			StringToValue.kShooterValue, ShooterValue.kOff, ShooterValue.kOff);
	public static final StoppableRegister<Boolean> kShooterClosed = new StoppableRegister<Boolean>(
			StringToValue.kBoolean, true, true);

	// Intake Output Registers

	public static final StoppableRegister<Boolean> kIntakeDown = new StoppableRegister<Boolean>(StringToValue.kBoolean,
			false, false);
	public static final StoppableRegister<IntakeValue> kIntakeValue = new StoppableRegister<IntakeValue>(
			StringToValue.kIntakeValue, IntakeValue.kOff, IntakeValue.kOff);

	// Tube Output Registers

	public static final StoppableRegister<TubeValue> kTubeValue = new StoppableRegister<TubeValue>(
			StringToValue.kTubeValue, TubeValue.kOff, TubeValue.kOff);

	// Pivot Output Registers

	public static final StoppableRegister<PivotMode> kPivotMode = new StoppableRegister<PivotMode>(
			StringToValue.kPivotMode, PivotMode.kOff, PivotMode.kOff);
	public static final StoppableRegister<Double> kPivotTargetRev = new StoppableRegister<Double>(StringToValue.kDouble,
			0.0, 0.0);
	public static final StoppableRegister<Double> kPivotSpeed = new StoppableRegister<Double>(StringToValue.kDouble,
			0.0, 0.0);

	// Climb Output Registers

	public static final StoppableRegister<Boolean> kClimbExtend = new StoppableRegister<Boolean>(StringToValue.kBoolean,
			false, false);
	public static final StoppableRegister<Double> kClimbSpeed = new StoppableRegister<Double>(StringToValue.kDouble,
			0.0, 0.0);

	// DriveTrain Output Registers

	public static final StoppableRegister<DriveMode> kDriveMode = new StoppableRegister<DriveMode>(
			StringToValue.kDriveMode, DriveMode.kOff, DriveMode.kOff);
	public static final StoppableRegister<Double> kDriveL = new StoppableRegister<Double>(StringToValue.kDouble, 0.0,
			0.0);
	public static final StoppableRegister<Double> kDriveR = new StoppableRegister<Double>(StringToValue.kDouble, 0.0,
			0.0);
	public static final StoppableRegister<Double> kDriveF = new StoppableRegister<Double>(StringToValue.kDouble, 0.0,
			0.0);
	public static final StoppableRegister<Double> kDriveZ = new StoppableRegister<Double>(StringToValue.kDouble, 0.0,
			0.0);

	// Shooter Status Registers

	public static final UpdateRegister<Double> kShooterRPM = new UpdateRegister<Double>(StringToValue.kDouble);

	// Pivot Status Registers

	public static final UpdateRegister<Double> kPivotRev = new UpdateRegister<Double>(StringToValue.kDouble);

	public static final List<Register<?>> kRegisters = Collections.unmodifiableList(Arrays.asList(kTelemetry, kReal,
			kShooterState, kIntakeState, kTubeState, kPivotState, kClimbState, kDriveState, kShooterValue,
			kShooterClosed, kIntakeValue, kIntakeDown, kTubeValue, kPivotMode, kPivotTargetRev, kPivotSpeed,
			kClimbExtend, kClimbSpeed, kDriveMode, kDriveL, kDriveR, kDriveF, kDriveZ, kShooterRPM, kPivotRev));
}