package synergia.platri

import scala.collection.mutable.ListBuffer
import scala.math._
import TUIO._

abstract class Graphic(val parent: Object) extends Helpers {
	def display
}

class Object(val source: TuioObject) extends Node with Events {
	protected var oldClose = new ListBuffer[Object]()
	protected val graphics = new ListBuffer[Graphic]()
	
	def symbolID = source.getSymbolID
	
	def angle = source.getAngleDegrees
	
	def rotationSpeed = source.getRotationSpeed
	
	override def display {
		super.display
		displayGraphics
	}
	
	def move {
		onMoved
		updateCloseObjects(true)
	}
	
	def connections = View.manager.connections.filter(_.check(this))
	
	def addConnection(obj: Object){
		View.manager.addConnection(this, obj)
	}
	
	def removeConnection(obj: Object){
		View.manager.removeConnection(this, obj)
	}
	
	protected def displayGraphics {
		graphics.foreach(_.display)
	}
	
	protected def updateCloseObjects(recursive: Boolean) {
		val newClose = findCloseObjects(Config.CLOSE_OBJECT_DISTANCE)
		newClose.foreach { o =>
			if(oldClose.contains(o)) {
				oldClose -= o
			} else {
				if(recursive){
					this.onCloseAdded(o)
					o.onCloseAdded(this)
					o.updateCloseObjects(false)
				}
			}
		}
		
		if(recursive){
			oldClose.foreach { o => 
				this.onCloseRemoved(o)
				o.onCloseRemoved(this)
				o.updateCloseObjects(false)
			}
		}
		
		oldClose.clear
		oldClose.appendAll(newClose)
	}
	
	protected def findCloseObjects(range: Int) = View.manager.findCloseObjects(this, range)
}

class Cursor(val source: TuioCursor) extends Node with Events {
	def move = onMoved
}

trait Events {
	def onMoved { println("[EVENT] onMove") }
	def onCloseAdded(obj: Object) { println("[EVENT] onCloseAdded") }
	def onCloseRemoved(obj: Object) { println("[EVENT] onCloseRemoved") }
}

class Connection(val from: Object, val to: Object) extends Helpers {
	def display {
		val length = from distanceTo to
		val angle = from angleTo to
		
		matrix {
			translate(from.x, from.y)
			rotate(angle)
			
			apps.midi.App.roadTexture.foreach(_.bind)
			translate(length/2, 0)
			rect(length-50, 40)
		}
	}
	
	def check(obj: Object) = obj == from || obj == to
	def check(obj1: Object, obj2: Object) = (obj1 == from && obj2 == to) || (obj2 == from && obj1 == to)
}

trait Node extends Helpers {
	// Required interface
	def source: TuioContainer
	def display {
		withoutTextures {
			color("#000")
			text(x, y, "%s(%d)".format(this.getClass.getName, sid))
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