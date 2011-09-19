package synergia.platri

class Connection(val from: Object, val to: Object) extends GFX {
    def display {
        if(Config.DEBUG){
            View.stroke(70)
            View.strokeWeight(20)
            View.line(from.x, from.y, to.x, to.y)
        }
    }

    def check(obj: Object) = obj == from || obj == to
    def check(obj1: Object, obj2: Object) = (obj1 == from && obj2 == to) || (obj2 == from && obj1 == to)
}