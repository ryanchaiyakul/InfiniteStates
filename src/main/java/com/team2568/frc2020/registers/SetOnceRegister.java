package com.team2568.frc2020.registers;

public class SetOnceRegister<T> extends Register<T> {
    private boolean isSet;

    public SetOnceRegister() {
        isSet = false;
    }

    public SetOnceRegister(T value) {
        this();
        set(value);
    }

    @Override
    public void set(T value) {
        if (!isSet) {
            super.set(value);
            isSet = true;
        }
    }
}
