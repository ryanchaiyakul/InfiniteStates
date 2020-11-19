package com.team2568.frc2020;

import com.team2568.frc2020.registers.Register;
import com.team2568.frc2020.registers.UpdateRegister;
import com.team2568.frc2020.states.GameState;

public class Registers {
	public static final Register<Boolean> kDashboard = new Register<Boolean>();
	public static final Register<GameState> kGameState = new Register<GameState>();

	public static final UpdateRegister<Integer> kX = new UpdateRegister<Integer>();
	public static final UpdateRegister<Integer> kY = new UpdateRegister<Integer>();
}
