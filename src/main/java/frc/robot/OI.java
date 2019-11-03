/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

  XboxController driver = new XboxController(0);

  public double driveControl()
  {
    return getTrigger(Hand.kRight) - getTrigger(Hand.kLeft);
  }

  public double getDriverLeftStickX()
  {
    return deadzone(driver.getX(Hand.kLeft),0.2);
  }

  public double getTrigger(Hand h)
  {
    return deadzone(driver.getTriggerAxis(h),0.3);
  }


  private double deadzone(double input, double deadzone)
  {
    return input < deadzone? 0.0: input;
  }
}
