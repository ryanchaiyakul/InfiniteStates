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
        super("Teleop", 1);
        registerRunnables(Climb.getInstance().getRunnable(), Intake.getInstance().getRunnable(),
                DriveTrain.getInstance().getRunnable(), Pivot.getInstance().getRunnable(),
                Shooter.getInstance().getRunnable(), Tube.getInstance().getRunnable());
        registerStoppableRegisters(Registers.kClimbState, Registers.kShooterState, Registers.kIntakeState,
                Registers.kTubeState, Registers.kPivotState, Registers.kDriveState);
    }
}
