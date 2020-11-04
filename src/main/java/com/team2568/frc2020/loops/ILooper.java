package com.team2568.frc2020.loops;

import java.util.ArrayList;

import com.team2568.frc2020.Constants;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.timer.Timer;

/**
 * ILooper instances manage a list of loops that share a common period.
 * Loops can be added dynamically when the loops are stopped and cannot be removed.
 */

public class ILooper {
	private final double kPeriod;

	private boolean mActive = false;
	private final Object mLoopLock = new Object();

	private Notifier mNotifier;
	private ArrayList<Loop> mLoops = new ArrayList<Loop>();
	
	private Runnable mDefaultRunnable = new Runnable() {
		@Override
		public void run() {
			if (mActive) {
				double now = Timer.getFPGATimestamp();
				synchronized(mLoopLock) {
					for (Loop loop : mLoops) {
						loop.onLoop(now);
					}
				}
			}
		}
	};
	
	public void Looper(String name) {
		Looper(name, Constants.kDefaultPeriod, mDefaultRunnable);
	}

	public void Looper(String name, double period) {
		Looper(name, period, mDefaultRunnable);
	}

	public void Looper(String name, Runnable runnable) {
		Looper(name, Constants.kDefaultPeriod, runnable);
	}

	public void Looper(String name, double period, Runnable runnable) {
		this.kPeriod = period;
		this.mNotifier = new Notifier(runnable);
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
			double now = Timper.getFPGATimestamp();
			synchronized(mLoopLock) {
				for (Loop loop : mLoops) {
					loop.onStart(now);
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

			double now = Timer.getFPGATimestamp();
			synchronized(mLoopLock) {
				for (Loop loop: mLoops) {
					loop.onStop(now);
				}
			}
			mActive = false;
		}	
	}
}
