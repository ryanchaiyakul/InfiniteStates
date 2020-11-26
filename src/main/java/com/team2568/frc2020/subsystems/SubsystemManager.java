package com.team2568.frc2020.subsystems;

import java.util.ArrayList;

import com.team2568.frc2020.loops.ILooper;
import com.team2568.frc2020.registers.StoppableRegister;

/**
 * Looper for subsystems. Only one instance should be created. The stateRegister
 * passed along side the subsystem will be set to its respective reset and stop
 * state when start and stop are called.
 */
public class SubsystemManager extends ILooper {
    private static SubsystemManager mInstance;
    private final Object mStateRegisterLock = new Object();

    private ArrayList<StoppableRegister<?>> mStateRegisters = new ArrayList<StoppableRegister<?>>();

    /**
     * Use this function to get the SubsystemManager object
     * 
     * @return
     */
    public static SubsystemManager getInstance() {
        if (mInstance == null) {
            mInstance = new SubsystemManager();
        }

        return mInstance;
    }

    private SubsystemManager() {
        super("SubsystemManager");
    }

    /**
     * Registers a subsystem and its respective state register (A stoppable
     * register)
     * 
     * @param subsystem
     * @param stateRegister
     */
    public void registerSubsystem(Subsystem subsystem, StoppableRegister<?> stateRegister) {
        registerLoop(subsystem.getLoop());

        // Register stoppable register
        if (!isActive()) {
            mStateRegisters.add(stateRegister);
        }
    }

    /**
     * Sets state registers to reset before starting loops
     */
    @Override
    public void start() {
        if (!isActive()) {
            synchronized (mStateRegisterLock) {
                for (StoppableRegister<?> stateRegister : mStateRegisters) {
                    stateRegister.reset();
                }

                updateRegisters();
            }
            super.start();
        }
    }

    /**
     * Sets state registers to stop before executing one cycle.
     */
    @Override
    public void stop() {
        if (!isActive()) {
            super.stop();

            synchronized (mStateRegisterLock) {
                for (StoppableRegister<?> stateRegister : mStateRegisters) {
                    stateRegister.stop();
                }
            }

            updateRegisters();
            step();
        }
    }
}
