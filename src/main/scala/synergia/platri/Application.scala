package synergia.platri

import TUIO._

abstract class Application extends Helpers {
    def createObject(tobj: TuioObject): Object = new Object(tobj)
    
    def createCursor(tcur: TuioCursor): Cursor = new Cursor(tcur)
    
    def display {}
    
    def start { Debug.info("Application START") }
    
    def stop { Debug.info("Application STOP") }
    
    protected def sym(tobj: TuioObject) = tobj.getSymbolID
}
