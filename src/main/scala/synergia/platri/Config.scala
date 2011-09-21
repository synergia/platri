package synergia.platri

object Props {
    val * = new Properties("config.ini")
}

object Config {
    import Props._

    var DEBUG = *("debug") or true
    var EV = *("ev") or false

    final val FULLSCREEN = *("fullscreen") or false
    final val FPS = 60

    def width = Calibration.displayWidth
    def height = Calibration.displayHeight

    lazy val WIDTH = if(FULLSCREEN) View.width else width
    lazy val HEIGHT = if(FULLSCREEN) View.height else height
    lazy val BASE_SIZE = width / 4

    def CLOSE_OBJECT_DISTANCE = Calibration.closeObjectDistance

    def toggleDebug { DEBUG = !DEBUG }

    def toggleEv { EV = !EV }
}

object Calibration extends GFX {
    import Props._

    var offsetX = *("calibration.offsetX") or 0
    var offsetY = *("calibration.offsetY") or 0
    var displayWidth   = *("calibration.displayWidth") or 400
    var displayHeight  = *("calibration.displayHeight") or 300
    var closeObjectDistance  = *("calibration.closeObjectDistance") or 100
    var backgroundColor = *("calibration.backgroundColor") or 130

    var enabled = *("calibration.enabled") or true

    def toggle { enabled = !enabled }

    def key(ch: Char) {
        val amount = if(ch.isUpperCase) 10 else 1

        ch match {
            case 'c' => toggle

            case 'q' => Config.toggleEv

            // change backgroud color
            case '1' =>
                backgroundColor -= 10
                backgroundColor = math.max(0, math.min(backgroundColor, 255))
                *("calibration.backgroundColor") = backgroundColor
                *.save

            case '2' =>
                backgroundColor += 10
                backgroundColor = math.max(0, math.min(backgroundColor, 255))
                *("calibration.backgroundColor") = backgroundColor
                *.save

            case c =>
                if(enabled){
                    ch.toLowerCase match {
                        // move
                        case 'i' =>
                            offsetY -= amount
                            *("calibration.offsetY") = offsetY
                            *.save
                        case 'k' =>
                            offsetY += amount
                            *("calibration.offsetY") = offsetY
                            *.save
                        case 'j' =>
                            offsetX -= amount
                            *("calibration.offsetX") = offsetX
                            *.save
                        case 'l' =>
                            offsetX += amount
                            *("calibration.offsetX") = offsetX
                            *.save

                        // resize
                        case 'w' =>
                            displayHeight -= amount
                            *("calibration.displayHeight") = displayHeight
                            *.save
                        case 's' =>
                            displayHeight += amount
                            *("calibration.displayHeight") = displayHeight
                            *.save
                        case 'a' =>
                            displayWidth -= amount
                            *("calibration.displayWidth") = displayWidth
                            *.save
                        case 'd' =>
                            displayWidth += amount
                            *("calibration.displayWidth") = displayWidth
                            *.save

                        // change close object distance
                        case ']' =>
                            closeObjectDistance += amount
                            *("calibration.closeObjectDistance") = closeObjectDistance
                            *.save

                        case '[' =>
                            closeObjectDistance -= amount
                            *("calibration.closeObjectDistance") = closeObjectDistance
                            *.save


                        case c =>

                            Debug.info("[key] " + c)
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
            text("Size:   %d x %d".format(displayWidth, displayHeight),   offsetX + displayWidth / 2 - 80, offsetY + displayHeight / 2 - 20)
            text("Offset: (%d, %d)".format(offsetX, offsetY),             offsetX + displayWidth / 2 - 80, offsetY + displayHeight / 2)
            text("Close object distance: %d".format(closeObjectDistance), offsetX + displayWidth / 2 - 80, offsetY + displayHeight / 2 + 20)

        }
    }

    def calculateX(x: Double) = (offsetX + displayWidth * x).toInt

    def calculateY(y: Double) = (offsetY + displayHeight * y).toInt
}