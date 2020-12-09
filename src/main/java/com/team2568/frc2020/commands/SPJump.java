package com.team2568.frc2020.commands;

import com.team2568.frc2020.registers.Register;

public class SPJump implements Command {
    private Register<Integer> register;

    public SPJump(Register<Integer> register) {
        this.register = register;
    }

    @Override
    public int execute() {
        return register.get();
    }
}
