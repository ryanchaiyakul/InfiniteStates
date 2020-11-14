package com.team2568.frc2020.registers;

public class UpdateOnceRegister<T> extends UpdateRegister<T> {
	@override
	public void set(T value) {
		synchronized (mLock) {
			if (mCurrent == null) {
				super.set(value);
			}
		}
	}

	@override
	public void update(T value) {
		super.update();
		mCurrent = null;
	}
}
