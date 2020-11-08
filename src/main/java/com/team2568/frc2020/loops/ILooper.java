package com.team2568.frc2020.loops;

import java.util.ArrayList;

import com.team2568.frc2020.Constants;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Timer;

/**
 * ILooper instances manage a list of loops that share a common period.
 * Loops can be added dynamically when the loops are stopped and cannot be removed.
 */

public class ILooper {
	private double kPeriod;

	private boolean mActive = false;
	private final Object mLoopLock = new Object();

	private Notifier mNotifier;
	private ArrayList<Loop> mLoops = new ArrayList<Loop>();
	
	private final Runnable mDefaultRunnable = new Runnable() {
		@Override
		public void run() {
			if (mActive) {
				synchronized(mLoopLock) {
					for (Loop loop : mLoops) {
						loop.onLoop();
					}
				}
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

	public void registerLoop(Loop loop) {
		if (!mActive) {
			synchronized(mLoopLock) {
				mLoops.add(loop);
			}
		}
	}

	public void start() {
		if (!mActive) {
			synchronized(mLoopLock) {
				for (Loop loop : mLoops) {
					loop.onStart();
				}
			}
			mActive = true;

			mNotifier.startPeriodic(this.kPeriod);
		}

	}

	public void stop() {
		if(mActive) {
			// Stop notifier before executing onStop so that notifier does not execute onLoop afterwards
			mNotifier.stop();

			synchronized(mLoopLock) {
				for (Loop loop: mLoops) {
					loop.onStop();
				}
			}
			mActive = false;
		}	
	}
}
