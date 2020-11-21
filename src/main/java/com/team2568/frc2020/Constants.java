package com.team2568.frc2020;

public class Constants {
	// Loop Constants
	public static final double kDefaultPeriod = 0.01;

	// Controllers
	public static final Controller kDriveController = new Controller(0);
	public static final Controller kOperatorController = new Controller(0);

	// CAN Ports
	public static final int kShooterLMotor = 1;
	public static final int kShooterRMotor = 2;
	public static final int kPivotMotor = 3;

	// Solenoid Ports
	public static final int kShooterF = 1;
	public static final int kShooterR = 0;

	// Digital Ports
	public static final int kPivotLimit = 1;
	
	// Controller Constants
	public static final double kTriggerThreshold = 0.1;

	// SparkMax Constants
	public static final int kSparkCurrentLimit = 40;

	// Shooter Constants
	public static final double kShooterRPM = 5000;
	public static final double kTurnRPM = 500;
	public static final double kShooterThreshold = 100;
	public static final double kShooterSpinTime = 0.5;

	public static final double kShooterkP = 0;
	public static final double kShooterkI = 0;
	public static final double kShooterkD = 0;
	public static final double kShooterkF = 0;

	// Pivot Constants
	public static final double kPivotMin = -0.1;
	public static final double kPivotMax = 0.5;

	public static final double kPivotkP = 0;
	public static final double kPivotkI = 0;
	public static final double kPivotkD = 0;
	public static final double kPivotkF = 0;
}
