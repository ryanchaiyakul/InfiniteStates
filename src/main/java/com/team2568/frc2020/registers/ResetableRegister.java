package com.team2568.frc2020.registers;

/**
 * Sets the current value to the passed reset value when requested.
 */
public class ResetableRegister<T> extends UpdateRegister<T> {
    private T mResetValue;

    public ResetableRegister(T resetValue) {
        this.mResetValue = resetValue;
    }

    public void reset() {
        set(mResetValue);
    }

    public T getReset() {
        return mResetValue;
    }
}
