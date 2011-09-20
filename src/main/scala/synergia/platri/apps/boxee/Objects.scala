package synergia.platri.apps.boxee

import synergia.platri._
import TUIO.TuioObject
import processing.core._
import java.awt.event.KeyEvent._


class SavedState[T](default: => T){
    val storage = scala.collection.mutable.Map[Int, T]() withDefaultValue default

    def apply(id: Int): T = storage(id)

    def update(id: Int, data: T) { storage(id) = data }
}

object SavedPrevCurr extends SavedState[(Double, Double)]((0.0, 0.0))


class StepFader(tobj: TuioObject, left: Int, right: Int, diff: Int = 20) extends Object(tobj) with Loops {
    object Step extends Enumeration {
        val None, Left, Right = Value
    }

    protected var step: Step.Value = Step.None

    protected var (curr, prev) = SavedPrevCurr(symbolID)

    override def onMoved {
        if(rotationSpeed == 0){
            prev = curr
        } else {
            curr += rotationSpeed*10

            if(math.abs(curr - prev) > diff){
                if(prev < curr) {
                    Debug.info("step <--")
                    step = Step.Left
                    SystemEvents keyStroke left
                } else {
                    Debug.info("step -->")
                    step = Step.Right
                    SystemEvents keyStroke right
                }
                timeout(200){ step = Step.None }

                prev = curr;
            }
        }

        SavedPrevCurr(symbolID) = (prev, curr)
    }

}

class PlayPause(tobj: TuioObject) extends StepFader(tobj, VK_MINUS, VK_EQUALS, 10){
    playPause

    override def onRemoved {
        super.onRemoved
        playPause
    }

    protected def playPause {
        SystemEvents keyStroke VK_SPACE
    }
}

class LeftRight(tobj: TuioObject) extends StepFader(tobj, VK_LEFT, VK_RIGHT){
    override def onRemoved {
        super.onRemoved
        SystemEvents keyStroke VK_ESCAPE
    }

    override def display {
        super.display
        import Images._
        View.tint(255, 255)

        val x = off.width

        // left
        View.pushMatrix
        View.rotate((Math.Pi / 2).toFloat)
        View.image(off, 3*x, -x)
        if(step == Step.Left) View.image(on, x, -x)
        View.popMatrix

        // right
        View.pushMatrix
        View.rotate(( Math.Pi *3 /2).toFloat)
        View.image(off, -4*x, 2*x)
        if(step == Step.Right) View.image(on, -2*x, 2*x)
        View.popMatrix
    }
}

class UpDown(tobj: TuioObject) extends StepFader(tobj, VK_UP, VK_DOWN){
    override def onRemoved {
        super.onRemoved
        SystemEvents keyStroke VK_ESCAPE
    }

    override def onCloseAdded(obj: Object) {
        super.onCloseAdded(obj)
        obj match {
            case o: LeftRight =>
                Debug.info("ENTER ON")
                SystemEvents keyPress VK_ENTER
            case _ =>
        }
    }

    override def onCloseRemoved(obj: Object) {
        super.onCloseRemoved(obj)
        obj match {
            case o: LeftRight =>
                Debug.info("ENTER OFF")
                SystemEvents keyRelease VK_ENTER
            case _ =>
        }
    }

    override def display {
        super.display
        import Images._

        View.tint(255, 255)

        val x = off.width

        // up
        View.image(off, x, x)
        if(step == Step.Left) View.image(on, x, x)

        // down
        View.pushMatrix
        View.rotate((Math.Pi).toFloat)
        View.image(off, -2*x, -x)
        if(step == Step.Right) View.image(on, -2*x, -x)
        View.popMatrix
    }
}

