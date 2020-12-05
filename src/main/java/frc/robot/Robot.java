/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2020 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.team2568.frc2020.Registers;
import com.team2568.frc2020.subsystems.Climb;
import com.team2568.frc2020.subsystems.DriveTrain;
import com.team2568.frc2020.subsystems.Intake;
import com.team2568.frc2020.subsystems.Pivot;
import com.team2568.frc2020.subsystems.Shooter;
import com.team2568.frc2020.subsystems.SubsystemManager;
import com.team2568.frc2020.subsystems.Tube;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
	private SubsystemManager mManager;

	private Shooter mShooter;
	private Intake mIntake;
	private Tube mTube;
	private Pivot mPivot;
	private Climb mClimb;
	private DriveTrain mDrive;

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		Registers.kSimulate.set(!RobotBase.isReal());

		SmartDashboard.putBoolean("isSimulate", Registers.kSimulate.get());

		mManager = SubsystemManager.getInstance();

		mShooter = Shooter.getInstance();
		mIntake = Intake.getInstance();
		mTube = Tube.getInstance();
		mClimb = Climb.getInstance();
		mDrive = DriveTrain.getInstance();
		mPivot = Pivot.getInstance();

		mManager.registerSubsystem(mShooter, Registers.kShooterState);
		mManager.registerSubsystem(mIntake, Registers.kIntakeState);
		mManager.registerSubsystem(mTube, Registers.kTubeState);
		mManager.registerSubsystem(mClimb, Registers.kClimbState);
		mManager.registerSubsystem(mDrive, Registers.kDriveState);
		mManager.registerSubsystem(mPivot, Registers.kPivotState);
	}

	/**
	 * This function is called every robot packet, no matter the mode. Use this for
	 * items like diagnostics that you want ran during disabled, autonomous,
	 * teleoperated and test.
	 *
	 * <p>
	 * This runs after the mode specific periodic functions, but before LiveWindow
	 * and SmartDashboard integrated updating.
	 */
	@Override
	public void robotPeriodic() {
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable chooser
	 * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
	 * remove all of the chooser code and uncomment the getString line to get the
	 * auto name from the text box below the Gyro
	 *
	 * <p>
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the SendableChooser
	 * make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		Registers.kTelemetry.set(false);
		mManager.stop();
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
	}

	/**
	 * This function is called once when teleop is enabled.
	 */
	@Override
	public void teleopInit() {
		Registers.kTelemetry.set(false);
		mManager.start();
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
	}

	/**
	 * This function is called once when the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		Registers.kTelemetry.set(false);
		mManager.stop();
	}

	/**
	 * This function is called periodically when disabled.
	 */
	@Override
	public void disabledPeriodic() {
	}

	/**
	 * This function is called once when test mode is enabled.
	 */
	@Override
	public void testInit() {
		Registers.kTelemetry.set(true);
		mManager.start();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}

	@Override
	public void simulationPeriodic() {

	}
}
