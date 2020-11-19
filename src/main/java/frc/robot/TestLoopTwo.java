package frc.robot;

import com.team2568.frc2020.Registers;
import com.team2568.frc2020.loops.Loop;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TestLoopTwo implements Loop {
    private int mState;

    public void onStart() {
        mState = 0;
        Registers.kX.set(0);
    }

    public void onLoop() {
        switch (mState) {
            case 0:
                Registers.kX.set(0);
                mState = 1;
                break;
            case 1:
                if (Registers.kY.get() == 1) {
                    mState = 2;
                }
                Registers.kX.set(1);
                break;
            case 2:
                break;
        }

        SmartDashboard.putNumber("StateMachine2", mState);
        SmartDashboard.putNumber("X", Registers.kX.get());
    }

    public void onStop() {
    }
}
