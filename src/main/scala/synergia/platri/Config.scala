package synergia.platri

object Props {
    val * = new Properties("config.ini")
}

object Config {
    import Props._

    var DEBUG = *("debug") or true

    final val FULLSCREEN = *("fullscreen") or false
    final val FPS = 60

    def width = Calibration.width
    def height = Calibration.height

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
    var width   = *("calibration.width") or 400
    var height  = *("calibration.height") or 300
    var closeObjectDistance  = *("calibration.closeObjectDistance") or 100

    var enabled = *("calibration.enabled") or true

    def toggle { enabled = !enabled }

    def key(ch: Char) {
        if(enabled){
            ch match {
                // move
                case 'i' =>
                    offsetY -= 1
                    *("calibration.offsetY") = offsetY.toString
                    *.save
                case 'k' =>
                    offsetY += 1
                    *("calibration.offsetY") = offsetY.toString
                    *.save
                case 'j' =>
                    offsetX -= 1
                    *("calibration.offsetX") = offsetX.toString
                    *.save
                case 'l' =>
                    offsetX += 1
                    *("calibration.offsetX") = offsetX.toString
                    *.save

                // resize
                case 'w' =>
                    height -= 1
                    *("calibration.height") = height.toString
                    *.save
                case 's' =>
                    height += 1
                    *("calibration.height") = height.toString
                    *.save
                case 'a' =>
                    width -= 1
                    *("calibration.width") = width.toString
                    *.save
                case 'd' =>
                    width += 1
                    *("calibration.width") = width.toString
                    *.save

                // change close object distance
                case ']' =>
                    closeObjectDistance += 1
                    *("calibration.closeObjectDistance") = closeObjectDistance.toString
                    *.save

                case '[' =>
                    closeObjectDistance -= 1
                    *("calibration.closeObjectDistance") = closeObjectDistance.toString
                    *.save


                case c => Debug.info("[key] " + c)
            }
        }

        ch match {
            case 'c' => toggle
            case c => Debug.info("[key] " + c)
        }
    }

    def display {
        val size = (100, 30)

        // matrix {
        //     // corners
        //     color("#3afdff")
        //
        //     // top left
        //     translateAnd(offsetX, offsetY){
        //         quad {
        //             // horizontal
        //             vec(0, 0)
        //             vec(0, size._2)
        //             vec(size._1, size._2)
        //             vec(size._1, 0)
        //
        //             // vertical
        //             vec(0, 0)
        //             vec(0, size._1)
        //             vec(size._2, size._1)
        //             vec(size._2, 0)
        //         }
        //     }
        //
        //     // bottom left
        //     translateAnd(offsetX, offsetY+height){
        //         quad {
        //             // horizontal
        //             vec(0, 0)
        //             vec(0, -size._2)
        //             vec(size._1, -size._2)
        //             vec(size._1, 0)
        //
        //             // vertical
        //             vec(0, 0)
        //             vec(0, -size._1)
        //             vec(size._2, -size._1)
        //             vec(size._2, 0)
        //         }
        //     }
        //
        //     // bottom right
        //     translateAnd(offsetX+width, offsetY+height){
        //         quad {
        //             // horizontal
        //             vec(0, 0)
        //             vec(0, -size._2)
        //             vec(-size._1, -size._2)
        //             vec(-size._1, 0)
        //
        //             // vertical
        //             vec(0, 0)
        //             vec(0, -size._1)
        //             vec(-size._2, -size._1)
        //             vec(-size._2, 0)
        //         }
        //     }
        //
        //     // top right
        //     translateAnd(offsetX+width, offsetY){
        //         quad {
        //             // horizontal
        //             vec(0, 0)
        //             vec(0, size._2)
        //             vec(-size._1, size._2)
        //             vec(-size._1, 0)
        //
        //             // vertical
        //             vec(0, 0)
        //             vec(0, size._1)
        //             vec(-size._2, size._1)
        //             vec(-size._2, 0)
        //         }
        //     }
        //
        // }
        //
        // // center window
        // matrix {
        //     translate(offsetX + width / 2, offsetY + height / 2)
        //     color("#3afdff")
        //     quad {
        //         vec(-100, -50)
        //         vec(-100,  50)
        //         vec( 100,  50)
        //         vec( 100, -50)
        //     }
        //
        //     color("#000")
        //     text(-50, -20, "Size: %d x %d".format(width, height), false)
        //     text(-50, 20, "Offset: (%d, %d)".format(offsetX, offsetY), false)
        // }
    }
}