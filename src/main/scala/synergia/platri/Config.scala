package synergia.platri

object Props {
    val * = new Properties("config.ini")
}

object Config {
    import Props._

    var DEBUG = *("debug") or true

    final val FULLSCREEN = *("fullscreen") or false
    final val FPS = 60

    def width = Calibration.displayWidth
    def height = Calibration.displayHeight

    lazy val WIDTH = if(FULLSCREEN) View.width else width
    lazy val HEIGHT = if(FULLSCREEN) View.height else height
    lazy val BASE_SIZE = width / 4

    var CLOSE_OBJECT_DISTANCE = Calibration.closeObjectDistance

    def toggleDebug { DEBUG = !DEBUG }
}

object Calibration extends GFX {
    import Props._

    var offsetX = *("calibration.offsetX") or 0
    var offsetY = *("calibration.offsetY") or 0
    var displayWidth   = *("calibration.displayWidth") or 400
    var displayHeight  = *("calibration.displayHeight") or 300
    var closeObjectDistance  = *("calibration.closeObjectDistance") or 100

    var enabled = *("calibration.enabled") or true

    def toggle { enabled = !enabled }

    def key(ch: Char) {
        ch match {
            case 'c' => toggle
            case c =>
                if(enabled){
                    ch match {
                        // move
                        case 'i' =>
                            offsetY -= 1
                            *("calibration.offsetY") = offsetY
                            *.save
                        case 'k' =>
                            offsetY += 1
                            *("calibration.offsetY") = offsetY
                            *.save
                        case 'j' =>
                            offsetX -= 1
                            *("calibration.offsetX") = offsetX
                            *.save
                        case 'l' =>
                            offsetX += 1
                            *("calibration.offsetX") = offsetX
                            *.save

                        // resize
                        case 'w' =>
                            displayHeight -= 1
                            *("calibration.displayHeight") = displayHeight
                            *.save
                        case 's' =>
                            displayHeight += 1
                            *("calibration.displayHeight") = displayHeight
                            *.save
                        case 'a' =>
                            displayWidth -= 1
                            *("calibration.displayWidth") = displayWidth
                            *.save
                        case 'd' =>
                            displayWidth += 1
                            *("calibration.displayWidth") = displayWidth
                            *.save

                        // change close object distance
                        case ']' =>
                            closeObjectDistance += 1
                            *("calibration.closeObjectDistance") = closeObjectDistance
                            *.save

                        case '[' =>
                            closeObjectDistance -= 1
                            *("calibration.closeObjectDistance") = closeObjectDistance
                            *.save


                        case c => Debug.info("[key] " + c)
                    }
                } else {
                    Debug.info("[key] " + c)
                }
        }
    }

    def display {
        if(enabled){
            import View._

            val w = 100
            val h = 30

            fill(58, 253, 255)
            noStroke

            rect(offsetX, offsetY, w, h)
            rect(offsetX, offsetY, h, w)
            rect(offsetX+displayWidth-w, offsetY, w, h)
            rect(offsetX+displayWidth-h, offsetY, h, w)
            rect(offsetX, offsetY+displayHeight-h, w, h)
            rect(offsetX, offsetY+displayHeight-w, h, w)
            rect(offsetX+displayWidth-w, offsetY+displayHeight-h, w, h)
            rect(offsetX+displayWidth-h, offsetY+displayHeight-w, h, w)


            rect(offsetX + displayWidth / 2 - 100, offsetY + displayHeight / 2 - 50, 200, 100)

            fill(0)
            text("Size:   %d x %d".format(displayWidth, displayHeight),     offsetX + displayWidth / 2 - 50, offsetY + displayHeight / 2 - 10)
            text("Offset: (%d, %d)".format(offsetX, offsetY), offsetX + displayWidth / 2 - 50, offsetY + displayHeight / 2 + 10)
        }
    }
}