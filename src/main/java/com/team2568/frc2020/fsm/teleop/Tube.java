package com.team2568.frc2020.fsm.teleop;

import com.team2568.frc2020.Constants;
import com.team2568.frc2020.Registers;
import com.team2568.frc2020.fsm.FSM;
import com.team2568.frc2020.states.TubeState;
import com.team2568.frc2020.subsystems.Tube.TubeValue;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * LB move the balls up the tube. LT runs both the intake and tube to intake the
 * balls. RT shoots the balls and the tube must spin to push the unlocked balls
 * into the shooter wheels. B reverses the tube to removes the balls from the
 * back.
 * 
 * @author Ryan Chaiyakul
 */
public class Tube extends FSM {
    private static Tube mInstance;

    private TubeState nState;
    private TubeValue value;

    public static Tube getInstance() {
        if (mInstance == null) {
            mInstance = new Tube();
        }
        return mInstance;
    }

    private Tube() {
    };

    public void compute() {
        nState = Registers.kTubeState.get();

        switch (Registers.kTubeState.get()) {
            case OFF:
                value = TubeValue.kOff;

                if (Constants.kOperatorController.getLeftBumper()) {
                    nState = TubeState.INTAKE;
                } else if (Constants.kOperatorController.getRightTrigger()
                        || Constants.kOperatorController.getLeftTrigger()) {
                    nState = TubeState.SHOOT;
                } else if (Constants.kOperatorController.getBButton()) {
                    nState = TubeState.REVERSE;
                }
                break;
            case INTAKE:
                value = TubeValue.kIntake;

                if (!Constants.kOperatorController.getLeftBumper()) {
                    nState = TubeState.OFF;
                }
                break;
            case SHOOT:
                value = TubeValue.kShoot;

                if (!Constants.kOperatorController.getRightTrigger()
                        && !Constants.kOperatorController.getLeftTrigger()) {
                    nState = TubeState.OFF;
                }
                break;
            case REVERSE:
                value = TubeValue.kReverse;

                if (!Constants.kOperatorController.getBButton()) {
                    nState = TubeState.OFF;
                }
                break;
            case STOP:
                value = TubeValue.kOff;
                break;
        }

        Registers.kTubeValue.set(value);
        Registers.kTubeState.set(nState);
    }

    public void writeDashboard() {
        SmartDashboard.putString("TubeState", nState.toString());
    }
}
