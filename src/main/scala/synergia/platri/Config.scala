package synergia.platri

object Config {
    var DEBUG = true
    final val FULLSCREEN = Properties("fullscreen").toBoolean
    final val FPS = 60
    final val width = Calibration.width
    final val height = Calibration.height

    lazy val WIDTH = if(FULLSCREEN) View.width else width
    lazy val HEIGHT = if(FULLSCREEN) View.height else height
    lazy val BASE_SIZE = width / 4

    var CLOSE_OBJECT_DISTANCE = Calibration.closeObjectDistance

    def toggleDebug { DEBUG = !DEBUG }
}

object Calibration extends Helpers {
    var offsetX = Properties("calibration.offsetX").toInt
    var offsetY = Properties("calibration.offsetY").toInt
    var width   = Properties("calibration.width").toInt
    var height  = Properties("calibration.height").toInt
    var closeObjectDistance  = Properties("calibration.closeObjectDistance").toInt

    var enabled = false

    def toggle { enabled = !enabled }

    def key(ch: Char) {
        if(enabled){
            ch match {
                // move
                case 'i' =>
                    offsetY -= 1
                    Properties("calibration.offsetY") = offsetY.toString
                    Properties.save
                case 'k' =>
                    offsetY += 1
                    Properties("calibration.offsetY") = offsetY.toString
                    Properties.save
                case 'j' =>
                    offsetX -= 1
                    Properties("calibration.offsetX") = offsetX.toString
                    Properties.save
                case 'l' =>
                    offsetX += 1
                    Properties("calibration.offsetX") = offsetX.toString
                    Properties.save

                // resize
                case 'w' =>
                    height -= 1
                    Properties("calibration.height") = height.toString
                    Properties.save
                case 's' =>
                    height += 1
                    Properties("calibration.height") = height.toString
                    Properties.save
                case 'a' =>
                    width -= 1
                    Properties("calibration.width") = width.toString
                    Properties.save
                case 'd' =>
                    width += 1
                    Properties("calibration.width") = width.toString
                    Properties.save

                // change close object distance
                case ']' =>
                    closeObjectDistance += 1
                    Properties("calibration.closeObjectDistance") = closeObjectDistance.toString
                    Properties.save

                case '[' =>
                    closeObjectDistance -= 1
                    Properties("calibration.closeObjectDistance") = closeObjectDistance.toString
                    Properties.save


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