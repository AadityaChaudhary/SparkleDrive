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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Util.Constants;
import frc.robot.Util.SparkyConfig;
import frc.robot.commands.SparkyDrive;

/**
 * Add your docs here.
 */
public class SparkleDrivetrain extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public CANSparkMax leftM;
  public CANSparkMax rightM;
  public CANSparkMax leftS;
  public CANSparkMax rightS;

  

  public SparkleDrivetrain(int leftID, int rightID, int leftSID, int rightSID, MotorType type)
  {
    leftM = new CANSparkMax(leftID, type);
    rightM = new CANSparkMax(rightID, type);

    leftS = new CANSparkMax(leftSID, type);
    rightS = new CANSparkMax(rightSID, type); 

    SparkyConfig.config(leftM,rightM,leftS,rightS);

  }

  public void postSpeed()
  {
    SmartDashboard.putNumber("left master", leftM.getEncoder().getVelocity());
    SmartDashboard.putNumber("right master", rightM.getEncoder().getVelocity());
  }

  public void arcadeDrive(double speed, double turn)
  {
    leftM.set(speed + turn);
    rightM.set(speed - turn);
  }

  public void arcadeDriveWF(double speed, double turn)
  {
    SmartDashboard.putNumber("turn", turn);
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
    SmartDashboard.putNumber("left", left);
    SmartDashboard.putNumber("right", right);
    leftM.set(left);
    rightM.set(right);
  }


  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    setDefaultCommand(new SparkyDrive());
  }
}
