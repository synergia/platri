package synergia.platri

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