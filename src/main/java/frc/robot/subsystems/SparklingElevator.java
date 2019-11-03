/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Util.*;

import edu.wpi.first.wpilibj.command.Subsystem;

/**aa
 * Add your docs here.
 */
public class SparklingElevator extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  CANSparkMax elevatorMaster;
  CANSparkMax elevatorSlave0;
  CANSparkMax elevatorSlave1;
  CANSparkMax elevatorSlave2;

  private int holdPos;
  private int target;

  public enum Position {

    HATCH1(Constants.HATCH_1), 
    HATCH2(Constants.HATCH_2), 
    HATCH3(Constants.HATCH_3),
    CARGO1(Constants.CARGO_1),
    CARGO2(Constants.CARGO_2),
    CARGO3(Constants.CARGO_3),
    CARGOSHIP(Constants.CARGOSHIP),
    INTAKE(Constants.INTAKE),
    DEFAULT(Constants.DEFAULT),
    DROPCLAW(2320);
    

    
    private final int value;
    private Position(int value) {
       
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

  public SparklingElevator(int a, int b, int c, int d, MotorType type) {
    elevatorMaster = new CANSparkMax(a,type);
    elevatorSlave0 = new CANSparkMax(b, type);
    elevatorSlave1 = new CANSparkMax(c, type);
    elevatorSlave2 = new CANSparkMax(d, type);

    
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
