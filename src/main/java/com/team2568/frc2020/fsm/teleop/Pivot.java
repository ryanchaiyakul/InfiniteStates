package com.team2568.frc2020.fsm.teleop;

import com.team2568.frc2020.Constants;
import com.team2568.frc2020.Registers;
import com.team2568.frc2020.fsm.FSM;
import com.team2568.frc2020.fsm.auto.Pivot.PivotAutoMode;
import com.team2568.frc2020.states.PivotState;
import com.team2568.frc2020.subsystems.Pivot.PivotMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The left Y axis controls the pivot speed. The D pad has macros which are set
 * to different heights for shooting.
 * 
 * @author Ryan Chaiyakul
 */
public class Pivot extends FSM {
    private static Pivot mInstance;

    private PivotState nState;

    private PivotMode nMode;
    private double joystick;
    private double targetRev;

    public static Pivot getInstance() {
        if (mInstance == null) {
            mInstance = new Pivot();
        }
        return mInstance;
    }

    private Pivot() {
    };

    public void compute() {
        nState = Registers.kPivotState.get();

        switch (Registers.kPivotState.get()) {
            case TELEOP:
                nMode = PivotMode.kTeleop;
                targetRev = 0;
                joystick = -Constants.kOperatorController.getLeftDeadzoneY();

                if (joystick == 0) {
                    if (Constants.kOperatorController.getRightStickButton()) {
                        nState = PivotState.TARGET;

                        Registers.kPivotAutoMode.set(PivotAutoMode.kTarget);
                    } else {
                        switch (Constants.kOperatorController.getPOV()) {
                            case 0:
                                // Up
                                nState = PivotState.AGAINST;
                                break;
                            case 90:
                                // Right
                                nState = PivotState.WHEEL;
                                break;
                            case 180:
                                // Down
                                nState = PivotState.TRENCH;
                                break;
                            case 270:
                                // Left
                                nState = PivotState.LINE;
                                break;
                            default:
                                // Nothing is pressed
                                break;
                        }
                    }
                }
                break;
            case AGAINST:
                nMode = PivotMode.kAuto;
                targetRev = Constants.kPivotAgainst;
                joystick = 0;

                if (Constants.kOperatorController.getRightDeadzoneY() != 0
                        || Constants.kOperatorController.getPOV() != 0) {
                    nState = PivotState.TELEOP;
                }
                break;
            case WHEEL:
                nMode = PivotMode.kAuto;
                targetRev = Constants.kPivotWheel;
                joystick = 0;

                if (Constants.kOperatorController.getRightDeadzoneY() != 0
                        || Constants.kOperatorController.getPOV() != 90) {
                    nState = PivotState.TELEOP;
                }
                break;
            case TRENCH:
                nMode = PivotMode.kAuto;
                targetRev = Constants.kPivotTrench;
                joystick = 0;

                if (Constants.kOperatorController.getRightDeadzoneY() != 0
                        || Constants.kOperatorController.getPOV() != 180) {
                    nState = PivotState.TELEOP;
                }
                break;
            case LINE:
                nMode = PivotMode.kAuto;
                targetRev = Constants.kPivotLine;
                joystick = 0;

                if (Constants.kOperatorController.getRightDeadzoneY() != 0
                        || Constants.kOperatorController.getPOV() != 270) {
                    nState = PivotState.TELEOP;
                }
                break;
            case TARGET:
                nMode = PivotMode.kAuto;
                targetRev = Registers.kPivotAutoTargetRev.get();
                joystick = 0;

                if (Constants.kOperatorController.getRightDeadzoneY() != 0
                        || !Constants.kOperatorController.getRightStickButton()) {
                    nState = PivotState.TELEOP;

                    Registers.kPivotAutoMode.set(PivotAutoMode.kOff);
                }
                break;
            case STOP:
                nMode = PivotMode.kOff;
                targetRev = 0;
                joystick = 0;
                break;
        }

        Registers.kPivotMode.set(nMode);
        Registers.kPivotTargetRev.set(targetRev);
        Registers.kPivotSpeed.set(joystick);
        Registers.kPivotState.set(nState);
    }

    public void writeDashboard() {
        SmartDashboard.putString("PivotState", nState.toString());
    }
}
