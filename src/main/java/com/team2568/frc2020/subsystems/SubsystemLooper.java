package com.team2568.frc2020.subsystems;

import com.team2568.frc2020.Registers;
import com.team2568.frc2020.ILooper;

/**
 * Looper which executes the subsystem runnables and updates status registers
 */
public class SubsystemLooper extends ILooper {
    private static SubsystemLooper mLooper;

    public static SubsystemLooper getInstance() {
        if (mLooper == null) {
            mLooper = new SubsystemLooper();
        }
        return mLooper;
    }

    private SubsystemLooper() {
        super("Subsystem");
        registerRunnables(Climb.getInstance().getRunnable(), DriveTrain.getInstance().getRunnable(),
                Intake.getInstance().getRunnable(), Pivot.getInstance().getRunnable(),
                Shooter.getInstance().getRunnable(), Tube.getInstance().getRunnable());

        registerUpdateRegisters(Registers.kPivotRev, Registers.kShooterRPM);
    }
}
