package com.team2568.frc2020.commands;

import com.team2568.frc2020.registers.Register;

public class Set<T> implements Command {
    private Register<T> register;
    private T value;

    public Set(Register<T> register, T value) {
        this.register = register;
        this.value = value;
    }

    @Override
    public int execute() {
        register.set(value);
        return -1;
    }
}
