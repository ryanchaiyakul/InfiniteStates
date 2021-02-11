package com.team2568.frc2020.commands;

import com.team2568.frc2020.registers.Register;

public class Compare<T> {
    private Register<T> register;
    private T value;

    public Compare(Register<T> register, T value) {
        this.register = register;
        this.value = value;
    }

    public boolean isEqual() {
        return register.get().equals(value);
    }
}
