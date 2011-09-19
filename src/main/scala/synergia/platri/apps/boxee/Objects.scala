package synergia.platri.apps.boxee

import synergia.platri._
import TUIO.TuioObject
import java.awt.event.KeyEvent._


class SavedState[T](default: => T){
    val storage = scala.collection.mutable.Map[Int, T]() withDefault default

    def apply(id: Int) = storage(id)

    def update(id: Int, data: T) { storage(id) = data }
}

object SavedPrevCurr extends SavedState[(Double, Double)]((0.0, 0.0))

class StepFader(tobj: TuioObject, left: Int, right: Int, diff: Int = 20) extends Object(tobj) {
    protected var (curr, prev) = SavedPrevCurr(symbolID)

    override def onMoved {
        curr += rotationSpeed*10

        if(math.abs(curr - prev) > diff){
            if(prev < curr) {
                Debug.info("step <--")
                SystemEvents keyStroke left
            } else {
                Debug.info("step -->")
                SystemEvents keyStroke right
            }

            prev = curr;
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
}

class UpDown(tobj: TuioObject) extends StepFader(tobj, VK_UP, VK_DOWN){
    override def onRemoved {
        super.onRemoved
        SystemEvents keyStroke VK_ESCAPE
    }

    override def onCloseAdded(obj: Object) {
        super.onCloseAdded(obj)
        obj match {
            case o: LeftRight => SystemEvents keyPress VK_ENTER
            case _ =>
        }
    }

    override def onCloseRemoved(obj: Object) {
        super.onCloseRemoved(obj)
        obj match {
            case o: LeftRight => SystemEvents keyPress VK_ENTER
            case _ =>
        }
    }
}

