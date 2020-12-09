package com.team2568.frc2020.fsm.teleop;

import com.team2568.frc2020.Constants;
import com.team2568.frc2020.Registers;
import com.team2568.frc2020.fsm.FSM;
import com.team2568.frc2020.states.IntakeState;
import com.team2568.frc2020.subsystems.Intake.IntakeValue;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake extends FSM {
    private static Intake mInstance;
    
    private IntakeState nState;
    private boolean down;
    private IntakeValue on;

    public static Intake getInstance() {
        if (mInstance == null) {
            mInstance = new Intake();
        }
        return mInstance;
    }

    private Intake() {
    };

    public void compute() {
        nState = Registers.kIntakeState.get();

        switch (Registers.kIntakeState.get()) {
            case UP:
                down = false;
                on = IntakeValue.kOff;

                if (Constants.kOperatorController.getAButtonPressed()) {
                    nState = IntakeState.DOWN;
                }
                break;
            case DOWN:
                down = true;
                on = IntakeValue.kOff;

                if (Constants.kOperatorController.getAButtonPressed()) {
                    nState = IntakeState.UP;
                } else if (Constants.kOperatorController.getLeftTrigger()) {
                    nState = IntakeState.FORWARD;
                } else if (Constants.kOperatorController.getRightBumper()) {
                    nState = IntakeState.REVERSE;
                }
                break;
            case FORWARD:
                down = true;
                on = IntakeValue.kForward;

                if (!Constants.kOperatorController.getLeftTrigger()) {
                    nState = IntakeState.DOWN;
                }
                break;
            case REVERSE:
                down = true;
                on = IntakeValue.kReverse;

                if (!Constants.kOperatorController.getRightBumper()) {
                    nState = IntakeState.DOWN;
                }
                break;
            case STOP:
                down = false;
                on = IntakeValue.kOff;
                break;
        }

        Registers.kIntakeState.set(nState);
        Registers.kIntakeDown.set(down);
        Registers.kIntakeValue.set(on);
    }

    @Override
    public void writeDashboard() {
        SmartDashboard.putString("IntakeState", nState.toString());
    }
}
