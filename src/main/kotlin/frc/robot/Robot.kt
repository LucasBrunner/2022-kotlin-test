package frc.robot

import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj2.command.CommandScheduler

import frc.robot.UserInput
import frc.robot.subsystems.Drivetrain
import frc.robot.utility.Equation

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
class Robot : TimedRobot() {
  val drivetrain = Drivetrain()
  
  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  override fun robotInit() {}

  override fun robotPeriodic() {}

  override fun autonomousInit() {}

  override fun autonomousPeriodic() {}

  override fun teleopInit() {}

  override fun teleopPeriodic() {
    drivetrain.primitiveArcadeInput(
      Equation.deadzoneRamp(UserInput.getDriveForward(), 1.0, 1.0, 0.2),
      Equation.deadzoneRamp(UserInput.getDriveTurn(),    1.0, 1.0, 0.2),
      0.0,
    )

    // println(Equation.deadzoneRamp(UserInput.getDriveForward(), 1.0, 1.0, 0.2))
    // println(Equation.deadzoneRamp(UserInput.getDriveTurn(),    1.0, 1.0, 0.2))

    CommandScheduler.getInstance().run()
  }

  override fun disabledInit() {}

  override fun disabledPeriodic() {}

  override fun testInit() {}

  override fun testPeriodic() {}
}
