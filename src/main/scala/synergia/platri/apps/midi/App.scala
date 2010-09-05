package synergia.platri.apps.midi

import synergia.platri._
import TUIO._

object App extends Application {
	val circleTexture = texture("circle.png")
	val faderTextures = texture("fader/%d.png", 0 to 16)
	val roadTexture = texture("road.png")
	
	override def createObject(tobj: TuioObject) = sym(tobj) match {
		case 0 => new Fader(tobj)
		case 1 => new Base(tobj)
		case 2 => new Base(tobj)
		case 3 => new Base(tobj)
		case 4 => new Base(tobj)
		case _ => new Child(tobj)
	}
	
	override def createCursor(tcur: TuioCursor) = {
		new DemoCursor(tcur)
	}
	
	override def display {
		clear("#d0d0d0")
	}
}

class DemoGraphic(parent: Object, val multiply: Int) extends Graphic(parent) {
	protected var angle = 0
	
	def display {
		matrix {
			color("#fff")
			angle += multiply
			if(angle >= 360) angle = 0
			
			translate(parent.x, parent.y)
			rotate(angle)
			App.circleTexture.foreach(_.bind)
			square(Config.BASE_SIZE)
		}
	}
}

class Base(tobj: TuioObject) extends Object(tobj) {
	graphics += new DemoGraphic(this, 1)
	
	override def onCloseAdded(obj: Object) {
		if(obj.getClass == classOf[Child]) addConnection(obj)
	}
	
	override def onCloseRemoved(obj: Object) {
		removeConnection(obj)
	}
}

class Child(tobj: TuioObject) extends Object(tobj) {
	graphics += new DemoGraphic(this, 10)
}

class DemoCursor(c: TuioCursor) extends Cursor(c) {
	
	override def display {
		matrix {
			translate(x, y)
			color("#fff")
			circle(20)
		}
	}
}
