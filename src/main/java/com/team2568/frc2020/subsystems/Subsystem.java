package com.team2568.frc2020.subsystems;

import com.team2568.frc2020.Registers;
import com.team2568.frc2020.loops.Loop;
import com.team2568.frc2020.registers.UpdateRegister;

public abstract class Subsystem {
    private final Loop mLoop = new Loop() {
        @Override
        public void onStart() {
            onStart();
        }

        @Override
        public void onLoop() {
            evaluateState();
            setState();

            if (Registers.kDashboard.get()) {
                writeDashboard();
            }
        }

        @Override
        public void onStop() {
            onStop();
        }
    };

    public Loop getLoop() {
        return mLoop;
    }

    public abstract UpdateRegister<?> getRegister();

    public abstract void onStart();

    public abstract void onStop();

    public abstract void evaluateState();

    public abstract void setState();

    public abstract void writeDashboard();

}
