package spike

/**
 * Created by IntelliJ IDEA.
 * User: Steve
 * Date: 5/29/11
 * Time: 10:57 AM
 * To change this template use File | Settings | File Templates.
 */

class Dimension private (value:Number) {
  def pixels: String = value + "px"
  def percent: String = value + "%"
}

object Dimension {
  implicit def intToDimension(value: Int): Dimension = new Dimension(value)
  implicit def doubleToDimension(value: Double): Dimension = new Dimension(value)
}
