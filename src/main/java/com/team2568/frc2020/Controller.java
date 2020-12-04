package com.team2568.frc2020;

import edu.wpi.first.wpilibj.XboxController;

/**
 * Cleans up the API to the XboxController by removing the need for a hand
 * constant and applying a threshold to triggers. In the future, inputs may be
 * buffered so that races will not occur during the compute phase of the
 * subsystems.
 */
public class Controller extends XboxController {

    public Controller(int port) {
        super(port);
    }

    public boolean getLeftTrigger() {
        return getTrigger(Hand.kLeft);
    }

    public boolean getRightTrigger() {
        return getTrigger(Hand.kRight);
    }

    private boolean getTrigger(Hand hand) {
        return Constants.kTriggerThreshold <= super.getTriggerAxis(hand);
    }

    public boolean getLeftBumper() {
        return getBumper(Hand.kLeft);
    }

    public boolean getRightBumper() {
        return getBumper(Hand.kRight);
    }

    public boolean getLeftBumperPressed() {
        return getBumperPressed(Hand.kLeft);
    }

    public boolean getRightBumperPressed() {
        return getBumperPressed(Hand.kRight);
    }

    public boolean getLeftBumperReleased() {
        return getBumperReleased(Hand.kLeft);
    }

    public boolean getRightBumperReleased() {
        return getBumperReleased(Hand.kRight);
    }

    public double getLeftX() {
        return getX(Hand.kLeft);
    }

    public double getRightX() {
        return getX(Hand.kRight);
    }

    public double getLeftY() {
        return getY(Hand.kLeft);
    }

    public double getRightY() {
        return getY(Hand.kRight);
    }

    public double getDeadzoneX(Hand hand) {
        if (Math.abs(getX(hand)) < Constants.kJoystickDeadzone) {
            return 0;
        }
        return getX(hand);
    }

    public double getDeadzoneY(Hand hand) {
        if (Math.abs(getY(hand)) < Constants.kJoystickDeadzone) {
            return 0;
        }
        return getX(hand);
    }

    public double getLeftDeadzoneX() {
        return getDeadzoneX(Hand.kLeft);
    }

    public double getRightDeadzoneX() {
        return getDeadzoneX(Hand.kRight);
    }

    public double getLeftDeadzoneY() {
        return getDeadzoneY(Hand.kLeft);
    }

    public double getRightDeadzoneY() {
        return getDeadzoneY(Hand.kRight);
    }
}
