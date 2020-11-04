package com.team2568.frc2020.loops;

import java.util.ArrayList;

import com.team2568.frc2020.Constants;

import edu.wpi.first.wpilibj.Notifier;

/**
 * An instance of the Looper class manages an arraylist of loops that share a common period.
 * Specifically, the looper handles startup, operation, and termination of these loops.
 * A lock is utilized when accessing the arraylist of loops.
 * Create a new instance of Looper for each unique notifier needed.
 */

public class Looper {
	private final double kPeriod;

	private boolean mActive = false;
	private final Object mLoopLock = new Object();

	private Notifier mNotifier;
	private ArrayList<Loop> mLoops = new ArrayList<Loop>();
	
	private Runnable mDefaultRunnable = new Runnable() {
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
	
	public Looper() {
		this.kPeriod = Constants.kDefaultPeriod;
		this.mNotifier = new Notifier(this.mDefaultRunnable);
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
			mNotifier.stop();

			synchronized(mLoopLock) {
				for (Loop loop: mLoops) {
					loop.onStop();
				}
			}
			mActive = false;
		}	
	}

	public void registerLoop(Loop loop) {
		synchronized(mLoopLock) {
			mLoops.add(loop);
		}
	}
}
