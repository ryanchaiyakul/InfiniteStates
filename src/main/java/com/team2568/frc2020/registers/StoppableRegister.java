package com.team2568.frc2020.registers;

/**
 * Sets the current value to the passed stop value when requested.
 */
public class StoppableRegister<T> extends ResetableRegister<T> {
    private T mStopValue;

    public StoppableRegister(T resetValue, T stopValue) {
        super(resetValue);
        this.mStopValue = stopValue;
    }

    public void stop() {
        set(mStopValue);
    }
    
    public T getStop() {
        return mStopValue;
    }
}
