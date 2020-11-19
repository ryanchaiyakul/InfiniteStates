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

	// Solenoid Ports
	public static final int kShooterF = 1;
	public static final int kShooterR = 0;
	
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
}
