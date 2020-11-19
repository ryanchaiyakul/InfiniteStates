package com.team2568.frc2020;

import java.util.ArrayList;

import com.team2568.frc2020.loops.ILooper;
import com.team2568.frc2020.states.GameState;

public class AoB implements Runnable {
    private static AoB mInstance;

    private ArrayList<ILooper> mLoopers;

    public static AoB getInstance() {
        if (mInstance == null) {
            mInstance = new AoB();
        }
        return mInstance;
    }

    public void registerLooper(ILooper looper) {
        mLoopers.add(looper);
    }

    // Run these commands in the aptly named callbacks
    public void teleopInit() {
        Registers.kGameState.set(GameState.TELEOP);
    }

    public void autoInit() {
        Registers.kGameState.set(GameState.AUTO);
    }

    public void testInit() {
        Registers.kGameState.set(GameState.TEST);
    }

    public void disabledInit() {
        Registers.kGameState.set(GameState.DISABLED);
    }

    public void run() {
        switch (Registers.kGameState.get()) {
            case AUTO:
                break;
            case TELEOP:
                break;
            case TEST:
                break;
            case DISABLED:
                break;
        }
    }
}
