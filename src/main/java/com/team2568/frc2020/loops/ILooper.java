package com.team2568.frc2020.loops;

import java.util.ArrayList;

import com.team2568.frc2020.Constants;
import com.team2568.frc2020.registers.UpdateRegister;

import edu.wpi.first.wpilibj.Notifier;

/**
 * ILooper instances manage a list of loops that share a common period. Loops
 * can be added dynamically when inactive. However, loops cannot be removed.
 * Register a register to a looper if a loop here is the main source (FSMs are
 * output latched).
 */

public class ILooper {
	// Flags
	private boolean mActive = false;
	private final Object mLoopLock = new Object();

	// Notifier
	private Notifier mNotifier;
	private double kPeriod;

	// Registered objects
	private ArrayList<Loop> mLoops = new ArrayList<Loop>();
	private ArrayList<UpdateRegister> mUpdateRegisters = new ArrayList<UpdateRegister>();

	// Runnable instance that executes onLoop for every loop and updates registers
	private final Runnable mDefaultRunnable = new Runnable() {
		@Override
		public void run() {
			if (mActive) {
				synchronized (mLoopLock) {
					for (Loop loop : mLoops) {
						loop.onLoop();
					}
				}
				updateRegisters();
			}
		}
	};

	public ILooper(String name) {
		this(name, Constants.kDefaultPeriod);
	}

	public ILooper(String name, double period) {
		this.kPeriod = period;

		this.mNotifier = new Notifier(mDefaultRunnable);
		mNotifier.setName(name);
	}

	/**
	 * Register a loop to be controlled by this looper
	 * 
	 * @param loop
	 */
	public void registerLoop(Loop loop) {
		if (!mActive) {
			synchronized (mLoopLock) {
				mLoops.add(loop);
			}
		}
	}

	/**
	 * Register a register to be updated by this looper
	 * 
	 * @param register
	 */
	public void registerRegister(UpdateRegister register) {
		if (!mActive) {
			mUpdateRegisters.add(register);
		}
	}

	/**
	 * Starts periodic notifier and executes loops' onStart
	 */
	public void start() {
		if (!mActive) {
			synchronized (mLoopLock) {
				for (Loop loop : mLoops) {
					loop.onStart();
				}
			}
			updateRegisters();

			mActive = true;

			mNotifier.startPeriodic(this.kPeriod);
		}

	}

	/**
	 * Stops periodic notifier and executes loops' onStop
	 */
	public void stop() {
		if (mActive) {
			mNotifier.stop();

			synchronized (mLoopLock) {
				for (Loop loop : mLoops) {
					loop.onStop();
				}
			}
			updateRegisters();

			mActive = false;
		}
	}

	/**
	 * Executes update for every updatable register registered to this looper
	 */
	private void updateRegisters() {
		for (UpdateRegister updateRegister : mUpdateRegisters) {
			updateRegister.update();
		}
	}
}
