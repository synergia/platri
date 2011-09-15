package synergia.platri

import TUIO._

class Cursor(val source: TuioCursor) extends Node with Events {
    def move = onMoved
}
