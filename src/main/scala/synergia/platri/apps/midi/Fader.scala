// package synergia.platri.apps.midi
//
// import synergia.platri._
// import TUIO.TuioObject
// import java.awt.event.KeyEvent._
//
//
//
// class Fader(tobj: TuioObject, color: OnOffTexture) extends Object(tobj) {
//     protected var calculatedAngle = defaultAngle
//
//     protected def defaultAngle = 0.0
//
//     override def onMoved {
//         calculatedAngle += rotationSpeed*10
//         if(calculatedAngle > 360) calculatedAngle = 360
//         else if(calculatedAngle < 0) calculatedAngle = 0
//         onRotated
//     }
//
//     def onRotated {}
//
//     override def angle = calculatedAngle.toInt
//
//     override def display {
//         super.display
//
//         matrix {
//             translate(x, y)
//             color("#fff")
//
//             color.on
//             square(Config.BASE_SIZE)
//
//             color.off
//             alpha(angle / 360.0)
//             square(Config.BASE_SIZE)
//         }
//     }
// }
//
//
// object Save {
//     val data = scala.collection.mutable.Map[Int, (Double, Double)]()
//
//     def apply(id: Int) = data.get(id) getOrElse (0.0, 0.0)
//
//     def set(id: Int, prev: Double, curr: Double) = {
//         data(id) = (prev, curr)
//     }
// }
//
// object Robot {
//     val robot = new java.awt.Robot
//
//     def key(k: Int) = {
//         press(k)
//         release(k)
//     }
//
//     def press(k: Int) = robot keyPress k
//
//     def release(k: Int) = robot keyRelease k
//
// }
//
// class StepFader(tobj: TuioObject, left: Int, right: Int, diff: Int = 20) extends Object(tobj) {
//
//     protected var (curr, prev) = Save(symbolID)
//
//
//     override def onMoved {
//         // R.robot.mouseMove(x, y)
//
//         curr += rotationSpeed*10
//
//         Debug.debug("prev: " + prev + "\tcurr: " + curr)
//
//         if(math.abs(curr - prev) > diff){
//             if(prev < curr) {
//                 Debug.info("step <--")
//                 Robot key left
//             } else {
//                 Debug.info("step -->")
//                 Robot key right
//             }
//
//             prev = curr;
//         }
//
//         Save.set(symbolID, prev, curr)
//     }
// }
//
// class PlayPause(tobj: TuioObject) extends StepFader(tobj, VK_MINUS, VK_EQUALS, 10
//      ){
//     playPause
//
//     override def onRemoved {
//         super.onRemoved
//         playPause
//     }
//
//     protected def playPause {
//         Robot key VK_SPACE
//     }
// }
//
// class LeftRight(tobj: TuioObject) extends StepFader(tobj, VK_LEFT, VK_RIGHT){
//
//     override def onRemoved {
//         super.onRemoved
//         Robot key VK_ESCAPE
//     }
// }
//
// class UpDown(tobj: TuioObject) extends StepFader(tobj, VK_UP, VK_DOWN){
//
//     override def onRemoved {
//         super.onRemoved
//         Robot key VK_ESCAPE
//     }
//
//     override def onCloseAdded(obj: Object) {
//         super.onCloseAdded(obj)
//         obj match {
//             case o: LeftRight => Robot press VK_ENTER
//             case _ =>
//         }
//     }
//
//
//     override def onCloseRemoved(obj: Object) {
//         super.onCloseRemoved(obj)
//         obj match {
//             case o: LeftRight => Robot release VK_ENTER
//             case _ =>
//         }
//
//     }
// }
//
