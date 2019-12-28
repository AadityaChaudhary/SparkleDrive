package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

/**
 * Before Running:
 * Open shuffleBoard, select File->Load Layout and select the 
 * shuffleboard.json that is in the root directory of this example
 */

/**
 * REV Smart Motion Guide
 * 
 * The SPARK MAX includes a new control mode, REV Smart Motion which is used to 
 * control the position of the motor, and includes a max velocity and max 
 * acceleration parameter to ensure the motor moves in a smooth and predictable 
 * way. This is done by generating a motion profile on the fly in SPARK MAX and 
 * controlling the velocity of the motor to follow this profile.
 * 
 * Since REV Smart Motion uses the velocity to track a profile, there are only 
 * two steps required to configure this mode:
 *    1) Tune a velocity PID loop for the mechanism
 *    2) Configure the smart motion parameters
 * 
 * Tuning the Velocity PID Loop
 * 
 * The most important part of tuning any closed loop control such as the velocity 
 * PID, is to graph the inputs and outputs to understand exactly what is happening. 
 * For tuning the Velocity PID loop, at a minimum we recommend graphing:
 *
 *    1) The velocity of the mechanism (‘Process variable’)
 *    2) The commanded velocity value (‘Setpoint’)
 *    3) The applied output
 *
 * This example will use ShuffleBoard to graph the above parameters. Make sure to
 * load the shuffleboard.json file in the root of this directory to get the full
 * effect of the GUI layout.
 */
public class Robot extends TimedRobot {
 // private SparkleDrivetrain sparky;
  private CANSparkMax m_motor, m_motorL;
  private CANSparkMax mtrSlave, mtrSlaveL;
  private CANPIDController m_pidController, m_pidControllerL;
  private CANEncoder m_encoder, m_encoderL;
  public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM, maxVel, minVel, maxAcc, allowedErr, allowedErrL;

  @Override
  public void robotInit() { 

    minVel = 0;
    // initialize motor
    m_motor = new CANSparkMax(3, MotorType.kBrushless);
    mtrSlave = new CANSparkMax(4,MotorType.kBrushless);
    m_motorL = new CANSparkMax(1, MotorType.kBrushless);
    mtrSlaveL = new CANSparkMax(2, MotorType.kBrushless);

    m_motorL.setInverted(true);
    mtrSlaveL.setInverted(true);

    /**
     * The RestoreFactoryDefaults method can be used to reset the configuration parameters
     * in the SPARK MAX to their factory default state. If no argument is passed, these
     * parameters will not persist between power cycles
     */
    // m_motor.restoreFactoryDefaults();
    // m_motorL.restoreFactoryDefaults();

    // setting motors to brake mode
    m_motor.setIdleMode(IdleMode.kBrake);
    mtrSlave.setIdleMode(IdleMode.kBrake);

    m_motorL.setIdleMode(IdleMode.kBrake);
    mtrSlaveL.setIdleMode(IdleMode.kBrake);
    

    // initialze PID controller and encoder objects
    m_pidControllerL = m_motorL.getPIDController();
    m_encoderL = m_motorL.getEncoder();
    m_pidController = m_motor.getPIDController();
    m_encoder = m_motor.getEncoder();

    // PID coefficients
    kP = 5e-5; 
    kI = 1e-6;
    kD = 0; 
    kIz = 0; 
    kFF = 0.000156; 
    kMaxOutput = 1; 
    kMinOutput = -1;
    maxRPM = 5700;

    allowedErr = 1;

    // ignore this value
    // allowedErrL = 5;

    // Smart Motion Coefficients
    maxVel = 7500; // rpm
    maxAcc = 5000;

    m_pidController.setP(0.00005);
    m_pidController.setI(0.00000165);
    m_pidController.setD(0.00044);
    m_pidController.setIZone(kIz);
    m_pidController.setFF(0.00045);
    m_pidController.setOutputRange(kMinOutput, kMaxOutput);

    m_pidControllerL.setP(0.00005);
    m_pidControllerL.setI(0.00000165);
    m_pidControllerL.setD(0.00044);
    m_pidControllerL.setIZone(kIz);
    m_pidControllerL.setFF(0.00045);
    m_pidControllerL.setOutputRange(kMinOutput, kMaxOutput);

   
    int smartMotionSlot = 0;
    m_pidController.setSmartMotionMaxVelocity(maxVel, smartMotionSlot);
    m_pidController.setSmartMotionMinOutputVelocity(minVel, smartMotionSlot);
    m_pidController.setSmartMotionMaxAccel(maxAcc, smartMotionSlot);
    m_pidController.setSmartMotionAllowedClosedLoopError(allowedErr, smartMotionSlot);

    m_pidControllerL.setSmartMotionMaxVelocity(maxVel, smartMotionSlot);
    m_pidControllerL.setSmartMotionMinOutputVelocity(minVel, smartMotionSlot);
    m_pidControllerL.setSmartMotionMaxAccel(maxAcc, smartMotionSlot);
    m_pidControllerL.setSmartMotionAllowedClosedLoopError(allowedErr, smartMotionSlot);
    // display PID coefficients on SmartDashboard
    SmartDashboard.putNumber("P Gain", kP);
    SmartDashboard.putNumber("I Gain", kI);
    SmartDashboard.putNumber("D Gain", kD);
    SmartDashboard.putNumber("I Zone", kIz);
    SmartDashboard.putNumber("Feed Forward", kFF);
    SmartDashboard.putNumber("Max Output", kMaxOutput);
    SmartDashboard.putNumber("Min Output", kMinOutput);
    // PID part 2
    SmartDashboard.putNumber("Set Position2", 0);

    // display Smart Motion coefficients
    SmartDashboard.putNumber("Max Velocity", maxVel);
    SmartDashboard.putNumber("Min Velocity", minVel);
    SmartDashboard.putNumber("Max Acceleration", maxAcc);
    SmartDashboard.putNumber("Allowed Closed Loop Error", allowedErr);
    SmartDashboard.putNumber("Set Position", 0);
    SmartDashboard.putNumber("Set Velocity", 0);

    


    // button to toggle between velocity and smart motion modes
    SmartDashboard.putBoolean("Mode", true);
    mtrSlave.follow(m_motor);
    mtrSlaveL.follow(m_motorL);
  }

  public void robotPeriodic() {
    SmartDashboard.putNumber("encoder val", m_encoder.getPosition()) ;
    SmartDashboard.putNumber("other encoder val", m_encoderL.getPosition());
  }

  @Override
  public void teleopPeriodic() {
    // read PID coefficients from SmartDashboard
    
    SmartDashboard.putNumber("velocity", m_encoder.getVelocity());

   
    // if PID coefficients on SmartDashboard have changed, write new values to controller

    

    // if((p != kP)) { m_pidController.setP(0.00005); kP = p; }
    // if((i != kI)) { m_pidController.setI(i); kI = i; }
    // if((d != kD)) { m_pidController.setD(d); kD = d; }
    // if((iz != kIz)) { m_pidController.setIZone(iz); kIz = iz; }
    // if((ff != kFF)) { m_pidController.setFF(ff); kFF = ff; }
    // if((max != kMaxOutput) || (min != kMinOutput)) { 
    //   m_pidController.setOutputRange(min, max); 
    //   kMinOutput = min; kMaxOutput = max; 
    // }
    // if((maxV != maxVel)) { m_pidController.setSmartMotionMaxVelocity(maxV,0); maxVel = maxV; }
    // if((minV != minVel)) { m_pidController.setSmartMotionMinOutputVelocity(minV,0); minVel = minV; }
    // if((maxA != maxAcc)) { m_pidController.setSmartMotionMaxAccel(maxA,0); maxAcc = maxA; }
    // if((allE != allowedErr)) { m_pidController.setSmartMotionAllowedClosedLoopError(allE,0); allowedErr = allE; }
    double setPoint, setPoint2, processVariable;
    boolean mode = SmartDashboard.getBoolean("Mode", false);
    if(false) {
      SmartDashboard.putBoolean("velocity", true);
      setPoint = SmartDashboard.getNumber("Set Velocity", 0);
      m_pidController.setReference(setPoint, ControlType.kVelocity);
      processVariable = m_encoder.getVelocity();
    } else {
      SmartDashboard.putBoolean("velocity", false);
      setPoint = SmartDashboard.getNumber("Set Position", 0);
      setPoint2 = SmartDashboard.getNumber("Set Position2", 0);
      /**
       * As with other PID modes, Smart Motion is set by calling the
       * setReference method on an existing pid object and setting
       * the control type to kSmartMotion
       */
      m_pidController.setReference(setPoint, ControlType.kSmartMotion);
      m_pidControllerL.setReference(setPoint2, ControlType.kSmartMotion);
      processVariable = m_encoder.getPosition();
      SmartDashboard.putNumber("error", setPoint - processVariable);
    }
    
    SmartDashboard.putNumber("SetPoint", setPoint);
    SmartDashboard.putNumber("Process Variable", processVariable);
    SmartDashboard.putNumber("Output", m_motor.getAppliedOutput());
  }
}