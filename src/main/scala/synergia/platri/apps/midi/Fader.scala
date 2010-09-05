package synergia.platri.apps.midi

import synergia.platri._
import TUIO.TuioObject


class Fader(tobj: TuioObject) extends Object(tobj) {
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
			
			val n = (angle/22.5).floor
			val a = ((angle - (n*22.5)) * 0.9 / 22.5) + 0.1
			
			App.faderTextures.zipWithIndex.foreach { case(tex, i) =>
				tex.foreach(_.bind)
				if(i == n+1) alpha(a)
				else if(i > n+1) alpha(0.1)
				
				square(Config.BASE_SIZE)
			}
		}
	}
}
