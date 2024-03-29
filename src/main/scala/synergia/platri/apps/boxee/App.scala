package synergia.platri.apps.boxee

import synergia.platri._
import TUIO._
import processing.core._

object Images {
    val off = View.loadImage("off.png")
    val on = View.loadImage("on.png")
    val logo = View.loadImage("synergia.png")
    val synergia = View.loadImage("synergia_logo.png")
    val winko = View.loadImage("winko_logo.png")

}

object App extends Application {
    import Images._

    override def createObject(tobj: TuioObject) = sym(tobj) match {
        case 113 | 0 => new UpDown(tobj)
        case 114 | 1 => new LeftRight(tobj)
        case 115 | 2 => new PlayPause(tobj)
        case 111 | 4 => new Splash(tobj)
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
        View.tint(255,255)
        View.image(logo,0.5f*x,100)

        View.pushMatrix
        View.tint(255,0)

        View.image(off, 1.5f*x, 3*x) //down

        View.pushMatrix
        View.rotate((Math.Pi / 2).toFloat)
        View.image(off, 3*x, (-1.5*x).toFloat) //left
        View.popMatrix

        View.pushMatrix
        View.rotate((Math.Pi).toFloat)
        View.image(off, (-2.5*x).toFloat, -3*x)  //up
        View.popMatrix

        View.pushMatrix
        View.rotate(( Math.Pi *3 /2).toFloat)
        View.image(off, -4*x, (2.5*x).toFloat) //right
        View.popMatrix

        View.popMatrix
    }
}


