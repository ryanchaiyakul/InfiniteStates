package com.team2568.registers;


/**
 * A Register restricts writing and reading of a singluar value.
 */

public abstract class Register<T> {
	private T mValue;
	private object mLock = new Object();

	public void set(T value) {
		synchronized(mLock) {
			this.mValue = value;
		}
	}

	public T get() {
		synchronized(mLock) {
			return mValue;
		}
	}
}
