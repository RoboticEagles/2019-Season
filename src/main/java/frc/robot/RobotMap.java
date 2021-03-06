/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
  // For example to map the left and right motors, you could define the
  // following variables to use with your drivetrain subsystem.
  // public static int leftMotor = 1;
  // public static int rightMotor = 2;

  public static Servo servoLeft = new Servo(0);
  public static Servo servoRight = new Servo(1);

  public static WPI_VictorSPX intakeMotor = new WPI_VictorSPX(8);

  public static WPI_VictorSPX shooterRightMotor = new WPI_VictorSPX(2);
  public static WPI_VictorSPX shooterLeftMotor = new WPI_VictorSPX(9);

  public static WPI_VictorSPX frontLeftDrive = new WPI_VictorSPX(7);
  public static WPI_VictorSPX frontRightDrive = new WPI_VictorSPX(5);
  public static WPI_VictorSPX backLeftDrive = new WPI_VictorSPX(4);
  public static WPI_VictorSPX backRightDrive = new WPI_VictorSPX(6);

  public static SpeedControllerGroup leftDrive = new SpeedControllerGroup(frontLeftDrive, backLeftDrive);
  public static SpeedControllerGroup rightDrive = new SpeedControllerGroup(frontRightDrive, backRightDrive);
  public static DifferentialDrive drive = new DifferentialDrive(leftDrive, rightDrive);

  public static DoubleSolenoid intakeSolenoid = new DoubleSolenoid(0, 1);
  public static DoubleSolenoid lowSolenoid = new DoubleSolenoid(2, 3);
  public static DoubleSolenoid highSolenoid = new DoubleSolenoid(6, 7);

  // If you are using multiple modules, make sure to define both the port
  // number and the module. For example you with a rangefinder:
  // public static int rangefinderPort = 1;
  // public static int rangefinderModule = 1;
}
