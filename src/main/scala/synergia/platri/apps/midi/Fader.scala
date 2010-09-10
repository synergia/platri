package synergia.platri.apps.midi

import synergia.platri._
import TUIO.TuioObject

class Fader(tobj: TuioObject, color: OnOffTexture) extends Object(tobj) {
	protected var calculatedAngle = 0.0
	
	override def onMoved {
		calculatedAngle += rotationSpeed*10
		if(calculatedAngle > 360) calculatedAngle = 360
		else if(calculatedAngle < 0) calculatedAngle = 0
	}
	
	override def angle = calculatedAngle.toInt
	
	override def display {
		super.display
		
		matrix {
			translate(x, y)
			color("#fff")
			
			color.on
			square(Config.BASE_SIZE)
			
			color.off
			alpha(angle / 360.0)
			square(Config.BASE_SIZE)
		}
	}
}
