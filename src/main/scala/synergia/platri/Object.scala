package synergia.platri

import scala.collection.mutable.ListBuffer
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
	
	def incomingConnections = View.manager.connections.filter(_.to == this)
	
	def outgoingConnections = View.manager.connections.filter(_.from == this)
	
	def addConnection(connection: Connection){
		View.manager.connections += connection
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
