package frc.robot

import edu.wpi.first.wpilibj.Joystick

/**
 * Add your docs here.
 */
object UserInput {
  private val joy1 = Joystick(0)

  fun getDriveForward(): Double = joy1.getRawAxis(1)
  fun getDriveTurn():    Double = joy1.getRawAxis(4)
}
