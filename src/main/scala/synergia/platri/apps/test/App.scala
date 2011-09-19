package synergia.platri.apps.test

import synergia.platri._
import TUIO._

class TestObject(tobj: TuioObject) extends Object(tobj) with GFX {
    override def display {
        super.display
        View.rect(30, 30, 55, 55)
    }
}

object App extends Application {
    override def createObject(tobj: TuioObject) = sym(tobj) match {
        case _ => new TestObject(tobj)
    }

    override def start {
        Debug.info("Application Test started")
    }

    override def stop {
        Debug.info("Application Test stopped")
    }
}
