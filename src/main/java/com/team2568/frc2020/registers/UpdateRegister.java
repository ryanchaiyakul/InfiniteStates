package com.team2568.frc2020.registers;

public class UpdateRegister<T> extends Register<T> {
	private T mCurrent;

	@Override
	public void set(T value) {
		synchronized (mLock) {
			mCurrent = value;
		}
	}

	public void update() {
		synchronized (mLock) {
			super.set(mCurrent);
		}
	}
}
