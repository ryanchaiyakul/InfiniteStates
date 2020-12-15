package com.team2568.frc2020.registers;

interface StringToValFunc<T> {
    public T convert (String s);
}

/**
 * Actual value of the register will not be written until update is called.
 */
public class UpdateRegister<T> extends Register<T> {
	private T mCurrent;

	@Override
	public void set(T value) {
		synchronized (mLock) {
			mCurrent = value;
		}
	}

	public void setStringVal(String value, StringToValFunc<T> f) {
		synchronized (mLock) {
			mCurrent = f.convert(value);
		}
    }

	public void update() {
		synchronized (mLock) {
			super.set(mCurrent);
		}
	}
}
