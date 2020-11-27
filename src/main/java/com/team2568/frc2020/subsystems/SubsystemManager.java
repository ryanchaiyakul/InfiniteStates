package com.team2568.frc2020.subsystems;

import java.util.ArrayList;

import com.team2568.frc2020.loops.ILooper;
import com.team2568.frc2020.registers.StoppableRegister;

/**
 * Looper for subsystems. Only one instance should be created. The stateRegister
 * passed along side the subsystem will be set to its respective reset and stop
 * state when start and stop are called.
 */
public class SubsystemManager {
    private static SubsystemManager mInstance;
    private ILooper iLooper;
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
        iLooper = new ILooper("SubsystemManager");
    }

    /**
     * Registers a subsystem and its respective state register (A stoppable
     * register)
     * 
     * @param subsystem
     * @param stateRegister
     */
    public void registerSubsystem(Subsystem subsystem, StoppableRegister<?> stateRegister) {
        iLooper.registerLoop(subsystem.getLoop());

        // Register stoppable register
        if (!iLooper.isActive()) {
            mStateRegisters.add(stateRegister);
        }
    }

    /**
     * Sets state registers to reset before starting loops
     */
    public void start() {
        if (!iLooper.isActive()) {
            synchronized (mStateRegisterLock) {
                for (StoppableRegister<?> stateRegister : mStateRegisters) {
                    stateRegister.reset();
                }

                iLooper.updateRegisters();
            }
            iLooper.start();
        }
    }

    /**
     * Sets state registers to stop before executing one cycle.
     */
    public void stop() {
        if (!iLooper.isActive()) {
            iLooper.stop();

            synchronized (mStateRegisterLock) {
                for (StoppableRegister<?> stateRegister : mStateRegisters) {
                    stateRegister.stop();
                }
            }

            iLooper.updateRegisters();
            iLooper.step();
        }
    }
}
