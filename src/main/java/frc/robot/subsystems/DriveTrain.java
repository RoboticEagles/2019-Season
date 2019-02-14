/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.*;

import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class DriveTrain extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.


  WPI_VictorSPX frontLeftDrive = RobotMap.frontLeftDrive;
  WPI_VictorSPX frontRightDrive = RobotMap.frontRightDrive;
  WPI_VictorSPX backLeftDrive = RobotMap.backLeftDrive;
  WPI_VictorSPX backRightDrive = RobotMap.backRightDrive;

  DifferentialDrive drive = RobotMap.drive;
  SpeedControllerGroup leftDrive = RobotMap.leftDrive;
  SpeedControllerGroup rightDrive = RobotMap.rightDrive;
  
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

 public void set(Joystick d, double slider) {
  //backLeftDrive.setInverted(true);
  //frontLeftDrive.setInverted(true);
  drive.arcadeDrive(d.getY() * slider, d.getX() * 0.5 * slider);
  /*frontLeftDrive.set(ControlMode.PercentOutput, -x);
  frontRightDrive.set(ControlMode.PercentOutput, x);
  backLeftDrive.set(ControlMode.PercentOutput, -x);
  backRightDrive.set(ControlMode.PercentOutput, x);*/
  }
/*
  public void out(double x) {
    victor.set(ControlMode.PercentOutput, x);
  }

  public void stop() {
    victor.set(ControlMode.Current, 0);
  }*/
}
