package synergia.platri

import scala.math._
import TUIO._

trait Events {
	def onMoved { Debug.event("onMove") }
	def onCloseAdded(obj: Object) { Debug.event("onCloseAdded") }
	def onCloseRemoved(obj: Object) { Debug.event("onCloseRemoved") }
}


trait Node extends Helpers {
	// Required interface
	def source: TuioContainer
	def display {
		if(Config.DEBUG){
			withoutTextures {
				color("#000")
				text(x, y, "%s(%d)".format(this.getClass.getName, sid))
			}
		}
	}
	
	def x = (source.getX * Config.WIDTH).toInt
	
	def y = (source.getY * Config.HEIGHT).toInt
	
	def distanceTo(that: Node) = {
		val dx = x - that.x
		val dy = y - that.y
	
		sqrt(dx*dx + dy*dy).toInt
	}
	
	def angleTo(that: Node) = {
		val side = x - that.x
		val height = y - that.y
		val distance = distanceTo(that)
		var angle = (asin(side.toDouble/distance) + Pi/2);
		if(height < 0) angle = 2*Pi - angle
		
		-(angle * 180 / Pi).toInt
	}
	
	def sid = source.getSessionID
	
}