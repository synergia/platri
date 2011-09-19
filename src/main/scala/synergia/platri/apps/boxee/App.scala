package synergia.platri.apps.boxee

import synergia.platri._
import TUIO._

object App extends Application {
    val off = View.loadImage("off.png")
    val on = View.loadImage("on.png")
    
    override def createObject(tobj: TuioObject) = sym(tobj) match {
        case 33 => new UpDown(tobj, off, on)
        case 113 => new PlayPause(tobj, off, on)
        case 116 => new LeftRight(tobj, off, on)
        case _ => new LeftRight(tobj, off, on)
    }

    override def start {
        Debug.info("Application Boxee started")
    }

    override def stop {
        Debug.info("Application Boxee stopped")
    }

    override def display {
        // Textures
        // clear("#eee")
        // Textures.Background.bind
        // matrix {
            // alpha(1)
            // rect(0, 0, Calibration.width, Calibration.height)
        // }
	
	
    
    val x = off.width
	View.pushMatrix
	View.tint(255,0);
	
	View.image(off,x,x)
    
    View.pushMatrix
	View.rotate((Math.Pi / 2).toFloat) 
	View.image(off, x,-x)
    View.popMatrix
    
    View.pushMatrix
	View.rotate((Math.Pi).toFloat) 
	View.image(off,-2*x,-x)
    View.popMatrix
    
    View.pushMatrix
	View.rotate(( Math.Pi *3 /2).toFloat) 
	View.image(off,-2*x,2*x)
    View.popMatrix
    
    View.popMatrix
    }
}


