package com.team2568.frc2020.fsm.teleop;

import com.team2568.frc2020.Registers;
import com.team2568.frc2020.ILooper;

/**
 * Executes the runnables of all teleop FSMs and updates all output and state
 * registers
 */
public class TeleopLooper extends ILooper {
    private static TeleopLooper mLooper;

    public static TeleopLooper getInstance() {
        if (mLooper == null) {
            mLooper = new TeleopLooper();
        }
        return mLooper;
    }

    private TeleopLooper() {
        super("Teleop");
        registerRunnables(Climb.getInstance().getRunnable(), Intake.getInstance().getRunnable(),
                DriveTrain.getInstance().getRunnable(), Pivot.getInstance().getRunnable(),
                Shooter.getInstance().getRunnable(), Tube.getInstance().getRunnable());
        registerStoppableRegisters(Registers.kClimbState, Registers.kShooterState, Registers.kIntakeState,
                Registers.kTubeState, Registers.kPivotState, Registers.kDriveState);

        registerStoppableRegisters(Registers.kClimbExtend, Registers.kClimbSpeed);
        registerStoppableRegisters(Registers.kIntakeDown, Registers.kIntakeValue);
        registerStoppableRegisters(Registers.kDriveMode, Registers.kDriveF, Registers.kDriveZ, Registers.kDriveL,
                Registers.kDriveR);
        registerStoppableRegisters(Registers.kPivotMode, Registers.kPivotSpeed, Registers.kPivotTargetRev);
        registerStoppableRegisters(Registers.kShooterClosed, Registers.kShooterValue);
        registerStoppableRegisters(Registers.kTubeValue);
    }
}
