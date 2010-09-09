package synergia.platri

import java.awt._
import javax.swing._
import java.awt.event.{WindowAdapter, WindowEvent}
import javax.media.opengl._
import javax.media.opengl.awt.GLCanvas
import com.jogamp.opengl.util._

object View {
	object Scene extends GLEventListener {
		var i = 0f
		def display(drawable: GLAutoDrawable) {
			val gl = drawable.getGL.getGL2
			
			gl.glClear(GL.GL_COLOR_BUFFER_BIT);
			
		   	gl.glBegin(GL.GL_TRIANGLES);
		    gl.glColor3f(i, 0, 0);
		    gl.glVertex2f(-1, -1);
		    gl.glColor3f(0, 1, 0);
		    gl.glVertex2f(0, 1);
		    gl.glColor3f(0, 0, 1);
		    gl.glVertex2f(1, -1);
		    gl.glEnd();
		    // put your drawing code here
		
		i += 0.1f
		if(i > 1) i = 0f
		}

		def init(drawable: GLAutoDrawable) {
		    drawable.getGL.setSwapInterval(1)
		}

		def dispose(drawable: GLAutoDrawable) {
		    // put your cleanup code here
		}

		def reshape(drawable: GLAutoDrawable, x: Int, y: Int, width: Int, height: Int) {
		    // called when user resizes the window
		}
	}
	
	GLProfile.initSingleton
	val glp = GLProfile.getDefault
	val caps = new GLCapabilities(glp)
	val canvas = new GLCanvas(caps)
	canvas.addGLEventListener(Scene)
	
	val frame = new Frame("platri")
	frame.add(canvas)
	
	frame.addWindowListener(new WindowAdapter {
		override def windowClosing(e: WindowEvent){
			// stop
			exit(0)
		}
	})
	
	def start {
		val device = GraphicsEnvironment.getLocalGraphicsEnvironment.getDefaultScreenDevice
		val dms = device.getDisplayModes.toList
		println(dms)
		val dm = dms(2)
		device.setFullScreenWindow(frame)
		device.setDisplayMode(dm)
		
		frame.setSize(new Dimension(dm.getWidth, dm.getHeight))
		frame.setUndecorated(true)
		frame.validate()
		
		// if(device.isFullScreenSupported()){
		// 	if(device.isDisplayChangeSupported()){
		// 		mode = chooseBestMode(device.getDisplayModes, )
		// 	}
		// }
		// 
		// frame.setSize(Config.WIDTH, Config.HEIGHT)
		frame.setVisible(true)
		
		//val animator = new Animator(canvas)
		val animator = new FPSAnimator(canvas, Config.FPS)
		animator.add(canvas)
		animator.start
	}
	
	
	def main(args: Array[String]): Unit = {
		View.start	
	}
}
// 
// object View {
// 	def main(args: Array[String]): Unit = {
// 		try {
// 			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
// 		} catch {
// 			case e => println(e)
// 		}
// 		
//         val device = GraphicsEnvironment.getLocalGraphicsEnvironment.getScreenDevices()(0)
//         val test = new DisplayModeTest(device)
//         test.initComponents(test.getContentPane)
//         test.setVisible(true);
// 	}
// }

// package synergia.platri
// 
// import javax.swing.JFrame
// import java.awt._
// import java.awt.event._
// import com.sun.opengl.util.FPSAnimator
// import javax.media.opengl._
// import javax.media.opengl.glu.GLU
// import com.sun.opengl.util.GLUT
// 
// object View extends GLEventListener with KeyListener {
// 	import Config._
// 	
// 	// Temporary configuration
// 	final val DONT_CARE = -1
// 	
// 	var manager: Manager = null
// 	
// 	var usedDevice: GraphicsDevice = null
// 	
// 	// configuration bullshit
// 	val glCanvas = new GLCanvas(new GLCapabilities)
// 	glCanvas.setSize(WIDTH, HEIGHT)
// 	glCanvas.setIgnoreRepaint(true)
// 	glCanvas.addGLEventListener(this)
// 	glCanvas.addKeyListener(this)
// 	
// 	val frame = new JFrame("platri")
// 	frame.getContentPane.setLayout(new BorderLayout)
// 	frame.getContentPane.add(glCanvas, BorderLayout.CENTER)
// 	
// 	val animator = new FPSAnimator(glCanvas, FPS)
// 	animator.setRunAsFastAsPossible(true)
// 	
// 	var application: Application = null
// 		
// 	def init {
// 		frame.setUndecorated(FULLSCREEN)
// 		frame.addWindowListener(new WindowAdapter {
// 			override def windowClosing(e: WindowEvent){
// 				stop
// 				exit(0)
// 			}
// 		})
// 		
// 		if(FULLSCREEN){
// 			usedDevice = GraphicsEnvironment.getLocalGraphicsEnvironment.getDefaultScreenDevice
// 			usedDevice.setFullScreenWindow(frame)
// 			usedDevice.setDisplayMode(findDisplayMode(
// 				usedDevice.getDisplayModes,
// 				WIDTH, HEIGHT,
// 				usedDevice.getDisplayMode.getBitDepth,
// 				usedDevice.getDisplayMode.getRefreshRate
// 			))
// 		} else {
// 			val screenSize = Toolkit.getDefaultToolkit.getScreenSize
// 			frame.setSize(frame.getContentPane.getPreferredSize)
// 			frame.setLocation(
// 				(screenSize.width - frame.getWidth) / 2,
// 				(screenSize.height - frame.getHeight) / 2
// 			)
// 			frame.setVisible(true)
// 		}
// 		
// 		glCanvas.requestFocus
// 	}
// 	
// 	def start(app: Application) {
// 		application = app
// 		animator.start
// 	}
// 	
// 	def stop {
// 		manager.stop
// 		animator.stop
// 		if(FULLSCREEN){
// 			usedDevice.setFullScreenWindow(null)
// 			usedDevice = null
// 		}
// 		frame.dispose
// 	}
// 	
// 	private def findDisplayMode(displayModes: Array[DisplayMode], requestedWidth: Int, requestedHeight: Int, requestedDepth: Int, requestedRefreshRate: Int) = {
// 		var displayMode = findDisplayModeInternal(displayModes, requestedWidth, requestedHeight, requestedDepth, requestedRefreshRate)
// 
// 		if (displayMode == null)
// 			displayMode = findDisplayModeInternal(displayModes, requestedWidth, requestedHeight, DONT_CARE, DONT_CARE)
// 
// 		if (displayMode == null)
// 			displayMode = findDisplayModeInternal(displayModes, requestedWidth, DONT_CARE, DONT_CARE, DONT_CARE)
// 
// 		if (displayMode == null)
// 			displayMode = findDisplayModeInternal(displayModes, DONT_CARE, DONT_CARE, DONT_CARE, DONT_CARE)
// 
// 		displayMode
// 	}
// 
// 	private def findDisplayModeInternal(displayModes: Array[DisplayMode], requestedWidth: Int, requestedHeight: Int, requestedDepth: Int, requestedRefreshRate: Int) = {
// 		displayModes.toList.find(dm =>
// 			(requestedWidth == DONT_CARE || requestedWidth == dm.getWidth) &&
// 							(requestedHeight == DONT_CARE || requestedHeight == dm.getHeight) &&
// 							(requestedDepth == DONT_CARE || requestedDepth == dm.getBitDepth) &&
// 							(requestedRefreshRate == DONT_CARE || requestedDepth == dm.getRefreshRate)
// 			).getOrElse(displayModes(0))
// 	}	
// 	
// 	
// 	// KeyListener implementation
// 	
// 	def keyReleased(event: KeyEvent) {}
// 	
// 	def keyPressed(event: KeyEvent) {}
// 	
// 	def keyTyped(event: KeyEvent) {
// 		event.getKeyCode match {
// 			case KeyEvent.VK_ESCAPE => 
// 				stop
// 				exit(0)
// 			case c => Debug.info("[key] " + c)
// 		}
// 	}
// 	
// 	
// 	// GLEventListener implementation
// 	
// 	import GL._
// 	
// 	var drawable: GLAutoDrawable = _
// 	var gl: GL = _
// 	var glu: GLU = _
// 	var glut: GLUT = _
// 	
// 	def init(dr: GLAutoDrawable) {
// 		drawable = dr
// 		gl = drawable.getGL
// 		glu = new GLU
// 		glut = new GLUT
// 		
// 		View.gl = gl
// 		
// 		gl.glClearColor(0f, 0f, 0f, 0f)
// 		
// 		gl.glEnable(GL.GL_BLEND)
//         gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA)
// 
// 		manager = new Manager(application)
// 	}
// 	
// 	def reshape(dr: GLAutoDrawable, x: Int, y: Int, width: Int, height: Int) {
// 		gl.glViewport(0, 0, WIDTH, HEIGHT)
// 		gl.glMatrixMode(GL.GL_PROJECTION)
// 		gl.glLoadIdentity
// 		glu.gluOrtho2D(0, WIDTH, HEIGHT, 0)
// 		gl.glMatrixMode(GL.GL_MODELVIEW)
// 	}
// 
// 	def displayChanged(dr: GLAutoDrawable, modeChanged: Boolean, deviceChanged: Boolean) {}
// 
// 	def display(dr: GLAutoDrawable) {
// 		gl.glClear(GL_COLOR_BUFFER_BIT)
// 		gl.glClearColor(1f, 1f, 1f, 1f)
// 		
// 		if(DEBUG) Debug.display
// 
// 		gl.glPushMatrix
// 		gl.glEnable(GL.GL_TEXTURE_2D)
// 		manager.display
// 		gl.glDisable(GL.GL_TEXTURE_2D)
// 		gl.glPopMatrix
// 		
// 		gl.glFlush
// 	}
// 
// 	def main(args: Array[String]) {	
// 		init
// 		start(apps.midi.App)
// 	}
// }
