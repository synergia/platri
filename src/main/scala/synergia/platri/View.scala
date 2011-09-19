package synergia.platri

import javax.swing._
import java.awt.event._
import processing.core._

trait GFX {
    def display
}

object View extends PApplet {
    {
        val frame = new JFrame("Test")
        frame.addWindowListener(new WindowAdapter(){
            override def windowClosing(e: WindowEvent){
                System.exit(0)
            }
        })

        // def keyReleased(event: KeyEvent) {}
        //
        // def keyPressed(event: KeyEvent) {}
        //
        // def keyTyped(event: KeyEvent) {
        //     event.getKeyChar
        // }

        frame.getContentPane.add(this)
        init
        frame.pack
        frame.setVisible(true)
    }

    override def keyPressed {
        key match {
            case 'x' => Config.toggleDebug
            case c => Calibration.key(c)
        }
    }

    override def setup {
        import PConstants._
        size(400, 300, JAVA2D)
        hint(ENABLE_NATIVE_FONTS)
        textFont(helvetica, 14)
    }

    override def draw {
        background(51)
        Manager.display
    }

    lazy val helvetica = loadFont("Helvetica-14.vlw")

    // utils

    def matrix(f: => Unit){
        pushMatrix
        f
        popMatrix
    }
}







//     import Config._
//
//     var gl: GL2 = _
//
//     class Scene(canvas: GLCanvas) extends Frame with GLEventListener with KeyListener {
//         val device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()(0)
//
//         setTitle("Fullscreen")
//         add(canvas)
//         setUndecorated(FULLSCREEN)
//
//         addWindowListener(new WindowAdapter(){
//             override def windowActivated(e: WindowEvent){
//                 activated
//             }
//
//             override def windowClosing(e: WindowEvent){
//                 View.stop
//                 exit(0)
//             }
//         })
//
//         def activated {
//             if(Config.FULLSCREEN){
//                 setResizable(false)
//                 device.setFullScreenWindow(this)
//             } else {
//                 setSize(WIDTH, HEIGHT)
//             }
//             validate
//         }
//
//         def display(drawable: GLAutoDrawable) {
//             gl = drawable.getGL.getGL2
//             gl.glClear(GL_COLOR_BUFFER_BIT)
//             gl.glClearColor(1f, 1f, 1f, 1f)
//
//             gl.glPushMatrix
//             gl.glTranslated(Calibration.offsetX, Calibration.offsetY, 0)
//             gl.glEnable(GL.GL_TEXTURE_2D)
//             manager.display
//             gl.glDisable(GL.GL_TEXTURE_2D)
//             gl.glPopMatrix
//
//             if(DEBUG) Debug.display
//             if(Calibration.enabled) Calibration.display
//
//             gl.glFlush
//         }
//
//         def init(drawable: GLAutoDrawable) {
//             drawable.getGL.setSwapInterval(1)
//             gl = drawable.getGL.getGL2
//
//             gl.glClearColor(0f, 0f, 0f, 0f)
//             gl.glEnable(GL_BLEND)
//             gl.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
//         }
//
//         def dispose(drawable: GLAutoDrawable) {
//             // put your cleanup code here
//         }
//
//         def reshape(drawable: GLAutoDrawable, x: Int, y: Int, width: Int, height: Int) {
//             gl = drawable.getGL.getGL2
//             gl.glViewport(0, 0, WIDTH, HEIGHT)
//             gl.glMatrixMode(GL_PROJECTION)
//             gl.glLoadIdentity
//             (new GLU).gluOrtho2D(0, WIDTH, HEIGHT, 0)
//             gl.glMatrixMode(GL_MODELVIEW)
//         }
//
//         def keyReleased(event: KeyEvent) {}
//
//         def keyPressed(event: KeyEvent) {}
//
//         def keyTyped(event: KeyEvent) {
//             event.getKeyChar match {
//                 case 'x' => Config.toggleDebug
//                 // case 'q' =>
//                 //     View.stop
//                 //     exit(0)
//                 case c => Calibration.key(c)
//             }
//         }
//     }
//
//     GLProfile.initSingleton
//
//     val glp = GLProfile.getDefault
//     val caps = new GLCapabilities(glp)
//     val canvas = new GLCanvas(caps)
//     val scene = new Scene(canvas)
//     canvas.addGLEventListener(scene)
//     canvas.addKeyListener(scene)
//     scene.setVisible(true)
//
//     val animator = new Animator(canvas)
//     // val animator = new FPSAnimator(canvas, FPS)
//     animator.add(canvas)
//     animator.start
//
    // def stop {
    //     manager.stop
    //     // animator.stop
    // }

    // def width = 400 //scene.getWidth()

    // def height = 300 //scene.getHeight()

    // def main(args: Array[String]) {
    //     View
    // }
// }
