package frc.robot.utility

/**
 * Add your docs here.
 */
class SlewRateLimiter(
  val rate: Double,
  val min:  Double,
  val max:  Double,
  default:  Double = 0.0,
) {
  var value = default
    private set

  fun step(
    value: Double,
    limitWhileMovingTowardsZero: Boolean = false,
  ): Double {
    val difference = value - this.value
    if (Math.abs(difference) > rate) {
      if (
        (difference > 0 && this.value > 0) 
        || (difference < 0 && this.value < 0) 
        || limitWhileMovingTowardsZero
      ) {
        this.value += Math.copySign(rate, difference)
      } else {
        this.value = value 
      }
    } else {
      this.value = value 
    }
    this.value = Equation.clamp(this.value, min, max)
    return this.value
  }
}
