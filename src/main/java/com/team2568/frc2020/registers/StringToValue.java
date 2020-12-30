package com.team2568.frc2020.registers;

import com.team2568.frc2020.states.ClimbState;
import com.team2568.frc2020.states.DriveState;
import com.team2568.frc2020.states.IntakeState;
import com.team2568.frc2020.states.PivotState;
import com.team2568.frc2020.states.ShooterState;
import com.team2568.frc2020.states.TubeState;
import com.team2568.frc2020.subsystems.DriveTrain.DriveMode;
import com.team2568.frc2020.subsystems.Intake.IntakeValue;
import com.team2568.frc2020.subsystems.Pivot.PivotMode;
import com.team2568.frc2020.subsystems.Shooter.ShooterValue;
import com.team2568.frc2020.subsystems.Tube.TubeValue;

public interface StringToValue<T> {
    public T convert(String s);

    public static StringToValue<String> kString = new StringToValue<String>() {
        @Override
        public String convert(String s) {
            return s;
        }
    };

    public static StringToValue<Integer> kInteger = new StringToValue<Integer>() {
        @Override
        public Integer convert(String s) {
            return Integer.valueOf(s);
        }
    };

    public static StringToValue<Double> kDouble = new StringToValue<Double>() {
        @Override
        public Double convert(String s) {
            return Double.valueOf(s);
        }
    };

    public static StringToValue<Boolean> kBoolean = new StringToValue<Boolean>() {
        @Override
        public Boolean convert(String s) {
            return Boolean.valueOf(s);
        }
    };

    public static StringToValue<ShooterState> kShooterState = new StringToValue<ShooterState>() {
        @Override
        public ShooterState convert(String s) {
            return ShooterState.valueOf(s);
        }
    };

    public static StringToValue<IntakeState> kIntakeState = new StringToValue<IntakeState>() {
        @Override
        public IntakeState convert(String s) {
            return IntakeState.valueOf(s);
        }
    };

    public static StringToValue<TubeState> kTubeState = new StringToValue<TubeState>() {
        @Override
        public TubeState convert(String s) {
            return TubeState.valueOf(s);
        }
    };

    public static StringToValue<PivotState> kPivotState = new StringToValue<PivotState>() {
        @Override
        public PivotState convert(String s) {
            return PivotState.valueOf(s);
        }
    };

    public static StringToValue<ClimbState> kClimbState = new StringToValue<ClimbState>() {
        @Override
        public ClimbState convert(String s) {
            return ClimbState.valueOf(s);
        }
    };

    public static StringToValue<DriveState> kDriveState = new StringToValue<DriveState>() {
        @Override
        public DriveState convert(String s) {
            return DriveState.valueOf(s);
        }
    };

    public static StringToValue<ShooterValue> kShooterValue = new StringToValue<ShooterValue>() {
        @Override
        public ShooterValue convert(String s) {
            return ShooterValue.valueOf(s);
        }
    };

    public static StringToValue<IntakeValue> kIntakeValue = new StringToValue<IntakeValue>() {
        @Override
        public IntakeValue convert(String s) {
            return IntakeValue.valueOf(s);
        }
    };

    public static StringToValue<TubeValue> kTubeValue = new StringToValue<TubeValue>() {
        @Override
        public TubeValue convert(String s) {
            return TubeValue.valueOf(s);
        }
    };

    public static StringToValue<PivotMode> kPivotMode = new StringToValue<PivotMode>() {
        @Override
        public PivotMode convert(String s) {
            return PivotMode.valueOf(s);
        }
    };

    public static StringToValue<DriveMode> kDriveMode = new StringToValue<DriveMode>() {
        @Override
        public DriveMode convert(String s) {
            return DriveMode.valueOf(s);
        }
    };
}
