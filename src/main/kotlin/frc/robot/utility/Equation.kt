package frc.robot.utility

/**
 * Add your docs here.
 */
object Equation {
  fun deadzoneRamp(
    value:    Double,
    maxIn:    Double,
    maxOut:   Double,
    deadzone: Double,
  ): Double {
    val absValue = Math.abs(value)
    if (absValue < deadzone) {
      return 0.0
    } 
    val ratio = (absValue - deadzone) / (maxIn - deadzone)
    return Math.copySign(ratio * maxOut, value)
  }
  
  fun clamp(
    value: Double,
    min:   Double,
    max:   Double,
  ): Double {
    if (value > max) {
      return max
    } else if (value < min) {
      return min
    }
    return value
  }

  fun remainder(
    dividend: Double,
    divisor:  Double,
  ): Double {
    var out = dividend;
    if (sameSign(dividend, divisor)) {
      while (closerToZero(out, divisor) == divisor) {
        out -= divisor
      }
    } else {
      while (closerToZero(out, divisor) == divisor) {
        out += divisor
      }
    }
    return out
  }

  fun closerToZero(
    value1: Double, 
    value2: Double,
  ): Double {
    var value1Abs = Math.abs(value1)
    var value2Abs = Math.abs(value2)

    if (value1Abs < value2Abs) {
      return value1
    } else {
      return value2
    }
  } 

  fun sameSign(
    value1: Double, 
    value2: Double,
  ) = !((value1 >= 0.0) xor (value2 >= 0.0))
}
