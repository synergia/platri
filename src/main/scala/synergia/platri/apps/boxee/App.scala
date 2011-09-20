package synergia.platri.apps.boxee

import synergia.platri._
import TUIO._
import processing.core._

object Images {
    val off = View.loadImage("off.png")
    val on = View.loadImage("on.png")
}

object App extends Application {
    import Images._

    override def createObject(tobj: TuioObject) = sym(tobj) match {
        case 113 | 0 => new UpDown(tobj)
        case 114 | 1 => new LeftRight(tobj)
        case 115 | 2 => new PlayPause(tobj)
        case _ => new Object(tobj)
    }

    override def start {
        Debug.info("Application Boxee started")
    }

    override def stop {
        Debug.info("Application Boxee stopped")
    }

    override def display {
        val x = off.width
        View.pushMatrix
        View.tint(255,0);

        View.image(off,x,x)

        View.pushMatrix
        View.rotate((Math.Pi / 2).toFloat)
        View.image(off, x, -x)
        View.popMatrix

        View.pushMatrix
        View.rotate((Math.Pi).toFloat)
        View.image(off, -2*x, -x)
        View.popMatrix

        View.pushMatrix
        View.rotate(( Math.Pi *3 /2).toFloat)
        View.image(off, -2*x, 2*x)
        View.popMatrix

        View.popMatrix
    }
}


