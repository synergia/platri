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


class Splash(tobj: TuioObject) extends Object(tobj) with Loops {
    Manager.topGraphics += new GFX with Loops {
        var syn = true

        def display {
            View.tint(255,255)
            if(syn) View.image(Images.synergia, 0, 0)
            else View.image(Images.winko, 0, 0)
        }

        timeout(5000){
            syn = false

            timeout(5000){
                Manager.topGraphics -= this
            }
        }
    }
}

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
        View.image(off, 3*x, -5*x)
        if(step == Step.Left) View.image(on, 3*x, -5*x)
        View.popMatrix

        // right
        View.pushMatrix
        View.rotate(( Math.Pi *3 /2).toFloat)
        View.image(off, -4*x, 6*x)
        if(step == Step.Right) View.image(on, -4*x, 6*x)
        View.popMatrix
    }
}

class UpDown(tobj: TuioObject) extends StepFader(tobj, VK_UP, VK_DOWN){
    override def onRemoved {
        super.onRemoved
        SystemEvents keyStroke VK_ESCAPE
    }

    def newConnection(a: Object, b: Object) = new Connection(a, b) with Loops {
        var r = 0

        override def display {
            View.stroke(70)
            View.strokeWeight(2)
            View.line(from.x, from.y, to.x, to.y)
            View.noStroke
        }

        override def onRemoved {
            super.onRemoved

            Manager.graphics += new GFX {
                var r = 0
                val x1 = math.abs(from.x + (to.x - from.x) / 2)
                val y1 = math.abs(from.y + (to.y - to.y) / 2)

                val gf = this

                val t = new Thread {
                    var x = 0
                    override def run {
                        Debug.debug("CondLoop run")
                        while(x < 400){
                            x += 15
                            Debug.debug("CondLoop loop")
                            r = x
                            Thread.sleep(20)
                        }

                        Manager.graphics -= gf
                    }
                }
                t.start

                def display {
                    if(r > 0){
                        Debug.info("connection display")
                        View.fill(Calibration.backgroundColor-20)
                        View.ellipse(x1, y1, r, r)
                        View.fillBackground
                        View.ellipse(x1, y1, r-10, r-10)
                    }
                }
            }
        }
    }

    override def onCloseAdded(obj: Object) {
        super.onCloseAdded(obj)
        obj match {
            case o: LeftRight =>
                Debug.info("ENTER ON")
                SystemEvents keyPress VK_ENTER
                // addConnection(newConnection(this, o))
            case _ =>
        }
    }

    override def onCloseRemoved(obj: Object) {
        super.onCloseRemoved(obj)
        obj match {
            case o: LeftRight =>
                Debug.info("ENTER OFF")
                SystemEvents keyRelease VK_ENTER
                // removeConnection(o)
            case _ =>
        }
    }

    override def display {
        super.display
        import Images._

        View.tint(255, 255)

        val x = off.width

        // down
        View.image(off, 5*x, 3*x)
        if(step == Step.Right) View.image(on, 5*x, 3*x)

        // up
        View.pushMatrix
        View.rotate((Math.Pi).toFloat)
        View.image(off, -6*x, -3*x)
        if(step == Step.Left) View.image(on, -6*x, -3*x)
        View.popMatrix
    }
}

