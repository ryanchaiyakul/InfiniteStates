package com.team2568.frc2020.states;

public enum PivotState {
    OFF(0);

    private final double rev;

    private PivotState(double rev) {
        this.rev = rev;
    }

    public double getRev() {
        return rev;
    }
}
