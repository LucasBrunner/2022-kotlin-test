package frc.robot.utility

/**
 * Add your docs here.
 */
class PriorityValue<T>(default: T) {
  private val default = default
  var value = default
    private set

  private var priority: Double = Double.NEGATIVE_INFINITY

  fun reset() {
    value = default
    priority = Double.NEGATIVE_INFINITY
    // println(value)
  }

  fun set(value: T, priority: Double) {
    // println(String.format("Value: %f, priority: %f, prev priority: %f", value, priority, this.priority))
    if (priority >= this.priority) {
      this.priority = priority
      this.value = value
    }
    // println(this.value)
  }
}
