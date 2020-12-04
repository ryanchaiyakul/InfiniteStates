package com.team2568.lib.drivers;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.team2568.frc2020.Constants;

public class TalonSRXFactory {
    public static class Config {
        public int currentLimit = Constants.kTalonSRXCurrentLimit;
        public NeutralMode neutralMode = NeutralMode.Brake;
    }

    public static WPI_TalonSRX getDefault(int port, Config config) {
        WPI_TalonSRX ret = new WPI_TalonSRX(port);
        ret.configContinuousCurrentLimit(config.currentLimit);
        ret.setNeutralMode(config.neutralMode);
        return ret;
    }

    public static WPI_TalonSRX getDefault(int port) {
        return getDefault(port, new Config());
    }
}
