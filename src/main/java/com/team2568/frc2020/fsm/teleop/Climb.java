package com.team2568.frc2020.fsm.teleop;

import com.team2568.frc2020.Constants;
import com.team2568.frc2020.Registers;
import com.team2568.frc2020.fsm.FSM;
import com.team2568.frc2020.states.ClimbState;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Y button toggles climb extension. Right Y axis controls the speed of the
 * screw when the climber has been toggled at least once.
 * 
 * @author Ryan Chaiyakul
 */
public class Climb extends FSM {
    private static Climb mInstance;

    private ClimbState nState;
    private boolean extend;
    private double joystick;

    public static Climb getInstance() {
        if (mInstance == null) {
            mInstance = new Climb();
        }
        return mInstance;
    }

    private Climb() {
    };

    public void compute() {
        nState = Registers.kClimbState.get();

        switch (Registers.kClimbState.get()) {
            case OFF:
                extend = false;
                joystick = 0;

                if (Constants.kOperatorController.getYButtonPressed()) {
                    nState = ClimbState.EXTENDED;
                }
                break;
            case EXTENDED:
                extend = true;
                joystick = Constants.kOperatorController.getRightDeadzoneY();

                if (Constants.kOperatorController.getYButtonPressed()) {
                    nState = ClimbState.CLOSED;
                }
                break;
            case CLOSED:
                extend = false;
                joystick = Constants.kOperatorController.getRightDeadzoneY();

                if (Constants.kOperatorController.getYButtonPressed()) {
                    nState = ClimbState.EXTENDED;
                }
                break;
            case STOP:
                // Do not change extended state because it might be climbed
                joystick = 0;
                break;
        }

        Registers.kClimbState.set(nState);
        Registers.kClimbExtend.set(extend);
        Registers.kClimbSpeed.set(joystick);
    }

    public void writeDashboard() {
        SmartDashboard.putString("ClimbState", nState.toString());
    }
}
