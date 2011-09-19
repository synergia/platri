package synergia.platri

import TUIO._

class Cursor(val source: TuioCursor) extends Node with Events {
    def move = onMoved

    override def display {
        super.display
        if(Config.DEBUG) {
            View.fill(70)
            View.ellipse(x, y, 50, 50)
            View.fillBackground
            View.ellipse(x, y, 45, 45)
        }
    }

}
