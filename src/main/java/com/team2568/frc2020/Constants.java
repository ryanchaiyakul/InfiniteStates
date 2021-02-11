package com.team2568.frc2020;

public class Constants {
	// RunnableConstants
	public static final double kDefaultPeriod = 0.01;

	// Controllers
	public static final Controller kDriveController = new Controller(0);
	public static final Controller kOperatorController = new Controller(1);

	// CAN Ports

	// https://docs.google.com/spreadsheets/d/1xZ8lbKK4TP_i9pDaXgZNcLdZUCCJ9CWRHAVqdm4gPFw/edit?usp=sharing

	public static final int kShooterLMotor = 1;
	public static final int kShooterRMotor = 2;

	public static final int kPivotMotor = 11;

	public static final int kIntakeLMotor = 12;
	public static final int kIntakeRMotor = 13;

	public static final int kTubeLMotor = 14;
	public static final int kTubeRMotor = 15;

	public static final int kClimbLMotor = 8;
	public static final int kClimbRMotor = 7;

	public static final int kDriveTrainLAMotor = 3;
	public static final int kDriveTrainLBMotor = 4;
	public static final int kDriveTrainLCMotor = 5;

	public static final int kDriveTrainRAMotor = 6;
	public static final int kDriveTrainRBMotor = 9;
	public static final int kDriveTrainRCMotor = 10;

	// Solenoid Ports
	public static final int kShooterF = 1;
	public static final int kShooterR = 0;
	public static final int kIntakeF = 4;
	public static final int kIntakeR = 5;
	public static final int kClimbF = 2;
	public static final int kClimbR = 3;

	// Digital Ports
	public static final int kPivotLimit = 1;
	public static final int kClimbLLimit = 0;
	public static final int kClimbULimit = 2;

	// Controller Constants
	public static final double kTriggerThreshold = 0.3;
	public static final double kJoystickDeadzone = 0.1;

	// SparkMax Constants
	public static final int kSparkCurrentLimit = 40;

	// TalonSRX Constants
	public static final int kTalonSRXCurrentLimit = 60;

	// Shooter Constants
	public static final double kShooterRPM = 4500;
	public static final double kShooterTurnSpeed = 500;
	public static final double kShooterSpinTime = 0.7;

	public static final double kShooterkP = 0.3;
	public static final double kShooterkI = 0;
	public static final double kShooterkD = 3.5;
	public static final double kShooterkF = 0.5;

	// Intake Constants
	public static final double kIntakeSpeed = 0.8;

	// Tube Constants
	public static final double kTubeIntakeSpeed = 0.7;
	public static final double kTubeShootSpeed = 1;

	// Pivot Constants
	public static final double kPivotAutoMinSpeed = -0.1;
	public static final double kPivotAutoMaxSpeed = 0.25;

	public static final double kPivotZeroSpeed = 0.05;
	public static final double kPivotTeleopSpeed = 0.25;

	public static final double kPivotZeroThreshold = 3;
	public static final double kPivotHighestThreshold = 80;
	public static final double kPivotTargetRevThreshold = 0.5;

	public static final double kPivotTrench = 3.8;
	public static final double kPivotLine = 16.35;
	public static final double kPivotAgainst = 79;
	public static final double kPivotWheel = 33;

	public static final double kPivotkP = 0;
	public static final double kPivotkI = 0;
	public static final double kPivotkD = 0;
	public static final double kPivotkF = 0;

	// Climb Constants
	public static final double kClimbSpeed = 0.975;

	// Drive Constants
	public static final double kDriveSlowPower = 0.4;
	public static final double kDriveRegularPower = 0.7;
	public static final double kDriveTurboPower = 0.9;
}
