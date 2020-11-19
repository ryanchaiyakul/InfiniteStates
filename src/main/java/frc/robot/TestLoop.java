package frc.robot;

import com.team2568.frc2020.Registers;
import com.team2568.frc2020.loops.Loop;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TestLoop implements Loop {
    private int mState;

    public void onStart() {
        mState = 0;
        Registers.kY.set(0);
    }

    public void onLoop() {
        switch (mState) {
            case 0:
                if (Registers.kX.get() == 1) {
                    mState = 1;
                }
                Registers.kY.set(0);
                break;
            case 1:
                Registers.kY.set(1);
                mState = 2;
                break;
            case 2:
                break;
        }

        SmartDashboard.putNumber("StateMachine1", mState);
        SmartDashboard.putNumber("Y", Registers.kY.get());
    }

    public void onStop() {
    }
}
