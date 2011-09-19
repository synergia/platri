package synergia.platri.apps.boxee

import synergia.platri._
import TUIO._

object App extends Application {
    override def createObject(tobj: TuioObject) = sym(tobj) match {
        case 112 => new UpDown(tobj)
        case 113 => new PlayPause(tobj)
        case 116 => new LeftRight(tobj)
        case _ => new Fader(tobj, Textures.Green)
    }

    override def start {
        Debug.info("Application Boxee started")
    }

    override def stop {
        Debug.info("Application Boxee stopped")
    }

    override def display {
        Textures
        clear("#eee")
        // Textures.Background.bind
        // matrix {
            // alpha(1)
            // rect(0, 0, Calibration.width, Calibration.height)
        // }
    }
}


