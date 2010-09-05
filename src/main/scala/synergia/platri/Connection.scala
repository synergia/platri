package synergia.platri

abstract class Connection(val from: Object, val to: Object) extends Helpers {
	def display
		
	def check(obj: Object) = obj == from || obj == to
	def check(obj1: Object, obj2: Object) = (obj1 == from && obj2 == to) || (obj2 == from && obj1 == to)
}