package com.team2568.frc2020.registers;

public class UpdateRegister<T> extends Register<T> {
	public T mCurrent;

	@override
	public void set(T value) {
		synchronized(mLock) {
			mCurrent = value;
		}
	}

	public void update() {
		synchronized(mLock) {
			mValue = mCurrent;
		}
	}
}
