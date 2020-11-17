package com.team2568.frc2020.registers;

public class UpdateOnceRegister<T> extends UpdateRegister<T> {
	private boolean updated = false;

	@Override
	public void set(T value) {
		synchronized (mLock) {
			if (!updated) {
				super.set(value);
				updated = true;
			}
		}
	}

	@Override
	public void update() {
		super.update();
		updated = false;
	}
}
