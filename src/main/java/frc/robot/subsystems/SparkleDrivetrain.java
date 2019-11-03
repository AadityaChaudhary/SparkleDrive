/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Util.Constants;
import frc.robot.Util.SparkyConfig;
import frc.robot.commands.SparkyDrive;

/**
 * Add your docs here.
 */
public class SparkleDrivetrain extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  CANSparkMax leftM;
  CANSparkMax rightM;
  CANSparkMax leftS;
  CANSparkMax rightS;

  

  public SparkleDrivetrain(int leftID, int rightID, MotorType type)
  {
    leftM = new CANSparkMax(leftID, type);
    rightM = new CANSparkMax(rightID, type);

    leftS = new CANSparkMax(leftID, type);
    rightS = new CANSparkMax(rightID, type); 

    SparkyConfig.config(leftM,rightM,leftS,rightS);

  }

  public void arcadeDrive(double speed, double turn)
  {
    leftM.set(speed + turn);
    rightM.set(speed - turn);
  }

  public void arcadeDriveWF(double speed, double turn)
  {
    double left;
      double right;

    if (turn > 0) {
        left = (speed) + ((Math.exp(Constants.TURN_WEIGHT_FACTOR * turn) * turn));
        right = (speed) + ((Math.exp(Constants.TURN_WEIGHT_FACTOR * turn) * -turn));
    } else if (turn < 0) {
        left = (speed) + ((Math.exp(Constants.TURN_WEIGHT_FACTOR * -turn) * turn));
        right = (speed) + ((Math.exp(Constants.TURN_WEIGHT_FACTOR * -turn) * -turn));
    } else {
        left = speed;
        right = speed;
    }
    
    //TalonConfigDriveTeleop.config(Robot.drivetrain.talonL, Robot.drivetrain.talonR);
    this.tankDrive(left, right);
  }

  void tankDrive(double left, double right)
  {
    leftM.set(left);
    rightM.set(right);
  }


  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    setDefaultCommand(new SparkyDrive());
  }
}
