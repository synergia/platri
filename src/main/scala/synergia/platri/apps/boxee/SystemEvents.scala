package synergia.platri.apps.boxee

import java.awt.event.KeyEvent._

object SystemEvents {
    val robot = new java.awt.Robot

    def keyStroke(k: Int) {
        keyPress(k)
        keyRelease(k)
    }

    def keyPress(k: Int) = robot keyPress k

    def keyRelease(k: Int) = robot keyRelease k
}
