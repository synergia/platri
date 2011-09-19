package synergia.platri.apps.test

import synergia.platri._
import TUIO._


class TestObject(tobj: TuioObject) extends Object(tobj) with GFX {

    override def onCloseAdded(obj: Object) = addConnection(new Connection(this, obj))

    override def onCloseRemoved(obj: Object)  = removeConnection(obj)
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
