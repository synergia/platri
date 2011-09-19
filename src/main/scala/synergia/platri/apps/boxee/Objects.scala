package synergia.platri.apps.boxee

import synergia.platri._
import TUIO.TuioObject
import java.awt.event.KeyEvent._
import processing.core._


class SavedState[T](default: => T){
    val storage = scala.collection.mutable.Map[Int, T]() withDefaultValue default

    def apply(id: Int): T = storage(id)

    def update(id: Int, data: T) { storage(id) = data }
}

object SavedPrevCurr extends SavedState[(Double, Double)]((0.0, 0.0))

class StepFader(tobj: TuioObject, left: Int, right: Int, off : PImage, diff: Int = 20) extends Object(tobj) {
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

class PlayPause(tobj: TuioObject, off : PImage, on : PImage) extends StepFader(tobj, VK_MINUS, VK_EQUALS, off, 10){
    playPause

    override def onRemoved {
        super.onRemoved
        playPause
    }

    protected def playPause {
        SystemEvents keyStroke VK_SPACE
    }
}

class LeftRight(tobj: TuioObject, off : PImage, on : PImage) extends StepFader(tobj, VK_LEFT, VK_RIGHT, off ){
    override def onRemoved {
        super.onRemoved
        SystemEvents keyStroke VK_ESCAPE
    }
    
    override def display {
       def x = off.width
       View.tint(255,255)
        View.pushMatrix
	    View.rotate((Math.Pi / 2).toFloat) 
	    View.image(off, x,-x)
        View.popMatrix
        
         View.pushMatrix
	    View.rotate(( Math.Pi *3 /2).toFloat) 
	    View.image(off,-2*x,2*x)
        View.popMatrix
    }
    
    
}

class UpDown(tobj: TuioObject, off : PImage, on : PImage) extends StepFader(tobj, VK_UP, VK_DOWN, off){
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
    
    override def display {
        def x = off.width
        View.tint(255,255)
        View.image(off,x,x)
        
        View.pushMatrix
	    View.rotate((Math.Pi).toFloat) 
	    View.image(off,-2*x,-x)
        View.popMatrix
    }
}

