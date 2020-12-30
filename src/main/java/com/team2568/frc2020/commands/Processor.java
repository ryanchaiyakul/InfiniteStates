package com.team2568.frc2020.commands;

import java.util.List;

import com.team2568.frc2020.Registers;
import com.team2568.frc2020.ILooper;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Processor extends ILooper {
    private static Processor mInstance;

    private final Object mCommandLock = new Object();

    private int counter;

    private List<Command> mCommandList;

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (isActive()) {
                if (counter < mCommandList.size()) {
                    int temp = mCommandList.get(counter).execute();

                    /**
                     * Return of command (temp) action
                     * 
                     * -2 : stay
                     * 
                     * -1 : increment
                     * 
                     * any : jump to line
                     */
                    if (temp == -1) {
                        counter++;
                    } else if (temp != -2) {
                        counter = temp;
                    }
                }

                if (Registers.kTelemetry.get()) {
                    SmartDashboard.putNumber("ProcessorCounter", counter);
                }
            }
        }
    };

    public static Processor getInstance() {
        if (mInstance == null) {
            mInstance = new Processor();
        }
        return mInstance;
    }

    private Processor() {
        super("Processor", 1);
        registerRunnables(mRunnable);

        registerStoppableRegisters(Registers.kClimbExtend, Registers.kClimbSpeed);
        registerStoppableRegisters(Registers.kIntakeDown, Registers.kIntakeValue);
        registerStoppableRegisters(Registers.kDriveMode, Registers.kDriveF, Registers.kDriveZ, Registers.kDriveL,
                Registers.kDriveR);
        registerStoppableRegisters(Registers.kPivotMode, Registers.kPivotSpeed, Registers.kPivotTargetRev);
        registerStoppableRegisters(Registers.kShooterClosed, Registers.kShooterValue);
        registerStoppableRegisters(Registers.kTubeValue);
    }

    public void loadProgram(List<Command> commandList) {
        if (!isActive()) {
            synchronized (mCommandLock) {
                mCommandList = commandList;
            }
        }
    }

    @Override
    public void start() {
        // Load before you run
        if (mCommandList != null) {
            counter = 0;
            super.start();
        }
    }
}
