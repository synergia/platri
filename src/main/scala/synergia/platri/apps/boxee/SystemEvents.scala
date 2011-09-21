package synergia.platri.apps.boxee

import java.awt.event.KeyEvent._
import synergia.platri._


object SystemEvents {
    val robot = new java.awt.Robot

    def keyStroke(k: Int) {
        keyPress(k)
        keyRelease(k)
    }

    def keyPress(k: Int) = if(Config.EV) robot keyPress k

    def keyRelease(k: Int) = if(Config.EV) robot keyRelease k
}
