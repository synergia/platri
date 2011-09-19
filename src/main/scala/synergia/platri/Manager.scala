package synergia.platri

import scala.collection.mutable.{ListBuffer, HashMap}
import TUIO._

class Manager(val app: Application) extends TuioListener with GFX {
    val tuio = new TuioClient
    tuio.addTuioListener(this)

    app.start

    tuio.connect

    val objects = new HashMap[TuioObject, Object]
    val cursors = new HashMap[TuioCursor, Cursor]
    val connections = new ListBuffer[Connection]

    def stop {
        app.stop
    }

    def display(view: View) {
        // app.display

        (objects.values ++ cursors.values ++ connections).collect {
            case x: GFX => x.display(view)
        }
    }

    def removeConnection(from: Object, to: Object){
        connections.filter(_.check(from, to)).foreach( connections -= _ )
    }

    def addTuioObject(tobj: TuioObject) {
        val obj = app.createObject(tobj)
        obj.move
        objects(tobj) = obj
    }

    def updateTuioObject(tobj: TuioObject) {
        objects(tobj).move
    }

    def removeTuioObject(tobj: TuioObject) {
        val obj = objects(tobj)
        obj.remove
        connections.filter(_.check(obj)).foreach(connections -= _)
        objects -= tobj
    }

    def addTuioCursor(tcur: TuioCursor) {
        cursors(tcur) = app.createCursor(tcur)
    }

    def updateTuioCursor(tcur: TuioCursor) {
        cursors(tcur).move
    }

    def removeTuioCursor(tcur: TuioCursor) {
        cursors -= tcur
    }

    def refresh(ftime: TuioTime) {

    }

    def findCloseObjects(node: Node, range: Int) = objects.values.filter(o => o != node && node.distanceTo(o) <= range)

}
