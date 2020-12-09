/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2020 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.ArrayList;

import com.team2568.frc2020.Registers;
import com.team2568.frc2020.commands.Command;
import com.team2568.frc2020.commands.Compare;
import com.team2568.frc2020.commands.JumpIfEqual;
import com.team2568.frc2020.commands.NOOP;
import com.team2568.frc2020.commands.Processor;
import com.team2568.frc2020.commands.Set;
import com.team2568.frc2020.fsm.teleop.TeleopLooper;
import com.team2568.frc2020.loops.ILooper;
import com.team2568.frc2020.subsystems.SubsystemLooper;

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
	private ILooper teleopLooper, subsystemLooper;
	private Processor processor;

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		Registers.kReal.set(RobotBase.isReal());
		// Registers.kReal.set(true);
		SmartDashboard.putBoolean("isReal", Registers.kReal.get());

		if (!Registers.kReal.get()) {
			Registers.kTelemetry.set(true);
		} else {
			Registers.kTelemetry.set(false);
		}

		subsystemLooper = SubsystemLooper.getInstance();
		teleopLooper = TeleopLooper.getInstance();
		processor = Processor.getInstance();

		ArrayList<Command> commandList = new ArrayList<>();

		commandList.add(new Set<Double>(Registers.kDriveL, 1.0));
		commandList.add(new Set<Double>(Registers.kDriveR, 0.5));
		for (int i = 0; i < 100; i++) {
			commandList.add(new NOOP());
		}
		commandList.add(new Set<Double>(Registers.kDriveL, 0.5));
		commandList.add(new Set<Double>(Registers.kDriveR, 1.0));
		for (int i = 0; i < 100; i++) {
			commandList.add(new NOOP());
		}
		commandList.add(new JumpIfEqual(new Compare<Boolean>(Registers.kTelemetry, true), 0));
		commandList.add(new NOOP());

		processor.loadProgram(commandList);
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
		subsystemLooper.reset();
		teleopLooper.stop();
		processor.start();
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
		subsystemLooper.reset();
		teleopLooper.start();
		processor.stop();
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
		subsystemLooper.stop();
		teleopLooper.stop();
		processor.stop();
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
		subsystemLooper.reset();
		teleopLooper.stop();
		processor.stop();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
