package synergia.platri

import scala.math._
import TUIO._

trait Events {
    def onMoved { Debug.event("onMove")}
    def onRemoved { Debug.event("onRemoved") }
    def onCloseAdded(obj: Object) { Debug.event("onCloseAdded") }
    def onCloseRemoved(obj: Object) { Debug.event("onCloseRemoved") }
}


trait Node extends GFX {
    // Required interface
    def source: TuioContainer
    def display {
        if(Config.DEBUG){
            View.fill(70)
            View.text("%s(%d)".format(this.getClass.getName.split("\\.").last, sid), x+50, y+50)
        }
    }

    def x = Calibration.calculateX(source.getX)

    def y = Calibration.calculateY(source.getY)

    def distanceTo(that: Node) = {
        val dx = x - that.x
        val dy = y - that.y

        sqrt(dx*dx + dy*dy)
    }

    def angleTo(that: Node) = {
        val side = x - that.x
        val height = y - that.y
        val distance = distanceTo(that)
        var angle = (asin(side.toDouble/distance) + Pi/2);
        if(height < 0) angle = 2*Pi - angle

        -(angle * 180 / Pi)
    }

    def sid = source.getSessionID

}