package frc.robot.subsystems

import edu.wpi.first.wpilibj2.command.SubsystemBase

import com.revrobotics.CANSparkMax
import com.revrobotics.CANSparkMaxLowLevel.MotorType

import frc.robot.Main
import frc.robot.utility.SlewRateLimiter
import frc.robot.utility.PriorityValue
import frc.robot.utility.Equation

class Drivetrain : SubsystemBase() {
  private val leftPower  = PriorityValue(0.0)
  private val rightPower = PriorityValue(0.0)

  private val leftLimiter  = SlewRateLimiter(0.02, -1.0, 1.0);
  private val rightLimiter = SlewRateLimiter(0.02, -1.0, 1.0);

  private val leftFront  = CANSparkMax(11, MotorType.kBrushless)
  private val leftRear   = CANSparkMax(2,  MotorType.kBrushless)
  private val rightFront = CANSparkMax(5,  MotorType.kBrushless)
  private val rightRear  = CANSparkMax(6,  MotorType.kBrushless)

  private fun setRawMotorPowers(
    left:  Double,
    right: Double,
  ) {
    leftFront .set(-left)
    leftRear  .set(-left)
    rightFront.set(right)
    rightRear .set(right)
  }

  override fun periodic() {
    setRawMotorPowers(
      leftLimiter .step(leftPower .value), 
      rightLimiter.step(rightPower.value),
    )

    leftPower .reset()
    rightPower.reset()
  }

  fun tankInput(
    left:     Double,
    right:    Double,
    priority: Double,
  ) {
    leftPower .set(left,  priority)
    rightPower.set(right, priority)
  }

  fun primitiveArcadeInput(
    drive:    Double,
    turn:     Double,
    priority: Double,
  ) {
    leftPower.set(
      drive - turn,
      priority,
    )
    rightPower.set(
      drive + turn,
      priority,
    )
  }

  /**
   * If one side is reciving more than the max power the additional power is given to the other side
   */
  fun rolloverArcadeInput(
    drive:    Double,
    turn:     Double,
    maxPower: Double,
    priority: Double,
  ) {
    var left  = drive - turn
    var right = drive + turn

    val leftOverMax  = Math.abs(left) > maxPower
    val rightOverMax = Math.abs(right) > maxPower
    if (leftOverMax xor rightOverMax) {
      if (leftOverMax) {
        right -= Equation.remainder(left, 1.0)
      } else {
        left -= Equation.remainder(right, 1.0)
      }
    }

    leftPower .set(left,  priority)
    rightPower.set(right, priority)
  }

  /**
   * Math is applied to limit how often the motors run at 100%.
   */
  fun arcadeDriveTurnThrottle(
    drive:    Double, 
    turn:     Double,
    priority: Double,
  ) {
    // I don't actually remember how this algorithm works
    var right: Double 
    var left:  Double 
    
    val maxInput = Math.copySign(Math.max(Math.abs(drive), Math.abs(turn)), drive)
    
    if (drive >= 0.0) {
      // First quadrant, else second quadrant
      if (turn <= 0.0) {
        left = maxInput
        right = drive + turn
      } else {
        left = drive - turn
        right = maxInput
      }
    } else {
      // Third quadrant, else fourth quadrant
      if (turn <= 0.0) {
        left = drive - turn
        right = maxInput
      } else {
        left = maxInput
        right = drive + turn
      }
    }
    // println("" + left + ", " + right)
    
    leftPower .set(left,  priority)
    rightPower.set(right, priority)
  }
}
