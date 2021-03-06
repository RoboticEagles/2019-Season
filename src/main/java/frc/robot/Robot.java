/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import org.opencv.core.Mat;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSource;
import edu.wpi.first.networktables.*;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.buttons.POVButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.AutoCommand;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.IntakeSystem;
import frc.robot.subsystems.ShooterSystem;
import frc.robot.subsystems.PneumaticsSystem;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public static DriveTrain driveTrain = new DriveTrain();
  public static IntakeSystem intakeSystem = new IntakeSystem();
  public static ShooterSystem shooterSystem = new ShooterSystem();
  public static PneumaticsSystem pneumaticsSystem = new PneumaticsSystem();
  public static OI m_oi;

  DoubleSolenoid intakeSolenoid = RobotMap.intakeSolenoid;
  DoubleSolenoid hexagonSolenoid = RobotMap.lowSolenoid;
  DoubleSolenoid hatchSolenoid = RobotMap.highSolenoid;

  boolean pov;
  boolean intake;
  boolean hexagon;
  boolean hatch;

  double slider;
  double joystickY;

  NetworkTableEntry xEntry;
  NetworkTableEntry yEntry;

  Command m_autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  Compressor compressor = new Compressor(0);

  Joystick j = new Joystick(0);
  POVButton povRight = new POVButton(j, 0);
  POVButton povLeft = new POVButton(j, 180);

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    //UsbCamera camera = new UsbCamera("camera0", 0);
    //MjpegServer server = new MjpegServer("server0", 1180);
    //CameraServer.getInstance().startAutomaticCapture(camera);

    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    NetworkTable table = inst.getTable("datatable");
    xEntry = table.getEntry("X");
    yEntry = table.getEntry("Y");

    m_oi = new OI();
    m_chooser.setDefaultOption("Default Auto", new AutoCommand());
    // chooser.addOption("My Auto", new MyAutoCommand());
    SmartDashboard.putData("Auto mode", m_chooser);

    compressor.start();
    compressor.setClosedLoopControl(true);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_chooser.getSelected();

    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector",
     * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
     * = new MyAutoCommand(); break; case "Default Auto": default:
     * autonomousCommand = new ExampleCommand(); break; }
     */

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.start();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override

  public void teleopPeriodic() {
    Scheduler.getInstance().run();

    pov = povRight.get();

    slider = ((j.getThrottle() * -1) + 1) / 2;
    
    if (j.getRawButtonPressed(3)) {
      intake = !intake;
      pneumaticsSystem.intakeSet(intake);
    }

    if (j.getRawButtonPressed(4)) {
      hexagon = !hexagon;
      pneumaticsSystem.hexagonSet(hexagon);
    }

    if (j.getRawButtonPressed(5)) {
      hatch = !hatch;
      pneumaticsSystem.hatchSet(hatch);
    }

    driveTrain.set(j, slider);

    if (j.getRawButton(1)) {
      intakeSystem.set(slider);
    }

    else {
      intakeSystem.stop();
    }
    
    if (j.getRawButton(2)) {
      shooterSystem.set(slider);
    }
    else {
      shooterSystem.stop();
    }

    SmartDashboard.putNumber("slider:", slider);
    SmartDashboard.putBoolean("intake:", (intakeSolenoid.get() == Value.kForward) ? true : false);
    SmartDashboard.putBoolean("hexagon:", (hexagonSolenoid.get() == Value.kForward) ? true : false);
    SmartDashboard.putBoolean("hatch:", (hatchSolenoid.get() == Value.kForward) ? true : false);
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
