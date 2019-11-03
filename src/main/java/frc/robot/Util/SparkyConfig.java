package frc.robot.Util;

import com.revrobotics.CANSparkMax;

public class SparkyConfig
{
    public static void config(CANSparkMax leftM,CANSparkMax rightM,CANSparkMax leftS,CANSparkMax rightS)
    {
        leftS.follow(leftM);
        rightS.follow(rightM);

    }
}