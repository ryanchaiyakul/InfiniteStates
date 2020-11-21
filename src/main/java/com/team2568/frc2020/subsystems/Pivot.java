package com.team2568.frc2020.subsystems;

import com.revrobotics.CANSparkMax;
import com.team2568.frc2020.Constants;
import com.team2568.frc2020.Registers;
import com.team2568.frc2020.registers.UpdateRegister;
import com.team2568.lib.drivers.SparkMaxFactory;

import edu.wpi.first.wpilibj.DigitalInput;

public class Pivot extends Subsystem {
    private static Pivot mInstance;

    private CANSparkMax motor;
    private DigitalInput limit;

    public static Pivot getInstance() {
        if (mInstance == null) {
            mInstance = new Pivot();
        }

        return mInstance;
    }

    private Pivot() {
        motor = SparkMaxFactory.getDefault(Constants.kPivotMotor);
        SparkMaxFactory.setPIDF(motor, Constants.kPivotkP, Constants.kPivotkI, Constants.kPivotkD, Constants.kPivotkF);
        SparkMaxFactory.setOutput(motor, Constants.kPivotMin, Constants.kPivotMax);

        limit = new DigitalInput(Constants.kPivotLimit);
    }

    public UpdateRegister<?> getRegister() {
        return Registers.kPivotState;
    }

    public void onStart() {

    }

    public void evaluateState() {
        switch (Registers.kPivotState.get()) {
            case OFF:
            break;
        }
    }

    public void setState() {

    }

    public void onStop() {

    }

    public void writeDashboard() {

    }
}
