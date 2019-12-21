package frc.robot.Util;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;

public class SparkyConfig
{
    public static void config(CANSparkMax leftM,CANSparkMax rightM,CANSparkMax leftS,CANSparkMax rightS)
    {

        // restoring defaults
        leftM.restoreFactoryDefaults();
        rightM.restoreFactoryDefaults();
        leftS.restoreFactoryDefaults();
        rightS.restoreFactoryDefaults();

        rightM.setInverted(true);
        rightS.setInverted(true);

        rightM.setIdleMode(IdleMode.kCoast);
        leftM.setIdleMode(IdleMode.kCoast);
        // rightS.setIdleMode(IdleMode.kCoast);
        // leftS.setIdleMode(IdleMode.kCoast);

        rightM.setOpenLoopRampRate(0.5);
        leftM.setOpenLoopRampRate(0.5);
        rightS.setOpenLoopRampRate(0.5);
        leftS.setOpenLoopRampRate(0.5);

        // setting slaves to follow
        leftS.follow(leftM);
        rightS.follow(rightM);

    }
}