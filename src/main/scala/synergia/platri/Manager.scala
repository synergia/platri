package synergia.platri

import scala.collection.mutable.{ListBuffer, HashMap}
import TUIO._

object Manager extends TuioListener with GFX {
    protected var application: Option[Application] = None

    val tuio = new TuioClient
    tuio.addTuioListener(this)
    tuio.connect

    View // init the object

    val objects = new HashMap[TuioObject, Object]
    val cursors = new HashMap[TuioCursor, Cursor]
    val connections = new ListBuffer[Connection]
    val graphics = new ListBuffer[GFX]
    val topGraphics = new ListBuffer[GFX]

    def run(app: Application){
        application = Some(app)
        app.start
    }

    def stop {
        application.foreach(_.stop)
    }

    def display {
        Calibration.display
        Debug.display
        application.foreach(_.display)
        (graphics ++ connections ++ objects.values ++ cursors.values ++ topGraphics).foreach(_.display)
    }

    def removeConnection(from: Object, to: Object){
        connections.filter(_.check(from, to)).foreach { conn =>
            conn.onRemoved
            connections -= conn
        }
    }

    def addTuioObject(tobj: TuioObject) {
        Debug.info("app: " + application)

        application.foreach { app =>
            val obj = app.createObject(tobj)
            obj.move
            objects(tobj) = obj
        }
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
        application.foreach { app =>
            cursors(tcur) = app.createCursor(tcur)
        }
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

object Main {
    def main(args: Array[String]) {
        Manager.run(apps.boxee.App)
    }
}
