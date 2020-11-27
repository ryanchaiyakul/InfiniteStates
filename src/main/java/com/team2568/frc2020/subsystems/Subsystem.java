package com.team2568.frc2020.subsystems;

import com.team2568.frc2020.Registers;
import com.team2568.frc2020.loops.Loop;

/**
 * Subsystems should have one instance and be registered to the SubsystemManager
 * instance. Outputs (Motors) should not be set until the setOutput call to
 * avoid glitches during computation.
 */
public abstract class Subsystem {
    private final Loop mLoop = new Loop() {
        @Override
        public void onLoop() {
            compute();
            setOutputs();

            writeDashboard();

            if (Registers.kTelemetry.get()) {
                outputTelemetry();
            }
        }
    };

    public Loop getLoop() {
        return mLoop;
    }

    // Compute next state and local values
    public abstract void compute();

    // Read from results from compute and set motors etc.
    public abstract void setOutputs();

    // Output permanent statements
    public abstract void writeDashboard();

    // Debugging dashboard statements
    public abstract void outputTelemetry();

}
