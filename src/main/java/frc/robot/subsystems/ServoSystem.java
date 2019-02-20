/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Joystick;

/**
 * Add your docs here.
 */
public class ServoSystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  Servo servoLeft = RobotMap.servoLeft;
  Servo servoRight = RobotMap.servoRight;

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void set(Joystick j) {
	if(j.getRawButton(9)){
     servoLeft.setAngle(1);
     servoRight.setAngle(0);
    } else{
      servoLeft.setAngle(0);
      servoRight.setAngle(1);
    }
  }
}
