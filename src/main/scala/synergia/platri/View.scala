package synergia.platri

import javax.swing.JFrame
import java.awt._
import java.awt.event.{WindowEvent, WindowAdapter}
import com.sun.opengl.util.FPSAnimator
import javax.media.opengl._
import javax.media.opengl.glu.GLU
import com.sun.opengl.util.GLUT

object Config {
	final val WIDTH = 800
	final val HEIGHT = 600
	final val FULLSCREEN = false
	final val CLOSE_OBJECT_DISTANCE = 300
	final val BASE_SIZE = 100
}

object View extends GLEventListener {
	import Config._
	
	// Temporary configuration
	final val DONT_CARE = -1
	
	var manager: Manager = null
	
	var usedDevice: GraphicsDevice = null
	
	// configuration bullshit
	val glCanvas = new GLCanvas(new GLCapabilities)
	glCanvas.setSize(WIDTH, HEIGHT)
	glCanvas.setIgnoreRepaint(true)
	glCanvas.addGLEventListener(this)
	
	val frame = new JFrame("platri")
	frame.getContentPane.setLayout(new BorderLayout)
	frame.getContentPane.add(glCanvas, BorderLayout.CENTER)
	
	val animator = new FPSAnimator(glCanvas, 50)
	animator.setRunAsFastAsPossible(true)
	
	var appFunc: () => Application = null
		
	def init {
		frame.setUndecorated(FULLSCREEN)
		frame.addWindowListener(new WindowAdapter {
			override def windowClosing(e: WindowEvent){
				stop
				exit(0)
			}
		})
		
		if(FULLSCREEN){
			usedDevice = GraphicsEnvironment.getLocalGraphicsEnvironment.getDefaultScreenDevice
			usedDevice.setFullScreenWindow(frame)
			usedDevice.setDisplayMode(findDisplayMode(
				usedDevice.getDisplayModes,
				WIDTH, HEIGHT,
				usedDevice.getDisplayMode.getBitDepth,
				usedDevice.getDisplayMode.getRefreshRate
			))
		} else {
			val screenSize = Toolkit.getDefaultToolkit.getScreenSize
			frame.setSize(frame.getContentPane.getPreferredSize)
			frame.setLocation(
				(screenSize.width - frame.getWidth) / 2,
				(screenSize.height - frame.getHeight) / 2
			)
			frame.setVisible(true)
		}
		
		glCanvas.requestFocus
	}
	
	def start(app: () => Application) {
		appFunc = app
		animator.start
	}
	
	def stop {
		animator.stop
		if(FULLSCREEN){
			usedDevice.setFullScreenWindow(null)
			usedDevice = null
		}
		frame.dispose
	}
	
	private def findDisplayMode(displayModes: Array[DisplayMode], requestedWidth: Int, requestedHeight: Int, requestedDepth: Int, requestedRefreshRate: Int) = {
		var displayMode = findDisplayModeInternal(displayModes, requestedWidth, requestedHeight, requestedDepth, requestedRefreshRate)

		if (displayMode == null)
			displayMode = findDisplayModeInternal(displayModes, requestedWidth, requestedHeight, DONT_CARE, DONT_CARE)

		if (displayMode == null)
			displayMode = findDisplayModeInternal(displayModes, requestedWidth, DONT_CARE, DONT_CARE, DONT_CARE)

		if (displayMode == null)
			displayMode = findDisplayModeInternal(displayModes, DONT_CARE, DONT_CARE, DONT_CARE, DONT_CARE)

		displayMode
	}

	private def findDisplayModeInternal(displayModes: Array[DisplayMode], requestedWidth: Int, requestedHeight: Int, requestedDepth: Int, requestedRefreshRate: Int) = {
		displayModes.toList.find(dm =>
			(requestedWidth == DONT_CARE || requestedWidth == dm.getWidth) &&
							(requestedHeight == DONT_CARE || requestedHeight == dm.getHeight) &&
							(requestedDepth == DONT_CARE || requestedDepth == dm.getBitDepth) &&
							(requestedRefreshRate == DONT_CARE || requestedDepth == dm.getRefreshRate)
			).getOrElse(displayModes(0))
	}	
	
	
	// GLEventListener implementation
	
	import GL._
	
	var drawable: GLAutoDrawable = _
	var gl: GL = _
	var glu: GLU = _
	var glut: GLUT = _
	
	object FPSCounter extends Helpers {
		private var frameCount = 0
		private var timebase = 0L
		private var fpsStr = ""
		
		def display {
			frameCount += 1;
			val time = System.currentTimeMillis

			if(time - timebase > 1000) {
				fpsStr = "FPS:%4.2f".format(frameCount * 1000.0 / (time - timebase))
				timebase = time
				frameCount = 0
			}
			
			color(0, 0, 0)
			text(WIDTH-100, 60, fpsStr)
		}
	}
	
	def init(dr: GLAutoDrawable) {
		drawable = dr
		gl = drawable.getGL
		glu = new GLU
		glut = new GLUT
		
		View.gl = gl
		
		gl.glClearColor(0f, 0f, 0f, 0f)
		
		gl.glEnable(GL.GL_BLEND)
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA)

		manager = new Manager(appFunc())
	}
	
	def reshape(dr: GLAutoDrawable, x: Int, y: Int, width: Int, height: Int) {
		gl.glViewport(0, 0, WIDTH, HEIGHT)
		gl.glMatrixMode(GL.GL_PROJECTION)
		gl.glLoadIdentity
		glu.gluOrtho2D(0, WIDTH, HEIGHT, 0)
		gl.glMatrixMode(GL.GL_MODELVIEW)
	}

	def displayChanged(dr: GLAutoDrawable, modeChanged: Boolean, deviceChanged: Boolean) {}

	def display(dr: GLAutoDrawable) {
		gl.glClear(GL_COLOR_BUFFER_BIT)
		gl.glClearColor(1f, 1f, 1f, 1f)

		gl.glPushMatrix
		gl.glEnable(GL.GL_TEXTURE_2D)
		manager.display
		gl.glDisable(GL.GL_TEXTURE_2D)
		gl.glPopMatrix
		FPSCounter.display
		
		gl.glFlush
	}

	def main(args: Array[String]) {	
		init
		start(() => apps.midi.App)
	}
}